package org.codesquad.todo.domain.card;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.codesquad.todo.util.DatabaseCleaner;
import org.codesquad.todo.util.RepositoryTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

@RepositoryTest
public class CardRepositoryTest {
	private CardRepository cardRepository;
	private DatabaseCleaner databaseCleaner;

	@Autowired
	public CardRepositoryTest(JdbcTemplate jdbcTemplate) {
		this.cardRepository = new CardRepository(jdbcTemplate);
		this.databaseCleaner = new DatabaseCleaner(jdbcTemplate);
	}

	@BeforeEach
	void setUp() {
		databaseCleaner.execute();
	}

	@DisplayName("카드를 저장하고 저장한 카드의 아이디를 반환한다.")
	@Test
	void save() {
		// given
		Card card = new Card(null, "테스트 제목", "내용", 1L, null);

		// when
		Long actual = cardRepository.save(card);

		// then
		assertThat(actual).isEqualTo(1L);
	}

	@DisplayName("카드를 조회할 때 카드의 아이디를 입력하면 카드를 반환한다.")
	@Test
	void findById() {
		// given
		Card card = new Card(null, "테스트", "내용", 1L, null);
		Long savedId = cardRepository.save(card);

		// when
		Card actual = cardRepository.findById(savedId)
			.orElseThrow();

		// then
		assertAll(
			() -> assertThat(actual.getId()).isEqualTo(1L),
			() -> assertThat(actual.getPosition()).isEqualTo(1024L),
			() -> assertThat(actual).usingRecursiveComparison()
				.ignoringFields("id", "position")
				.isEqualTo(card)
		);
	}

	@DisplayName("카드 전체를 조회할 때 컬럼 아이디를 입력하면 해당 컬럼 아이디를 가지고 있는 카드들을 반환한다.")
	@Test
	void findAllByColumnId() {
		// given
		Card card = new Card(null, "테스트", "내용", 1L, null);
		Card card2 = new Card(null, "테스트2", "내용2", 1L, null);
		Long savedId = cardRepository.save(card);
		Long savedId2 = cardRepository.save(card2);

		// when
		List<Card> actual = cardRepository.findAllByColumnId(1L);

		// then
		assertThat(actual.size()).isEqualTo(2L);
	}

	@DisplayName("카드를 수정할 때 수정할 카드 정보들을 입력하면 수정이 되고 수정한 카드들의 수를 반환한다.")
	@Test
	void modify() {
		//given
		Long savedId = cardRepository.save(new Card(null, "테스트", "내용", 1L, null));

		//when
		Card updateCard = new Card(savedId, "변경 후 타이틀", "변경 후 내용", 1L, 1L);
		int updatedCount = cardRepository.update(updateCard);

		//then
		Card findCard = cardRepository.findById(savedId).orElseThrow();

		assertAll(
			() -> assertThat(updatedCount).isEqualTo(1L),
			() -> assertThat(findCard).usingRecursiveComparison()
				.isEqualTo(updateCard)
		);
	}

	@DisplayName("해당 컬럼 내 모든 카드의 포지션 간격을 1024로 초기화한다.")
	@Test
	void refreshPositionsByColumnId() {
		// given
		Card card1 = new Card(null, "제목1", "내용1", 1L, null);
		Card card2 = new Card(null, "제목2", "내용2", 1L, null);
		Card card3 = new Card(null, "제목3", "내용3", 1L, null);
		Long savedId1 = cardRepository.save(card1);
		Long savedId2 = cardRepository.save(card2);
		Long savedId3 = cardRepository.save(card3);

		Card updateCard1 = new Card(savedId1, "제목1", "내용1", 1L, 100L);
		Card updateCard2 = new Card(savedId2, "제목2", "내용2", 1L, 50L);
		Card updateCard3 = new Card(savedId3, "제목3", "내용3", 1L, 1L);
		cardRepository.update(updateCard1);
		cardRepository.update(updateCard2);
		cardRepository.update(updateCard3);

		List<Card> cardsBeforeRefresh = cardRepository.findAllByColumnId(1L);

		// when
		int updated = cardRepository.refreshPositionsByColumnId(1L);
		List<Card> cardsAfterRefresh = cardRepository.findAllByColumnId(1L);

		// then
		assertAll(
			() -> assertThat(cardsBeforeRefresh).hasSize(3)
				.extracting("id", "position")
				.containsExactlyInAnyOrder(
					tuple(1L, 100L),
					tuple(2L, 50L),
					tuple(3L, 1L)),
			() -> assertThat(updated).isEqualTo(3),
			() -> assertThat(cardsAfterRefresh).hasSize(3)
				.extracting("id", "position")
				.containsExactlyInAnyOrder(
					tuple(1L, 3072L),
					tuple(2L, 2048L),
					tuple(3L, 1024L))
		);
	}

