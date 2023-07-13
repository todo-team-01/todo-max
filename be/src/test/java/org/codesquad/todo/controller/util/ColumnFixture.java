package org.codesquad.todo.controller.util;

import org.codesquad.todo.controller.dto.ColumnSaveRequestDTO;
import org.codesquad.todo.controller.dto.ColumnSaveResponseDTO;
import org.codesquad.todo.controller.dto.ColumnUpdateRequestDTO;
import org.springframework.http.MediaType;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class ColumnFixture {

	public static ExtractableResponse<Response> 컬럼_생성_요청(ColumnSaveRequestDTO columnSaveRequestDTO) {
		return RestAssured.given().log().all()
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.body(columnSaveRequestDTO)
			.when().post("/columns")
			.then().log().all()
			.extract();
	}

	public static ExtractableResponse<Response> 컬럼_카드_전체_조회_요청() {
		return RestAssured.given().log().all()
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.when().get("/columns")
			.then().log().all()
			.extract();
	}

	public static ExtractableResponse<Response> 컬럼_수정_요청(ColumnUpdateRequestDTO columnUpdateRequestDTO, Long id) {
		return RestAssured.given().log().all()
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.body(columnUpdateRequestDTO)
			.when().put("/columns/{id}", id)
			.then().log().all()
			.extract();
	}

	public static ExtractableResponse<Response> 컬럼_삭제_요청(Long id) {
		return RestAssured.given().log().all()
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.when().delete("/columns/{id}", id)
			.then().log().all()
			.extract();
	}

	public static ColumnSaveResponseDTO 컬럼_생성(ColumnSaveRequestDTO columnSaveRequestDTO) {
		return 컬럼_변환(컬럼_생성_요청(columnSaveRequestDTO));
	}

	private static ColumnSaveResponseDTO 컬럼_변환(ExtractableResponse<Response> response) {
		return response.as(ColumnSaveResponseDTO.class);
	}
}
