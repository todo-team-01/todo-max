package org.codesquad.todo.domain.column;

import static org.mockito.BDDMockito.*;

import org.codesquad.todo.util.ServiceTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

@ServiceTest
public class ColumnValidatorTest {
	@InjectMocks
	private ColumnValidator columnValidator;

	@Mock
	ColumnRepository columnRepository;

	@DisplayName("찾고 싶은 칼럼이 DB에 존재하는지 여부를 확인한다.")
	@Test
	void exist() {

		// given
		Column 내일_할일 = new Column(null, "내일 할 일");
		given(columnRepository.exist(any())).willReturn(true);

		// when

		// then
		Assertions.assertDoesNotThrow(() -> columnValidator.exist(1L));
	}
}
