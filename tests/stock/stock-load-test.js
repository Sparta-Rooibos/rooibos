import http from 'k6/http';
import { check, sleep, group } from 'k6';
import { randomString } from 'https://jslib.k6.io/k6-utils/1.2.0/index.js';

// 기본 테스트 설정
export const options = {
    vus: 1,           // 동시 사용자 수 1명 (CRUD 기본 테스트)
    iterations: 5,    // 각 테스트를 5번 반복
    thresholds: {
        http_req_duration: ['p(95)<500'], // 95%의 요청이 500ms 이내에 완료
        http_req_failed: ['rate<0.01'],   // 실패율 1% 미만
    },
};

// 인증 헤더 설정
const headers = {
    'Content-Type': 'application/json',
    'X-User-Email': 'test@example.com',
    'X-User-Name': 'testuser',
    'X-User-Role': 'ROLE_MASTER'
};

// 기본 URL
const baseUrl = 'http://localhost:19102/api/v1/stock';

export default function () {
    let stockId; // 생성된 재고 ID 저장 변수
    let productId = `product-${randomString(5)}`; // 테스트에 사용할 상품 ID

    // 1. Create 테스트
    group('Create Stock', function () {
        const createPayload = JSON.stringify({
            hubId: `hub-${randomString(5)}`,
            productId: productId,
            productQuantity: Math.floor(Math.random() * 50) + 50 // 50~99 범위의 랜덤 수량
        });

        const createResponse = http.post(`${baseUrl}`, createPayload, { headers: headers });

        check(createResponse, {
            'Create 상태 코드는 200': (r) => r.status === 200,
            'Create 응답에 ID가 포함됨': (r) => JSON.parse(r.body).id !== undefined,
        });

        // 생성된 재고 ID 저장
        if (createResponse.status === 200) {
            stockId = JSON.parse(createResponse.body).id;
            console.log(`재고 생성 성공: ID = ${stockId}, 상품 ID = ${productId}`);
        } else {
            console.log(`재고 생성 실패: ${createResponse.status}, ${createResponse.body}`);
            return; // 생성 실패 시 이후 테스트 중단
        }
    });

    sleep(1);

    // 2. Read 테스트 (상품 ID로 조회)
    group('Read Stock', function () {
        const readResponse = http.get(`${baseUrl}/${productId}`, { headers: headers });

        check(readResponse, {
            'Read 상태 코드는 200': (r) => r.status === 200,
            'Read 응답의 상품 ID가 일치함': (r) => JSON.parse(r.body).productId === productId,
        });

        if (readResponse.status === 200) {
            console.log(`재고 조회 성공: 상품 ID = ${productId}`);
        } else {
            console.log(`재고 조회 실패: ${readResponse.status}, ${readResponse.body}`);
        }
    });

    sleep(1);

    // 3. Update 테스트 (단일 재고 업데이트)
    group('Update Stock', function () {
        const updateQuantity = 10; // 추가할 수량
        const updatePayload = JSON.stringify({
            quantity: updateQuantity
        });

        const updateResponse = http.put(`${baseUrl}/${stockId}`, updatePayload, { headers: headers });

        check(updateResponse, {
            'Update 상태 코드는 200': (r) => r.status === 200,
        });

        if (updateResponse.status === 200) {
            console.log(`재고 업데이트 성공: ID = ${stockId}, 추가된 수량 = ${updateQuantity}`);
        } else {
            console.log(`재고 업데이트 실패: ${updateResponse.status}, ${updateResponse.body}`);
        }
    });

    sleep(1);

    // 4. 검색 테스트 (재고 검색)
    group('Search Stocks', function () {
        const queryParams = {
            page: 1,
            size: 10
        };

        const searchResponse = http.get(
            `${baseUrl}?page=${queryParams.page}&size=${queryParams.size}`,
            { headers: headers }
        );


        check(searchResponse, {
            'Search 상태 코드는 200': (r) => r.status === 200,
            'Search 응답에 stocks가 포함됨': (r) => {
                try {
                    const data = JSON.parse(r.body);
                    return data.stocks !== undefined;
                } catch (e) {
                    return false;
                }
            },
            'Search 응답의 totalCount 확인': (r) => {
                try {
                    const data = JSON.parse(r.body);
                    return data.totalCount !== undefined;
                } catch (e) {
                    return false;
                }
            }
        });

        if (searchResponse.status === 200) {
            try {
                const responseData = JSON.parse(searchResponse.body);
                const stocks = responseData.stocks || [];

                console.log(`재고 검색 성공: 총 ${responseData.totalCount || 0}개 항목, 페이지: ${responseData.page}, 크기: ${responseData.size}`);

                // stocks 배열에 요소가 있는 경우에만 처리
                if (stocks.length > 0) {
                    // 첫 번째 항목의 정보에 접근
                    const firstItem = stocks[0];
                    console.log(`첫 번째 항목: ${JSON.stringify(firstItem)}`);

                    // 모든 항목 순회
                    stocks.forEach((item, index) => {
                        if (item) {  // item이 null이 아닌 경우에만
                            console.log(`항목 ${index+1}: ID=${item.id || '없음'}, 상품ID=${item.productId || '없음'}`);
                        }
                    });
                } else {
                    console.log('검색 결과가 없습니다.');
                }
            } catch (e) {
                console.log(`응답 파싱 실패: ${e.message}`);
                console.log(`원본 응답: ${searchResponse.body}`);
            }
        } else {
            console.log(`재고 검색 실패: ${searchResponse.status}, ${searchResponse.body}`);
        }
    });

    sleep(1);

    // 5. Delete 테스트 (재고 삭제)
    group('Delete Stock', function () {
        const deleteResponse = http.patch(`${baseUrl}/${stockId}`, null, { headers: headers });

        check(deleteResponse, {
            'Delete 상태 코드는 200': (r) => r.status === 200,
        });

        if (deleteResponse.status === 200) {
            console.log(`재고 삭제 성공: ID = ${stockId}`);
        } else {
            console.log(`재고 삭제 실패: ${deleteResponse.status}, ${deleteResponse.body}`);
        }
    });

    // 6. Delete 확인 테스트 (삭제 후 조회)
    group('Verify Delete', function () {
        // 상품 ID로 조회 시 삭제된 항목은 조회되지 않아야 함
        const verifyResponse = http.get(`${baseUrl}/${productId}`, { headers: headers });

        check(verifyResponse, {
            'Verify Delete - 상태 코드는 404 또는 삭제됨 표시': (r) =>
                r.status === 404 || (r.status === 200 && JSON.parse(r.body).deleteBy !== null),
        });

        if (verifyResponse.status === 404) {
            console.log(`삭제 확인 성공: 상품 ID = ${productId} 조회 불가`);
        } else if (verifyResponse.status === 200) {
            const result = JSON.parse(verifyResponse.body);
            console.log(`삭제 확인: 상품 ID = ${productId}, 삭제 여부 = ${result.deleteBy !== null}`);
        } else {
            console.log(`삭제 확인 실패: ${verifyResponse.status}, ${verifyResponse.body}`);
        }
    });

    sleep(2);
}