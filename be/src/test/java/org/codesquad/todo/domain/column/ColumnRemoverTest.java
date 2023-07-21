package org.codesquad.todo.domain.column;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ColumnRemoverTest {

	@InjectMocks
	private ColumnRemover columnRemover;

	@Mock
	private ColumnRepository columnRepository;

	@DisplayName("칼럼 id를 받아 해당 칼럼 정보를 삭제한다.")
	@Test
	void deleteColumn() {
		// given
		Long id = 1L;
		given(columnRepository.delete(id)).willReturn(1);

		// when
		int updatedCount = columnRemover.deleteColumn(id);

		// then
		assertThat(updatedCount).isEqualTo(1);
	}
}
