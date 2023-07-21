package org.codesquad.todo.domain.column;

import java.util.List;

import org.codesquad.todo.domain.card.Card;

public class ColumnWithCard {
	private final Long columnId;
	private final String columnName;
	private final List<Card> cards;

	public ColumnWithCard(Long columnId, String columnName, List<Card> cards) {
		this.columnId = columnId;
		this.columnName = columnName;
		this.cards = cards;
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
