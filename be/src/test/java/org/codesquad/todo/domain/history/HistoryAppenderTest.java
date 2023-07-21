package org.codesquad.todo.domain.history;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.codesquad.todo.util.ServiceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

@ServiceTest
class HistoryAppenderTest {
	@InjectMocks
	private HistoryAppender historyAppender;

	@Mock
	private HistoryRepository historyRepository;

	@DisplayName("히스토리 정보를 받아 저장한다.")
	@Test
	void append() {
		// given
		History history = new History("테스트");
		given(historyRepository.save(any())).willReturn(1L);

		// when
		Long actual = historyAppender.append(history);

		// then
		assertThat(actual).isEqualTo(1L);
	}
}
