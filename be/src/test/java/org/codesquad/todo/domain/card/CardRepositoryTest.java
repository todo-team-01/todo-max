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

	@DisplayName("카드를 저장한다.")
	@Test
	void save() {
		// given
		Card card = new Card(null, "Git 사용해 보기", "add, commit", 1L, 1L, null);

		// when
		Card savedCard = cardRepository.save(card);

		// then
		assertThat(savedCard.getId()).isEqualTo(1L);
		assertThat(savedCard.getTitle()).isEqualTo(card.getTitle());
		assertThat(savedCard.getContent()).isEqualTo(card.getContent());
		assertThat(savedCard.getMemberId()).isEqualTo(card.getMemberId());
		assertThat(savedCard.getPrevCardId()).isEqualTo(card.getPrevCardId());
	}

	@DisplayName("해당하는 ID의 카드가 존재한다면 삭제하고 1을 반환한다.")
	@Test
	void deleteExistentCard() {
		// given
		Card card = new Card(null, "Git 사용해 보기", "add, commit", 1L, 1L, null);
		Card savedCard = cardRepository.save(card);

		// when
		int deleted = cardRepository.delete(savedCard.getId());

		// then
		assertThat(deleted).isEqualTo(1);
	}

	@DisplayName("해당하는 ID의 카드가 존재하지 않는 경우 0을 반환한다.")
	@Test
	void deleteNonexistentCard() {
		// given
		Card card = new Card(null, "Git 사용해 보기", "add, commit", 1L, 1L, null);
		cardRepository.save(card);

		// when
		int deleted = cardRepository.delete(2L);

		// then
		assertThat(deleted).isEqualTo(0);
	}

}
