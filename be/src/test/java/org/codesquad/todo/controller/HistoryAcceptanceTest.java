package org.codesquad.todo.controller;

import static org.assertj.core.api.Assertions.*;
import static org.codesquad.todo.controller.util.CardSteps.*;
import static org.codesquad.todo.controller.util.ColumnSteps.*;
import static org.codesquad.todo.controller.util.HistorySteps.*;

import java.util.List;

import org.codesquad.todo.controller.dto.CardSaveRequestDto;
import org.codesquad.todo.controller.dto.ColumnSaveRequestDTO;
import org.codesquad.todo.controller.dto.HistoryResponseDto;
import org.codesquad.todo.util.AcceptanceTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

class HistoryAcceptanceTest extends AcceptanceTest {

	@DisplayName("모든 사용자 기록을 조회한다.")
	@Test
	void findAll() {
		// given
		칼럼과_카드를_생성한다();

		// when
		var response = 히스토리_전체_조회_요청();

		// then
		조회된_사용자_목록들을_검증한다(response);
	}

	@DisplayName("모든 사용자 기록을 삭제한다.")
	@Test
	void deleteAll() {
		// given
		칼럼과_카드를_생성한다();

		// when
		var response = 히스토리_전체_삭제_요청();

		// then
		삭제된_사용자_목록들을_검증한다(response);
	}

	private void 칼럼과_카드를_생성한다() {
		ColumnSaveRequestDTO columnSaveRequestDTO = new ColumnSaveRequestDTO(해야할_일_컬럼_이름);
		칼럼_생성_요청(columnSaveRequestDTO);
		CardSaveRequestDto cardSaveRequestDto = new CardSaveRequestDto(1L, "Git 공부하기", "stash");
		CardSaveRequestDto cardSaveRequestDto2 = new CardSaveRequestDto(1L, "테스트 제목", "테스트 내용");
		카드_생성_요청(cardSaveRequestDto);
		카드_생성_요청(cardSaveRequestDto2);
	}

	private void 조회된_사용자_목록들을_검증한다(ExtractableResponse<Response> response) {
		List<String> historyContents = response.jsonPath().getList("historyContent", String.class);

		Assertions.assertAll(
			() -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
			() -> assertThat(historyContents).containsAnyOf(
				"Git 공부하기을(를) 해야할 일에서 등록하였습니다.",
				"테스트 제목을(를) 해야할 일에서 등록하였습니다."
			)
		);
	}

	private void 삭제된_사용자_목록들을_검증한다(ExtractableResponse<Response> response) {
		List<HistoryResponseDto> findAll = 히스토리_전체_조회_요청().jsonPath().getList(".", HistoryResponseDto.class);

		Assertions.assertAll(
			() -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
			() -> assertThat(findAll.size()).isEqualTo(0)
		);
	}
}
