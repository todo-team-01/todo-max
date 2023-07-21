package org.codesquad.todo.domain.column;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.codesquad.todo.util.DatabaseCleaner;
import org.codesquad.todo.util.RepositoryTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

@RepositoryTest
public class ColumnRepositoryTest {
	private ColumnRepository columnRepository;
	private DatabaseCleaner databaseCleaner;

	@Autowired
	public ColumnRepositoryTest(JdbcTemplate jdbcTemplate) {
		this.columnRepository = new ColumnRepository(jdbcTemplate);
		this.databaseCleaner = new DatabaseCleaner(jdbcTemplate);
	}

	@BeforeEach
	void setUp() {
		databaseCleaner.execute();
	}

	@DisplayName("DB에 저장된 칼럼들을 리스트 형태로 반환한다.")
	@Test
	void findAll() {
		// given
		columnRepository.save(new Column(null, "오늘할 일"));
		columnRepository.save(new Column(null, "해야할 일"));


		// when
		List<Column> columns = columnRepository.findAll();

		// then
		assertThat(columns.size()).isEqualTo(2);
	}

	@DisplayName("DB에 이름이 있는 칼럼을 저장한다.")
	@Test
	void save() {
		// given
		Column column = new Column(null, "새로운_일");

		// when
		Long actual = columnRepository.save(column);

		// then
		Assertions.assertAll(
			() -> assertThat(actual).isEqualTo(1L)
		);
	}

	@DisplayName("DB에 칼럼을 저장하고 저장한 칼럼이 있는지 확인한다.")
	@Test
	void exist() {
		// given
		Long savedId = columnRepository.save(new Column(null, "새로운 일"));

		// when
		Boolean exist = columnRepository.exist(savedId);

		// then
		assertThat(exist).isTrue();
	}

	@DisplayName("컬럼을 수정할 때 수정할 컬럼 정보들을 입력하면 수정이 되고 수정한 컬럼들의 수를 반환한다.")
	@Test
	void update() {
		// given
		Long savedId = columnRepository.save(new Column(null, "새로운 일"));
		Column updateColumn = new Column(savedId, "변경된 오늘 할일");

		// when
		int actual = columnRepository.update(updateColumn);

		// then
		assertThat(actual).isEqualTo(1);
	}

	@DisplayName("캄럼을 삭제할 때 칼럼 아이디를 입력하면 칼럼이 삭제되고 삭제된 칼럼들의 수를 반환한다.")
	@Test
	void delete() {
		// given
		Long savedId = columnRepository.save(new Column(null, "새로운 일"));

		// when
		int actual = columnRepository.delete(1L);

		// then
		assertThat(actual).isEqualTo(1);
	}
}
