package org.codesquad.todo.controller;

import static org.assertj.core.api.Assertions.*;

import org.codesquad.todo.controller.dto.CardModifyRequestDto;
import org.codesquad.todo.controller.dto.CardSaveRequestDto;
import org.codesquad.todo.controller.dto.CardSaveResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CardControllerTest {

	@LocalServerPort
	private int port;

	@BeforeEach
	void setUp() {
		RestAssured.port = port;
	}

	@DisplayName("카드를 등록한다.")
	@Test
	void saveCard() {
		// given
		// TODO 칼럼 생성 만들기
		// TODO 멤버 생성 만들기
		CardSaveRequestDto cardSaveRequestDto = new CardSaveRequestDto(1L, "Git 공부하기", "stash", null);

		// when
		ExtractableResponse<Response> response = RestAssured.given().log().all()
			.body(cardSaveRequestDto)
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.when().post("/cards")
			.then().log().all()
			.extract();

		// then
		CardSaveResponseDto responseBody = response.response().as(CardSaveResponseDto.class);

		assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
		assertThat(responseBody.getCardId()).isEqualTo(4L);
		assertThat(responseBody.getColumnId()).isEqualTo(cardSaveRequestDto.getColumnId());
		assertThat(responseBody.getCardTitle()).isEqualTo(cardSaveRequestDto.getCardTitle());
		assertThat(responseBody.getCardContent()).isEqualTo(cardSaveRequestDto.getCardContent());
	}

	@DisplayName("카드를 수정한다.")
	@Test
	void modifyCard() {
		// given
		// TODO 칼럼 생성 만들기
		// TODO 멤버 생성 만들기
		CardModifyRequestDto cardModifyRequestDto = new CardModifyRequestDto("Git 공부하기", "stash");

		// when
		ExtractableResponse<Response> response = RestAssured.given().log().all()
			.body(cardModifyRequestDto)
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.when().put("/cards/1")
			.then().log().all()
			.extract();

		// then
		assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
	}
}
