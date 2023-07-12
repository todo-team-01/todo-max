package org.codesquad.todo.domain.card;

import static org.assertj.core.api.Assertions.*;

import javax.sql.DataSource;

import org.codesquad.todo.util.DatabaseCleaner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;

@JdbcTest
public class CardRepositoryTest {
	private CardRepository cardRepository;
	private DatabaseCleaner databaseCleaner;

	@Autowired
	public CardRepositoryTest(DataSource dataSource) {
		this.cardRepository = new CardRepository(dataSource);
		this.databaseCleaner = new DatabaseCleaner(dataSource);
	}

	@BeforeEach
	void setUp() {
		databaseCleaner.execute();
	}

	@DisplayName("카드를 저장할 때 카드의 정보들을 입력하면 저장하고 카드를 반환한다.")
	@Test
	void save() {
		// given
		Card card = new Card(null, "Git 사용해 보기", "add, commit", 1L, 1L, null);

		// when
		Card actual = cardRepository.save(card);

		// then
		assertThat(actual.getId()).isEqualTo(1L);
		assertThat(actual).usingRecursiveComparison()
			.ignoringFields("id")
			.isEqualTo(card);
	}

	@DisplayName("카드를 조회할 때 카드의 아이디를 입력하면 카드를 반환한다.")
	@Test
	void findById() {
		// given
		Card card = new Card(null, "Git 사용해 보기", "add, commit", 1L, 1L, null);
		Card savedCard = cardRepository.save(card);

		// when
		Card actual = cardRepository.findById(savedCard.getId())
			.orElseThrow();

		// then
		assertThat(actual.getId()).isEqualTo(1L);
		assertThat(actual).usingRecursiveComparison()
			.ignoringFields("id")
			.isEqualTo(card);
	}

	@DisplayName("카드를 수정할 때 수정할 카드 정보들을 입력하면 수정이 되고 수정한 카드들의 수를 반환한다.")
	@Test
	void modify() {
		//given
		Card card = new Card(null, "변경 전 타이틀", "변경 전 내용", 1L, 1L, null);
		Card savedCard = cardRepository.save(card);

		//when
		Card newCard = new Card(1L, "변경 후 타이틀", "변경 후 내용", 1L, 1L, null);
		int updatedCount = cardRepository.update(newCard);

		//then
		Card findCard = cardRepository.findById(savedCard.getId()).orElseThrow();
		assertThat(updatedCount).isEqualTo(1L);
		assertThat(findCard).usingRecursiveComparison()
			.isEqualTo(newCard);
	}
}
