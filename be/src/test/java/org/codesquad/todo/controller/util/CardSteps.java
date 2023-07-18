package org.codesquad.todo.controller.util;

import org.codesquad.todo.controller.dto.CardModifyRequestDto;
import org.codesquad.todo.controller.dto.CardMoveRequestDto;
import org.codesquad.todo.controller.dto.CardSaveRequestDto;
import org.springframework.http.MediaType;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class CardSteps {

	public static ExtractableResponse<Response> 카드_생성_요청(CardSaveRequestDto cardSaveRequestDto) {
		return RestAssured.given().log().all()
			.body(cardSaveRequestDto)
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.when().post("/api/cards")
			.then().log().all()
			.extract();
	}

	public static ExtractableResponse<Response> 카드_수정_요청(Long id, CardModifyRequestDto cardModifyRequestDto) {
		return RestAssured.given().log().all()
			.body(cardModifyRequestDto)
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.when().put("/api/cards/{id}", id)
			.then().log().all()
			.extract();
	}

	public static ExtractableResponse<Response> 카드_삭제_요청(Long id) {
		return RestAssured.given().log().all()
			.when().delete("/api/cards/{id}", id)
			.then().log().all()
			.extract();
	}

	public static ExtractableResponse<Response> 카드_이동_요청(Long id, CardMoveRequestDto cardMoveRequestDto) {
		return RestAssured.given().log().all()
			.body(cardMoveRequestDto)
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.when().patch("/api/cards/{id}", id)
			.then().log().all()
			.extract();
	}
}
