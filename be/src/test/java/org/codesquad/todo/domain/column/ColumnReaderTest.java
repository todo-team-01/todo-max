package org.codesquad.todo.domain.column;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.List;
import java.util.Optional;

import org.codesquad.todo.config.ColumnNotFoundException;
import org.codesquad.todo.util.ServiceTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

@ServiceTest
class ColumnReaderTest {
	@InjectMocks
	private ColumnReader columnReader;

	@Mock
	private ColumnRepository columnRepository;

	@DisplayName("컬럼 조회할 때 컬럼 아이디를 입력 받으면 컬럼을 반환한다.")
	@Test
	void findById() {
		// given
		Long id = 1L;
		Column column = new Column(1L, "오늘 할일");
		given(columnRepository.findById(any())).willReturn(Optional.of(column));

		// when
		Column actual = columnReader.findById(id);

		// then
		assertThat(actual).usingRecursiveComparison()
			.isEqualTo(column);
	}

	@DisplayName("컬럼 조회할 때 저장 되어 있지 않는 컬럼 아이디를 입력 받으면 에러를 반환한다.")
	@Test
	void findByIdFail() {
		// given
		Long id = 1L;
		given(columnRepository.findById(any())).willReturn(Optional.empty());

		// when

		// then
		Assertions.assertThrows(ColumnNotFoundException.class, () -> columnReader.findById(id));
	}

	@DisplayName("컬럼들을 조회하면 컬럼들을 반환한다.")
	@Test
	void findAll() {
		// given
		List<Column> columns = List.of(
			new Column(1L, "내일 할 일"),
			new Column(2L, "오늘 할일"),
			new Column(3L, "완료한 일"));
		given(columnRepository.findAll()).willReturn(columns);

		// when
		List<Column> actual = columnReader.findAll();

		// then
		Assertions.assertAll(
			() -> assertThat(actual.size()).isEqualTo(3L),
			() -> assertThat(actual).usingRecursiveComparison()
				.isEqualTo(columns)
		);
	}
}
