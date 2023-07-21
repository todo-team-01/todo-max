package org.codesquad.todo.controller;

import static org.assertj.core.api.Assertions.*;
import static org.codesquad.todo.controller.util.CardSteps.*;
import static org.codesquad.todo.controller.util.ColumnSteps.*;
import static org.codesquad.todo.controller.util.HistorySteps.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.codesquad.todo.config.CardNotFoundException;
import org.codesquad.todo.config.ColumnNotFoundException;
import org.codesquad.todo.controller.dto.CardModifyRequestDto;
import org.codesquad.todo.controller.dto.CardMoveRequestDto;
import org.codesquad.todo.controller.dto.CardSaveRequestDto;
import org.codesquad.todo.controller.dto.CardSaveResponseDto;
import org.codesquad.todo.controller.dto.ColumnSaveRequestDTO;
import org.codesquad.todo.util.AcceptanceTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class CardAcceptanceTest extends AcceptanceTest {

	@DisplayName("카드를 생성한다.")
	@Test
	void saveCard() {
		// given
		칼럼을_생성한다();

		// when
		var response = 카드를_생성한다(1L);

		// then
		생성된_카드를_검증한다(response);
	}

	@DisplayName("카드 생성할 때 칼럼 아이디가 없는 경우 에러를 반환한다.")
	@Test
	void saveCardFail() {
		// given

		// when
		var response = 카드를_생성한다(1L);

		// then
		칼럼_아이디가_존재하지_않아서_실패한_요청을_검증한다(response);
	}

	@DisplayName("카드를 수정한다.")
	@Test
	void modifyCard() {
		// given
		칼럼과_카드를_생성한다();

		// when
		var response = 카드를_수정한다();

		// then
		수정된_카드를_검증한다(response);
	}

	@DisplayName("카드 수정할 때 카드 아이디가 없는 경우 에러를 반환한다.")
	@Test
	void modifyCardFail() {
		// given

		// when
		var response = 카드를_수정한다();

		// then
		카드_아이디가_존재하지_않아서_실패한_요청을_검증한다(response);
	}

	@DisplayName("해당하는 ID의 카드를 삭제한다.")
	@Test
	void deleteCard() {
		// given
		칼럼과_카드를_생성한다();

		// when
		var response = 카드_삭제_요청(1L);

		// then
		삭제된_카드를_검증한다(response);
	}

	@DisplayName("해당하는 ID의 카드를 요청 위치로 이동한다.")
	@Test
	void moveCard() {
		// given
		칼럼과_카드들을_생성한다();

		// when
		var response = 카드를_이동한다();

		// then
		이동한_카드를_검증한다(response);
	}

	@DisplayName("카드 이동할 때 칼럼 아이디가 없는 경우 에러를 반환한다.")
	@Test
	void moveCardFailWithNoColumn() {
		// given
		칼럼과_카드들을_생성한다();

		// when
		var response = 컬럼_아이디가_존재하지_않는_카드를_이동한다();

		// then
		칼럼_아이디가_존재하지_않아서_실패한_요청을_검증한다(response);
	}

	@DisplayName("카드 이동할 때 카드 아이디가 없는 경우 에러를 반환한다.")
	@Test
	void moveCardFailWithNoCard() {
		// given
		칼럼과_카드를_생성한다();

		// when
		var response = 카드를_이동한다();

		// then
		카드_아이디가_존재하지_않아서_실패한_요청을_검증한다(response);
	}

	private void 칼럼을_생성한다() {
		ColumnSaveRequestDTO columnSaveRequestDTO = new ColumnSaveRequestDTO(해야할_일_컬럼_이름);
		칼럼_생성_요청(columnSaveRequestDTO);
	}

	private ExtractableResponse<Response> 카드를_생성한다(Long columnId) {
		CardSaveRequestDto cardSaveRequestDto = new CardSaveRequestDto(columnId, "Git 공부하기", "stash");
		return 카드_생성_요청(cardSaveRequestDto);
	}

	private void 칼럼_아이디가_존재하지_않아서_실패한_요청을_검증한다(ExtractableResponse<Response> response) {
		String message = response.jsonPath().getString("message");

		assertAll(
			() -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
			() -> assertThat(message).isEqualTo(ColumnNotFoundException.MESSAGE)
		);
	}

	private void 생성된_카드를_검증한다(ExtractableResponse<Response> response) {
		CardSaveResponseDto responseBody = response.as(CardSaveResponseDto.class);
		List<String> contents = 히스토리_전체_조회_요청().jsonPath().getList("historyContent", String.class);

		assertAll(
			() -> assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value()),
			() -> assertThat(responseBody.getCardId()).isEqualTo(1L),
			() -> assertThat(contents).contains("Git 공부하기을(를) 해야할 일에서 등록하였습니다.")
		);
	}

	private void 칼럼과_카드를_생성한다() {
		칼럼을_생성한다();
		카드를_생성한다(1L);
	}

	private ExtractableResponse<Response> 카드를_수정한다() {
		CardModifyRequestDto cardModifyRequestDto = new CardModifyRequestDto("Git 공부하기22", "stash22");
		return 카드_수정_요청(1L, cardModifyRequestDto);
	}

	private void 수정된_카드를_검증한다(ExtractableResponse<Response> response) {
		List<String> contents = 히스토리_전체_조회_요청().jsonPath().getList("historyContent", String.class);

		assertAll(
			() -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
			() -> assertThat(contents).contains("Git 공부하기22을(를) 변경하였습니다.")
		);
	}

	private void 칼럼과_카드들을_생성한다() {
		칼럼_생성_요청(new ColumnSaveRequestDTO("해야할 일"));
		칼럼_생성_요청(new ColumnSaveRequestDTO("하고 있는 일"));
		카드_생성_요청(new CardSaveRequestDto(2L, "Git 공부하기", "test1"));
		카드_생성_요청(new CardSaveRequestDto(2L, "모니터링 구축하기", "test2"));
		카드_생성_요청(new CardSaveRequestDto(1L, "로그인 구현하기", "test3"));
		카드_생성_요청(new CardSaveRequestDto(1L, "배포 하기", "test4"));
	}

	private ExtractableResponse<Response> 카드를_이동한다() {
		CardMoveRequestDto cardMoveRequestDto = new CardMoveRequestDto(1L, 4L, 3L);
		return 카드_이동_요청(2L, cardMoveRequestDto);
	}

	private void 이동한_카드를_검증한다(ExtractableResponse<Response> response) {
		List<String> contents = 히스토리_전체_조회_요청().jsonPath().getList("historyContent", String.class);

		Assertions.assertAll(
			() -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
			() -> assertThat(contents).contains("모니터링 구축하기을(를) 하고 있는 일에서 해야할 일으로 이동하였습니다.")
		);
	}

	private ExtractableResponse<Response> 컬럼_아이디가_존재하지_않는_카드를_이동한다() {
		CardMoveRequestDto cardMoveRequestDto = new CardMoveRequestDto(3L, 4L, 3L);
		return 카드_이동_요청(2L, cardMoveRequestDto);
	}

	private void 카드_아이디가_존재하지_않아서_실패한_요청을_검증한다(ExtractableResponse<Response> response) {
		String message = response.jsonPath().getString("message");
		assertAll(
			() -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
			() -> assertThat(message).isEqualTo(CardNotFoundException.MESSAGE)
		);
	}

	private void 삭제된_카드를_검증한다(ExtractableResponse<Response> response) {
		List<String> contents = 히스토리_전체_조회_요청().jsonPath().getList("historyContent", String.class);

		assertAll(
			() -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
			() -> assertThat(contents).contains("Git 공부하기을(를)이 삭제 되었습니다.")
		);
	}
}
