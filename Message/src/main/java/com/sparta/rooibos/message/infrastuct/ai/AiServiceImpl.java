package com.sparta.rooibos.message.infrastuct.ai;

import com.sparta.rooibos.message.application.ai.AiService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class AiServiceImpl implements AiService {
    @Value("${ai.token}")
    private String aiToken;

    @Override
    public String aiMadeMessage(String content) {
        WebClient webClient = WebClient.create("https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent");
        String requestBody = """
                {
                  "contents": [{
                    "parts": [
                      {"text": "주문 번호 : 1
                        주문자 정보 : 김말숙 / msk@seafood.world
                        상품 정보 : 마른 오징어 50박스
                        요청 사항 : 12월 12일 3시까지는 보내주세요!
                        발송지 : 경기 북부 센터
                        경유지 : 대전광역시 센터, 부산광역시 센터
                        도착지 : 부산시 사하구 낙동대로 1번길 1 해산물월드
                        배송담당자 : 고길동 / kdk@sparta.world
                        위 내용을 기반으로 도출된 최종 발송 시한은 12월 10일 오전 9시 입니다. 
                        템플릿은 이거고 발송지, 경유지, 도착지 정보로 배송 예정 시간을 계산해줘
                        12월 10일 오전 9시라는 값말고 다른 값으로 나오게 해줘
                        예시는 빼줘"
                      },
                      {"text": "%s"
                      }
                    ]
                  }]
                }
                """.formatted(content);

        String response = webClient.post()
                .uri("?key=" + aiToken)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return parsing(response);

    }

    public String parsing(String response) {
        // JSON 객체로 파싱
        JSONObject jsonObject = new JSONObject(response);
        // candidates 배열에서 첫 번째 항목 가져오기
        JSONArray candidates = jsonObject.getJSONArray("candidates");
        JSONObject firstCandidate = candidates.getJSONObject(0);
        // parts 배열에서 첫 번째 항목 가져오기
        JSONObject content = firstCandidate.getJSONObject("content");
        JSONArray parts = content.getJSONArray("parts");
        return parts.getJSONObject(0).getString("text");
    }
}
