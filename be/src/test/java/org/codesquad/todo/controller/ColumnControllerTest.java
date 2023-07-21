package org.codesquad.todo.controller;

import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.codesquad.todo.controller.dto.ColumnSaveRequestDTO;
import org.codesquad.todo.controller.dto.ColumnUpdateRequestDTO;
import org.codesquad.todo.domain.card.Card;
import org.codesquad.todo.domain.column.ColumnService;
import org.codesquad.todo.domain.column.ColumnWithCard;
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

@ControllerTest(ColumnController.class)
public class ColumnControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private ColumnService columnService;

	@DisplayName("칼럼을 생성한다.")
	@Test
	void saveColumn() throws Exception {
		// given
		given(columnService.save(any())).willReturn(1L);
		ColumnSaveRequestDTO columnSaveRequestDTO = new ColumnSaveRequestDTO("해야할 일");

		// when then
		mockMvc.perform(post("/api/columns")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(columnSaveRequestDTO)))
			.andDo(MockMvcRestDocumentation.document("columns/save",
				Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
				Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
				requestFields(
					fieldWithPath("columnName").description("칼럼 이름")
				),
				responseFields(
					fieldWithPath("columnId").description("칼럼 아이디")
				)))
			.andExpect(status().is2xxSuccessful());
	}

	@DisplayName("모든 칼럼 및 카드들을 조회한다.")
	@Test
	void showColumnWithCards() throws Exception {
		// given
		List<Card> cards = List.of(
			new Card(2L, "프로젝트 계획하기", "API 명세서", 1L, 2048L),
			new Card(1L, "Git 공부하기", "add, commit, push", 1L, 1024L)
		);
		List<Card> cards2 = List.of(
			new Card(3L, "그라운드 룰 정하기", "코어타임을 지키자", 2L, 1024L)
		);
		List<ColumnWithCard> columnWithCards = List.of(
			new ColumnWithCard(1L, "해야할 일", cards),
			new ColumnWithCard(2L, "하고 있는 일", cards2));
		given(columnService.findAll()).willReturn(columnWithCards);

		// when then
		mockMvc.perform(get("/api/columns"))
			.andDo(MockMvcRestDocumentation.document("columns/findAll",
				Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
				Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
				responseFields(
					fieldWithPath("[].columnId").description("칼럼 아이디"),
					fieldWithPath("[].columnName").description("칼럼 이름"),
					fieldWithPath("[].cards[].cardId").description("카드 아이디"),
					fieldWithPath("[].cards[].cardTitle").description("카드 제목"),
					fieldWithPath("[].cards[].cardContent").description("카드 내용")
				)))
			.andExpect(status().is2xxSuccessful());
	}

	@DisplayName("칼럼을 수정한다.")
	@Test
	void updateColumn() throws Exception {
		// given
		ColumnUpdateRequestDTO columnUpdateRequestDTO = new ColumnUpdateRequestDTO("Git 공부하기");
		given(columnService.update(any())).willReturn(1);

		// when then
		mockMvc.perform(put("/api/columns/{id}", 1L)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(columnUpdateRequestDTO)))
			.andDo(MockMvcRestDocumentation.document("columns/update",
				Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
				Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
				requestFields(
					fieldWithPath("changedColumnName").description("변경할 칼럼 이름")
				)))
			.andExpect(status().is2xxSuccessful());
	}

	@DisplayName("칼럼을 삭제한다.")
	@Test
	void deleteColumn() throws Exception {
		// given
		given(columnService.delete(any())).willReturn(1);

		// when then
		mockMvc.perform(delete("/api/columns/{id}", 1L))
			.andDo(MockMvcRestDocumentation.document("columns/delete",
				Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
				Preprocessors.preprocessResponse(Preprocessors.prettyPrint())))
			.andExpect(status().is2xxSuccessful());
	}
}
