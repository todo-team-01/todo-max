package org.codesquad.todo.domain.history;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDateTime;
import java.util.List;

import org.codesquad.todo.util.ServiceTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

@ServiceTest
class HistoryReaderTest {
	@InjectMocks
	private HistoryReader historyReader;

	@Mock
	private HistoryRepository historyRepository;

	@DisplayName("사용자 기록 전부 조회한다.")
	@Test
	void findAll() {
		// given
		List<History> histories = List.of(
			new History(1L, "Git 공부하기을(를) 해야할 일에서 등록하였습니다.", LocalDateTime.now(), false),
			new History(2L, "테스트 제목을(를) 해야할 일에서 등록하였습니다.", LocalDateTime.now(), false)
		);
		given(historyRepository.findAll()).willReturn(histories);

		// when
		List<History> actual = historyReader.findAll();

		// then
		Assertions.assertAll(
			() -> assertThat(actual.size()).isEqualTo(2),
			() -> assertThat(actual).usingRecursiveComparison()
				.isEqualTo(histories)
		);
	}
}
