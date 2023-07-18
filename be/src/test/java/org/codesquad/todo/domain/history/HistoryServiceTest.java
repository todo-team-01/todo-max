package org.codesquad.todo.domain.history;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.List;

import org.codesquad.todo.util.ServiceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

@ServiceTest
class HistoryServiceTest {
	@InjectMocks
	private HistoryService historyService;

	@Mock
	private HistoryReader historyReader;

	@Mock
	private HistoryRemover historyRemover;

	@DisplayName("DB에 저장된 history 중 is_deleted column이 false인 history 읽어온다.")
	@Test
	void findAll() {
		// given
		given(historyReader.findAll()).willReturn(
			List.of(new History("첫 번째 내용)"),
				new History("두 번째 내용)"),
				new History("세 번째 내용)")));

		// when
		List<History> histories = historyService.findAll();

		// then
		assertThat(histories.size()).isEqualTo(3);
	}

	@DisplayName("DB에 저장된 모든 history column is_deleted를  true로 바꾼다.")
	@Test
	void deleteAll() {
		// given
		given(historyRemover.deleteHistory()).willReturn(1);

		// when
		int actual = historyRemover.deleteHistory();

		// then
		assertThat(actual).isEqualTo(1);
	}
}
