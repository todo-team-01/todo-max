package org.codesquad.todo.controller.dto;

import org.codesquad.todo.domain.card.Card;

public class CardSaveRequestDto {
	private Long columnId;
	private String cardTitle;
	private String cardContent;

	public CardSaveRequestDto() {
	}

	public CardSaveRequestDto(Long columnId, String cardTitle, String cardContent) {
		this.columnId = columnId;
		this.cardTitle = cardTitle;
		this.cardContent = cardContent;
	}

	public Card toCard() {
		return new Card(null, cardTitle, cardContent, columnId, null);
	}

	public Long getColumnId() {
		return columnId;
	}

	public String getCardTitle() {
		return cardTitle;
	}

	public String getCardContent() {
		return cardContent;
	}
}
