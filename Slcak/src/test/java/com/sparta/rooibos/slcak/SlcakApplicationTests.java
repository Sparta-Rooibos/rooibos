package com.sparta.rooibos.slcak;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@SpringBootTest
class SlcakApplicationTests {

	@Test
	void test2() throws InterruptedException {
		WebClient webClient = WebClient.builder()
				.baseUrl("https://hooks.slack.com/services/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")
				.build();

		String payload = """
				{
				"text" : "
				 주문 번호 : 1
				 주문자 정보 : 김말숙 / msk@seafood.world
				 상품 정보 : 마른 오징어 50박스
				 요청 사항 : 12월 12일 3시까지는 보내주세요!
				 발송지 : 경기 북부 센터
				 경유지 : 대전광역시 센터, 부산광역시 센터
				 도착지 : 부산시 사하구 낙동대로 1번길 1 해산물월드
				 배송담당자 : 고길동 / kdk@sparta.world
				
				"
				}
				""";

		Mono<String> response = webClient.post()
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(payload)
				.retrieve()

				.bodyToMono(String.class);

		response.subscribe(System.out::println);
	}
}
