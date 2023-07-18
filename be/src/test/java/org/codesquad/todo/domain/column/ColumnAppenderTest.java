package org.codesquad.todo.domain.column;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.codesquad.todo.util.ServiceTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

@ServiceTest
class ColumnAppenderTest {
	@InjectMocks
	private ColumnAppender columnAppender;

	@Mock
	private ColumnRepository columnRepository;

	@DisplayName("컬럼 정보를 받아 저장한다.")
	@Test
	void append() {
		// given
		Column column = new Column(null, "test");
		given(columnRepository.save(any())).willReturn(1L);

		// when
		Long actual = columnAppender.append(column);

		// then
		Assertions.assertAll(
			() -> assertThat(actual).isEqualTo(1L)
		);
	}
}
