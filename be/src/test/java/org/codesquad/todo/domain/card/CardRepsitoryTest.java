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
public class CardRepsitoryTest {
	private CardRepository cardRepository;
	private DatabaseCleaner databaseCleaner;

	@Autowired
	public CardRepsitoryTest(DataSource dataSource) {
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
}
