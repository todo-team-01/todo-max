package org.codesquad.todo.controller;

import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.codesquad.todo.controller.dto.CardModifyRequestDto;
import org.codesquad.todo.controller.dto.CardMoveRequestDto;
import org.codesquad.todo.controller.dto.CardSaveRequestDto;
import org.codesquad.todo.domain.card.CardService;
import org.codesquad.todo.util.ControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@ControllerTest(CardController.class)
public class CardControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private CardService cardService;

	@DisplayName("카드 저장 요청을 성공적으로 처리한다.")
	@Test
	void saveCard() throws Exception {
		// given
		given(cardService.saveCard(any())).willReturn(1L);
		CardSaveRequestDto request = new CardSaveRequestDto(1L, "카드 저장 테스트 제목", "카드 저장 테스트 내용");

		// when then
		mockMvc.perform(post("/api/cards")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
			.andDo(MockMvcRestDocumentation.document("cards/save",
				Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
				Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
				requestFields(
					fieldWithPath("columnId").description("칼럼 아이디"),
					fieldWithPath("cardTitle").description("카드 제목"),
					fieldWithPath("cardContent").description("카드 내용")
				),
				responseFields(
					fieldWithPath("cardId").description("카드 아이디")
				)))
			.andExpect(status().isCreated());
	}

	@DisplayName("카드 수정 요청을 성공적으로 처리한다.")
	@Test
	void modifyCard() throws Exception {
		// given
		given(cardService.modifyCard(any(), any(), any())).willReturn(1);
		CardModifyRequestDto request = new CardModifyRequestDto("제목 수정 테스트", "내용 수정 테스트");

		// when then
		mockMvc.perform(put("/api/cards/1")
				.content(objectMapper.writeValueAsString(request))
				.contentType(MediaType.APPLICATION_JSON))
			.andDo(MockMvcRestDocumentation.document("cards/modify",
				Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
				Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
				requestFields(
					fieldWithPath("changedCardTitle").description("수정한 카드 제목"),
					fieldWithPath("changedCardContent").description("수정한 카드 내용")
				)))
			.andExpect(status().is2xxSuccessful());
	}

	@DisplayName("카드 이동 요청을 성공적으로 처리한다.")
	@Test
	void moveCard() throws Exception {
		// given
		given(cardService.moveCard(any(), any(), any(), any())).willReturn(1);
		CardMoveRequestDto request = new CardMoveRequestDto(1L, 3L, 2L);

		// when then
		mockMvc.perform(patch("/api/cards/1")
				.content(objectMapper.writeValueAsString(request))
				.contentType(MediaType.APPLICATION_JSON))
			.andDo(MockMvcRestDocumentation.document("cards/move",
				Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
				Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
				requestFields(
					fieldWithPath("changedColumnId").description("이동한 칼럼 아이디"),
					fieldWithPath("topCardId").description("위에 있는 카드 아이디"),
					fieldWithPath("bottomCardId").description("아래에 있는 카드 아이디")
				)))
			.andExpect(status().is2xxSuccessful());
	}

	@DisplayName("카드 삭제 요청을 성공적으로 처리한다.")
	@Test
	void deleteCard() throws Exception {
		// given
		given(cardService.deleteCardById(any())).willReturn(1);

		// when then
		mockMvc.perform(delete("/api/cards/1"))
			.andDo(MockMvcRestDocumentation.document("cards/delete",
				Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
				Preprocessors.preprocessResponse(Preprocessors.prettyPrint())))
			.andExpect(status().is2xxSuccessful());
	}
}
