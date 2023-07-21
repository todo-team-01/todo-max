package org.codesquad.todo.domain.column;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.stream.Collectors;

import org.codesquad.todo.domain.card.Card;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ColumnWithCardTest {

	@DisplayName("카드들이 최신순으로 정렬이 되어 있는 상태로 카드들을 반환한다.")
	@Test
	void getCards() {
		// given
		List<Card> cards = List.of(
			new Card(1L, "Git 사용해 보기", "add, commit", 1L, 1L, 2L),
			new Card(2L, "카드 등록 구현", "카드 등록", 1L, 1L, null),
			new Card(3L, "컬럼 등록 구현", "컬럼 등록", 1L, 1L, 1L));
		ColumnWithCard columnWithCard = new ColumnWithCard(1L, "오늘 해야 할일", cards);

		// when
		List<Card> actual = columnWithCard.createSortCards();

		// then
		assertThat(actual.stream()
			.map(Card::getId)
			.collect(Collectors.toUnmodifiableList()))
			.containsExactlyElementsOf(List.of(2L, 1L, 3L));
	}
}
