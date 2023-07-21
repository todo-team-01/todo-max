package org.codesquad.todo.controller;

import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.List;

import org.codesquad.todo.domain.history.History;
import org.codesquad.todo.domain.history.HistoryService;
import org.codesquad.todo.util.ControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.test.web.servlet.MockMvc;

@ControllerTest(HistoryController.class)
public class HistoryControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private HistoryService historyService;

	@DisplayName("모든 사용자 기록을 조회한다.")
	@Test
	void findAll() throws Exception {
		// given
		History history = new History(1L, "Git 공부하기을(를) 해야할 일에서 등록하였습니다.", LocalDateTime.now(), false);
		History history2 = new History(2L, "테스트 제목을(를) 해야할 일에서 등록하였습니다.", LocalDateTime.now(), false);

		List<History> histories = List.of(history2, history);
		given(historyService.findAll()).willReturn(histories);

		// when then
		mockMvc.perform(get("/api/histories"))
			.andDo(MockMvcRestDocumentation.document("histories/findAll",
				Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
				Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
				responseFields(
					fieldWithPath("[].historyId").description("히스토리 아이디"),
					fieldWithPath("[].historyCreatedAt").description("히스토리 생성 날짜"),
					fieldWithPath("[].historyContent").description("히스토리 내용")
				)))
			.andExpect(status().is2xxSuccessful());
	}

	@DisplayName("모든 사용자 기록을 삭제한다.")
	@Test
	void deleteAll() throws Exception {
		// given

		// when then
		mockMvc.perform(delete("/api/histories"))
			.andDo(MockMvcRestDocumentation.document("histories/deleteAll",
				Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
				Preprocessors.preprocessResponse(Preprocessors.prettyPrint())))
			.andExpect(status().is2xxSuccessful());
	}
}