	@DisplayName("카드를 이동 요청 시 position 값을 업데이트한다.")
	@Test
	void updatePosition() {
		// given
		Card card = new Card(null, "제목1", "내용1", 1L, null);
		Long savedId = cardRepository.save(card);

		// when
		cardRepository.updatePosition(savedId, 2L, 1536L);
		Card updatedCard = cardRepository.findById(savedId).orElseThrow();

		// then
		assertThat(updatedCard).extracting("id", "columnId", "position")
			.containsExactly(1L, 2L, 1536L);
	}

	@DisplayName("columnId와 id 를 입력해서 해당 id의 position 값이 최댓값이면 true 를 리턴  ")
	@Test
	void isMax() {
		// given
		Card card1 = new Card(null, "제목1", "내용1", 1L, null);
		Card card2 = new Card(null, "제목1", "내용1", 1L, null);
		cardRepository.save(card1);
		Long savedId2 = cardRepository.save(card2);

		// when
		Boolean max = cardRepository.isMax(1L,savedId2);

		// then
		assertThat(max).isTrue();
	}

	@DisplayName("columnId와 id 를 입력해서 해당 id의 position 값이 최소값이면 true 를 리턴")
	@Test
	void isMin() {
		// given
		Card card1 = new Card(null, "제목1", "내용1", 1L, null);
		Card card2 = new Card(null, "제목1", "내용1", 1L, null);
		Long savedId1 =cardRepository.save(card1);
		 cardRepository.save(card2);
		// when
		Boolean min = cardRepository.isMin(1L,savedId1);

		// then
		assertThat(min).isTrue();

	}

	@DisplayName("입력 받은 컬럼에 있는 top,bottom 카드를 입력받아 순위가 담긴 리스트를 반환받는다 ")
	@Test
	void findRankingById() {
		// given
		Card card1 = new Card(null, "제목1", "내용1", 1L, null);
		Card card2 = new Card(null, "제목1", "내용1", 1L, null);
		Card card3 = new Card(null, "제목1", "내용1", 1L, null);
		Card card4 = new Card(null, "제목1", "내용1", 2L, null);
		Long savedId1 =cardRepository.save(card1);
		Long savedId2 =cardRepository.save(card2);
		Long savedId3 =cardRepository.save(card3);
		Long savedId4 =cardRepository.save(card4);
		// when
		List<Long> rankings = cardRepository.findRankingById(1L,savedId1,savedId3);
		//then
		assertThat(rankings.get(0)).isEqualTo(1L);
		assertThat(rankings.get(1)).isEqualTo(3L);

	}

	@DisplayName("입력받은 컬럼에 입력받은 아이디를 찾는다 ")
	@Test
	void findByIdAndColumn() {
		// given
		Card card = new Card(null, "테스트", "내용", 1L, null);
		Long savedId = cardRepository.save(card);

		// when
		Card actual = cardRepository.findByIdAndColumn(savedId,1L)
			.orElseThrow();

		// then
		 assertThat(actual.getId()).isEqualTo(1L);
		 assertThat(actual.getColumnId()).isEqualTo(1L);

	}

}

