package org.codesquad.todo.controller;

import static org.assertj.core.api.Assertions.*;
import static org.codesquad.todo.controller.util.ColumnSteps.*;

import java.util.List;

import org.codesquad.todo.controller.dto.ColumnSaveRequestDTO;
import org.codesquad.todo.controller.dto.ColumnUpdateRequestDTO;
import org.codesquad.todo.controller.util.AcceptanceTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class ColumnAcceptanceTest extends AcceptanceTest {

	@DisplayName("모든 컬럼 및 카드들을 조회한다.")
	@Test
	void showColumnWithCards() {
		// given
		컬럼들을_생성한다();

		// when
		var response = 칼럼_카드_전체_조회_요청();

		// then
		조회된_컬럼_목록들을_검증한다(response);
	}

	@DisplayName("컬럼을 생성한다.")
	@Test
	void saveColumn() {
		// given

		// when
		var response = 컬럼을_저장한다();

		// then
		저장된_컬럼을_검증한다(response);
	}

	@DisplayName("컬럼을 수정한다.")
	@Test
	void updateColumn() {
		// given
		컬럼을_생성한다();

		// when
		var response = 컬럼을_수정한다();

		// then
		수정된_컬럼을_검증한다(response);
	}

	@DisplayName("컬럼을 삭제한다.")
	@Test
	void deleteColumn() {
		// given
		컬럼을_생성한다();

		// when
		var response = 컬럼을_삭제한다();

		// then
		삭제된_컬럼을_검증한다(response);
	}

	private void 컬럼들을_생성한다() {
		칼럼_생성(해야할_일_컬럼_이름);
		칼럼_생성(하고_있는_일_컬럼_이름);
	}

	private void 조회된_컬럼_목록들을_검증한다(ExtractableResponse<Response> response) {
		List<String> names = response.jsonPath().getList("columnName", String.class);

		Assertions.assertAll(
			() -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
			() -> assertThat(names).containsAnyOf(해야할_일_컬럼_이름, 하고_있는_일_컬럼_이름)
		);
	}

	private ExtractableResponse<Response> 컬럼을_저장한다() {
		return 칼럼_생성_요청(new ColumnSaveRequestDTO(해야할_일_컬럼_이름));
	}

	private void 저장된_컬럼을_검증한다(ExtractableResponse<Response> response) {
		Assertions.assertAll(
			() -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
			() -> assertThat(response.jsonPath().getString("name")).isEqualTo(해야할_일_컬럼_이름)
		);
	}

	private void 컬럼을_생성한다() {
		칼럼_생성(해야할_일_컬럼_이름);
	}

	private ExtractableResponse<Response> 컬럼을_수정한다() {
		return 칼럼_수정_요청(new ColumnUpdateRequestDTO("변경"), 1L);
	}

	private void 수정된_컬럼을_검증한다(ExtractableResponse<Response> response) {
		assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
	}

	private ExtractableResponse<Response> 컬럼을_삭제한다() {
		return 칼럼_삭제_요청(1L);
	}

	private void 삭제된_컬럼을_검증한다(ExtractableResponse<Response> response) {
		assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
	}
}
