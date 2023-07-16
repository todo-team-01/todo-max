package org.codesquad.todo.domain.card;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.codesquad.todo.util.DatabaseCleaner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
public class CardRepositoryTest {
	private CardRepository cardRepository;
	private DatabaseCleaner databaseCleaner;
	private Card SECOND_CARD;
	private Card FIRST_CARD;

	@Autowired
	public CardRepositoryTest(JdbcTemplate jdbcTemplate) {
		this.cardRepository = new CardRepository(jdbcTemplate);
		this.databaseCleaner = new DatabaseCleaner(jdbcTemplate);
	}

	@BeforeEach
	void setUp() {
		databaseCleaner.execute();
		FIRST_CARD = cardRepository.save(new Card(null, "카드 등록 구현", "카드 등록", 1L, 1L, 2L));
		SECOND_CARD = cardRepository.save(new Card(null, "Git 사용해 보기", "add, commit", 1L, 1L, null));
	}

	@DisplayName("카드를 저장할 때 카드의 정보들을 입력하면 저장하고 카드를 반환한다.")
	@Test
	void save() {
		// given
		Card saveCard = new Card(null, "테스트", "내용", 1L, 1L, 2L);

		// when
		Card actual = cardRepository.save(saveCard);

		// then
		assertThat(actual.getId()).isEqualTo(3L);
		assertThat(actual).usingRecursiveComparison()
			.ignoringFields("id")
			.isEqualTo(saveCard);
	}

	@DisplayName("카드를 조회할 때 카드의 아이디를 입력하면 카드를 반환한다.")
	@Test
	void findById() {
		// given

		// when
		Card actual = cardRepository.findById(SECOND_CARD.getId())
			.orElseThrow();

		// then
		assertThat(actual.getId()).isEqualTo(2L);
		assertThat(actual).usingRecursiveComparison()
			.isEqualTo(SECOND_CARD);
	}

	@DisplayName("카드 전체를 조회할 때 컬럼 아이디를 입력하면 해당 컬럼 아이디를 가지고 있는 카드들을 반환한다.")
	@Test
	void findAllByColumnId() {
		// given
		Long columnId = 1L;

		// when
		List<Card> actual = cardRepository.findAllByColumnId(columnId);

		// then
		assertThat(actual.size()).isEqualTo(2L);
		assertThat(actual).usingRecursiveComparison()
			.isEqualTo(List.of(FIRST_CARD, SECOND_CARD));
	}

	@DisplayName("카드를 수정할 때 수정할 카드 정보들을 입력하면 수정이 되고 수정한 카드들의 수를 반환한다.")
	@Test
	void modify() {
		//given

		//when
		Card updateCard = new Card(SECOND_CARD.getId(), "변경 후 타이틀", "변경 후 내용", 1L, 1L, null);
		int updatedCount = cardRepository.update(updateCard);

		//then
		Card findCard = cardRepository.findById(SECOND_CARD.getId()).orElseThrow();
		assertThat(updatedCount).isEqualTo(1L);
		assertThat(findCard).usingRecursiveComparison()
			.isEqualTo(updateCard);
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
		// when
		int deleted = cardRepository.delete(3L);

		// then
		assertThat(deleted).isEqualTo(0);
	}

	@DisplayName("삭제하려는 카드와 자식 카드를 찾는다.")
	@Test
	void findWithChildById() {
		// given

		// when
		List<Card> cards = cardRepository.findWithChildById(SECOND_CARD.getId());

		// then
		assertThat(cards).hasSize(2)
			.extracting("id", "prevCardId")
			.containsExactlyInAnyOrder(
				tuple(2L, null),
				tuple(1L, 2L)
			);
	}

	@DisplayName("자식 카드가 없는 카드를 찾는다.")
	@Test
	void findWithNoChildById() {
		// given

		// when
		List<Card> cards = cardRepository.findWithChildById(FIRST_CARD.getId());

		// then
		assertThat(cards).hasSize(1)
			.extracting("id", "prevCardId")
			.containsExactlyInAnyOrder(
				tuple(1L, 2L)
			);
	}

	@DisplayName("최상단에 위치한 카드를 삭제할 때 자신의 아래에 있는 카드의 prevCardId를 null로 변경한다.")
	@Test
	void updateBeforeDeleteWithNull() {
		// given

		// when
		int updated = cardRepository.updateBeforeDelete(FIRST_CARD.getId(), SECOND_CARD.getPrevCardId());
		List<Card> cards = cardRepository.findAll();

		// then
		assertThat(updated).isEqualTo(1);
		assertThat(cards).hasSize(2)
			.extracting("id", "prevCardId")
			.containsExactlyInAnyOrder(
				tuple(1L, null),
				tuple(2L, null)
			);
	}

	@DisplayName("자식 카드의 부모 카드를 삭제하려는 카드의 부모 카드로 변경한다.")
	@Test
	void updateBeforeDelete() {
		// given
		Card card3 = new Card(null, "변경 후 타이틀", "변경 후 내용", 1L, 1L, null);
		cardRepository.save(card3);
		SECOND_CARD = SECOND_CARD.createInstanceWithPrevId(3L);
		cardRepository.update(SECOND_CARD);

		// when
		int updated = cardRepository.updateBeforeDelete(FIRST_CARD.getId(), SECOND_CARD.getPrevCardId());
		List<Card> cards = cardRepository.findAll();

		// then
		assertThat(updated).isEqualTo(1);
		assertThat(cards).hasSize(3)
			.extracting("id", "prevCardId")
			.containsExactlyInAnyOrder(
				tuple(1L, 3L),
				tuple(2L, 3L),
				tuple(3L, null)
			);
	}

}
