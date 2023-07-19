package org.codesquad.todo.domain.history;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.codesquad.todo.util.ServiceTest;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

@ServiceTest
class HistoryRemoverTest {

	@InjectMocks
	private HistoryRemover historyRemover;

	@Mock
	private HistoryRepository historyRepository;

	@Test
	void deleteHistory() {
		// given
		given(historyRepository.deleteAll()).willReturn(1);

		// when
		int deletedCount = historyRemover.deleteHistory();

		// then
		assertThat(deletedCount).isEqualTo(1);
	}
}
