package org.codesquad.todo.domain.column;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.codesquad.todo.util.ServiceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

@ServiceTest
class ColumnManagerTest {
	@InjectMocks
	ColumnManager columnManager;

	@Mock
	ColumnRepository columnRepository;

	@Mock
	ColumnReader columnReader;

	@DisplayName("칼럼이 DB에 있는지 확인 후 해당 칼럼의 내용을 업데이트 한다.")
	@Test
	void updateColumn() {
		// given
		Long id = 1L;
		Column column = new Column(id, "이름");
		given(columnReader.findById(any())).willReturn(column);
		given(columnRepository.update(any())).willReturn(1);

		// when
		int changedCount = columnManager.updateColumn(column);

		// then
		assertThat(changedCount).isEqualTo(1);
	}
}
