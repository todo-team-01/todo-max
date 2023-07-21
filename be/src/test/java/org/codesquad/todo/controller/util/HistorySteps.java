package org.codesquad.todo.controller.util;

import org.springframework.http.MediaType;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class HistorySteps {

	public static ExtractableResponse<Response> 히스토리_전체_조회_요청() {
		return RestAssured.given().log().all()
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.when().get("/api/histories")
			.then().log().all()
			.extract();
	}

	public static ExtractableResponse<Response> 히스토리_전체_삭제_요청() {
		return RestAssured.given().log().all()
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.when().delete("/api/histories")
			.then().log().all()
			.extract();
	}
}
