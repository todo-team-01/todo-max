package org.codesquad.todo.controller.util;

import org.codesquad.todo.controller.dto.ColumnSaveRequestDTO;
import org.codesquad.todo.controller.dto.ColumnSaveResponseDTO;
import org.codesquad.todo.controller.dto.ColumnUpdateRequestDTO;
import org.springframework.http.MediaType;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class ColumnSteps {
	public static final String 해야할_일_컬럼_이름 = "해야할 일";
	public static final String 하고_있는_일_컬럼_이름 = "하고 있는 일";
	public static final String 완료한_일_컬럼_이름 = "완료한 일";

	public static ExtractableResponse<Response> 칼럼_생성_요청(ColumnSaveRequestDTO columnSaveRequestDTO) {
		return RestAssured.given().log().all()
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.body(columnSaveRequestDTO)
			.when().post("/api/columns")
			.then().log().all()
			.extract();
	}

	public static ExtractableResponse<Response> 칼럼_카드_전체_조회_요청() {
		return RestAssured.given().log().all()
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.when().get("/api/columns")
			.then().log().all()
			.extract();
	}

	public static ExtractableResponse<Response> 칼럼_수정_요청(Long id, ColumnUpdateRequestDTO columnUpdateRequestDTO) {
		return RestAssured.given().log().all()
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.body(columnUpdateRequestDTO)
			.when().put("/api/columns/{id}", id)
			.then().log().all()
			.extract();
	}

	public static ExtractableResponse<Response> 칼럼_삭제_요청(Long id) {
		return RestAssured.given().log().all()
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.when().delete("/api/columns/{id}", id)
			.then().log().all()
			.extract();
	}

	private static ColumnSaveResponseDTO 칼럼_변환(ExtractableResponse<Response> response) {
		return response.as(ColumnSaveResponseDTO.class);
	}
}
