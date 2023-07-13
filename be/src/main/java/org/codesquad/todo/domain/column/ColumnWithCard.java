package org.codesquad.todo.domain.column;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.codesquad.todo.config.CardNotFoundException;
import org.codesquad.todo.domain.card.Card;

public class ColumnWithCard {
	private final Long columnId;
	private final String columnName;
	private final List<Card> cards;

	public ColumnWithCard(Long columnId, String columnName, List<Card> cards) {
		this.columnId = columnId;
		this.columnName = columnName;
		this.cards = sort(cards);
	}

	private List<Card> sort(List<Card> cards) {
		if (cards.isEmpty()) {
			return Collections.emptyList();
		}

		List<Card> result = new ArrayList<>();
		Card currentCard = getRootCard(cards);
		result.add(currentCard);
		Map<Long, Card> prevIdCardMap = cards.stream()
			.collect(Collectors.toMap(Card::getPrevCardId, Function.identity()));

		while (result.size() < cards.size()) {
			currentCard = prevIdCardMap.get(currentCard.getId());
			result.add(currentCard);
		}

		return result;
	}

	private Card getRootCard(List<Card> cards) {
		return cards.stream()
			.filter(c -> c.getPrevCardId() == null)
			.findAny()
			.orElseThrow(CardNotFoundException::new);
	}

	public Long getColumnId() {
		return columnId;
	}

	public String getColumnName() {
		return columnName;
	}

	public List<Card> getCards() {
		return cards;
	}
}
