import http from 'k6/http';
import { check, sleep } from 'k6';
import { randomString } from 'https://jslib.k6.io/k6-utils/1.2.0/index.js';
import { Counter } from 'k6/metrics';

// 데드락 카운터 정의
const deadlockCounter = new Counter('deadlocks');
const successCounter = new Counter('successful_updates');

// 테스트 설정 - 매우 공격적인 설정
export const options = {
    scenarios: {
        extreme_deadlock: {
            executor: 'ramping-arrival-rate',
            startRate: 20,         // 초당 20개 요청으로 시작
            timeUnit: '1s',
            preAllocatedVUs: 50,
            maxVUs: 200,
            stages: [
                { duration: '5s', target: 50 },    // 5초 동안 초당 50개 요청으로 증가
                { duration: '10s', target: 100 },  // 10초 동안 초당 100개 요청으로 증가
                { duration: '10s', target: 100 },  // 10초 동안 100개 유지
                { duration: '5s', target: 20 }     // 5초 동안 20개로 감소
            ],
        }
    },
    thresholds: {
        'deadlocks': ['count>0'], // 데드락이 발생해야 성공
    },
};

// 인증 헤더 설정
const headers = {
    'Content-Type': 'application/json',
    'X-User-Email': 'test@example.com',
    'X-User-Name': 'testuser',
    'X-User-Role': 'MASTER'
};

// 기본 URL
const baseUrl = 'http://localhost:19102/api/v1/stock';

// 테스트 초기화 함수
export function setup() {
    const createdIds = [];

    // 필수 테스트용 재고 2개만 생성 (데드락 유발이 더 쉬움)
    for (let i = 0; i < 2; i++) {
        const productId = `deadlock-extreme-${randomString(5)}`;

        const createPayload = JSON.stringify({
            hubId: `hub-${randomString(5)}`,
            productId: productId,
            productQuantity: 10000 // 매우 큰 초기 수량
        });

        const createResponse = http.post(
            `${baseUrl}`,
            createPayload,
            { headers: headers }
        );

        if (createResponse.status === 200) {
            try {
                const stockId = JSON.parse(createResponse.body).id;
                createdIds.push(stockId);
                console.log(`테스트용 재고 ${i+1} 생성: ID = ${stockId}`);
            } catch (e) {
                console.log(`응답 파싱 실패: ${createResponse.body}`);
            }
        }
    }

    if (createdIds.length < 2) {
        console.log('테스트용 재고 생성 실패. 기본값을 사용합니다.');
        return {
            stockIds: [
                'f47ac10b-58cc-4372-a567-0e02b2c3d479',
                '7de88e5a-a91d-4e5b-b1eb-47bcf23adec1'
            ]
        };
    }

    return { stockIds: createdIds };
}

// 메인 테스트 함수
export default function (data) {
    const stockIds = data.stockIds;

    if (!stockIds || stockIds.length < 2) {
        console.log('테스트를 위한 충분한 재고 ID가 없습니다.');
        return;
    }

    // 랜덤하게 둘 중 하나의 업데이트 전략 선택
    const strategyChoice = Math.floor(Math.random() * 2);

    // 전략 1: 서로 반대 순서로 다중 업데이트 두 개를 동시에 실행
    if (strategyChoice === 0) {
        // 순서 1: A -> B
        const updatePayload1 = JSON.stringify({
            stocks: [
                { id: stockIds[0], productQuantity: 1 },
                { id: stockIds[1], productQuantity: 1 }
            ]
        });

        // 순서 2: B -> A (완전히 반대 순서)
        const updatePayload2 = JSON.stringify({
            stocks: [
                { id: stockIds[1], productQuantity: 1 },
                { id: stockIds[0], productQuantity: 1 }
            ]
        });

        // 두 요청을 배치로 동시에 보냄 (데드락 가능성 높음)
        const responses = http.batch([
            {
                method: 'PUT',
                url: `${baseUrl}/multi`,
                body: updatePayload1,
                params: { headers: headers, timeout: 10000 } // 10초 타임아웃
            },
            {
                method: 'PUT',
                url: `${baseUrl}/multi`,
                body: updatePayload2,
                params: { headers: headers, timeout: 10000 }
            }
        ]);

        // 응답 확인
        responses.forEach((response, index) => {
            if (response.status === 200) {
                successCounter.add(1);
            } else {
                checkForDeadlock(response);
            }
        });
    }
    // 전략 2: 개별 업데이트를 동시에 여러 번 시도 (더 작은 단위로 경쟁 발생)
    else {
        // 각 재고에 대해 개별 업데이트 여러 개 생성
        const requests = [];

        // 10개의 개별 요청 생성 (5개 재고 A, 5개 재고 B)
        for (let i = 0; i < 5; i++) {
            // 재고 A 업데이트
            requests.push({
                method: 'PUT',
                url: `${baseUrl}/${stockIds[0]}`,
                body: JSON.stringify({ quantity: 1 }),
                params: { headers: headers, timeout: 10000 }
            });

            // 재고 B 업데이트
            requests.push({
                method: 'PUT',
                url: `${baseUrl}/${stockIds[1]}`,
                body: JSON.stringify({ quantity: 1 }),
                params: { headers: headers, timeout: 10000 }
            });
        }

        // 모든 요청을 동시에 보냄
        const responses = http.batch(requests);

        // 응답 확인
        responses.forEach((response, index) => {
            if (response.status === 200) {
                successCounter.add(1);
            } else {
                checkForDeadlock(response);
            }
        });
    }

    // 짧은 시간 동안 대기
    sleep(Math.random() * 0.1); // 0~100ms 랜덤 대기
}

// 데드락 감지 함수
function checkForDeadlock(response) {
    // 응답 본문이 있고 데드락 관련 메시지가 포함되어 있는지 확인
    if (response.body && (
        response.body.includes('deadlock detected') ||
        response.body.includes('40P01') ||
        response.body.includes('could not serialize access') ||
        response.body.includes('Lock wait timeout') ||
        response.body.includes('transaction was deadlocked')
    )) {
        deadlockCounter.add(1);
        console.log(`데드락 감지됨!: ${response.body}`);
    } else {
        console.log(`요청 실패 (데드락 아님): ${response.status}, ${response.body}`);
    }
}

// 테스트 종료 후 정리
export function teardown(data) {
    console.log(`테스트 완료: 총 ${deadlockCounter.value}개의 데드락 감지, ${successCounter.value}개의 성공적인 업데이트`);

    if (deadlockCounter.value > 0) {
        console.log('데드락 테스트 성공! 데드락이 감지되었습니다.');
    } else {
        console.log('데드락이 발생하지 않았습니다. 더 강한 부하로 다시 시도하세요.');
    }
}