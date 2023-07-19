package org.codesquad.todo.domain.history;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.stream.Collectors;

import org.codesquad.todo.util.DatabaseCleaner;
import org.codesquad.todo.util.RepositoryTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

@RepositoryTest
class HistoryRepositoryTest {
	private DatabaseCleaner databaseCleaner;
	private HistoryRepository historyRepository;

	@Autowired
	public HistoryRepositoryTest(JdbcTemplate jdbcTemplate) {
		this.databaseCleaner = new DatabaseCleaner(jdbcTemplate);
		this.historyRepository = new HistoryRepository(jdbcTemplate);
	}

	@BeforeEach
	void setUp() {
		databaseCleaner.execute();
	}

	@Test
	void save() {
		// given
		History history = new History("테스트 내용");

		// when
		Long savedHistory = historyRepository.save(history);

		// then
		assertThat(savedHistory).isEqualTo(1L);
	}

	@Test
	void findAll() {
		// given
		historyRepository.save(new History("첫 번째 테스트 내용"));
		historyRepository.save(new History("두 번째 테스트 내용"));
		historyRepository.save(new History("세 번째 테스트 내용"));

		// when
		List<History> histories = historyRepository.findAll();

		// then
		assertThat(histories.stream()
			.map(History::getContent)
			.collect(Collectors.toUnmodifiableList()))
			.containsExactly("세 번째 테스트 내용", "두 번째 테스트 내용", "첫 번째 테스트 내용");
	}

	@Test
	void deleteAll() {
		// given
		historyRepository.save(new History("첫 번째 테스트 내용"));
		historyRepository.save(new History("첫 번째 테스트 내용"));

		// when
		int deletedRowCount = historyRepository.deleteAll();

		// then
		assertThat(deletedRowCount).isEqualTo(2);
	}
}
