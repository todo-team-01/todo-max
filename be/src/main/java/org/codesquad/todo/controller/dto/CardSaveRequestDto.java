package org.codesquad.todo.controller.dto;

import org.codesquad.todo.domain.card.Card;

public class CardSaveRequestDto {
	private Long columnId;
	private String cardTitle;
	private String cardContent;
	private Long prevCardId;

	public CardSaveRequestDto() {
	}

	public CardSaveRequestDto(Long columnId, String cardTitle, String cardContent, Long prevCardId) {
		this.columnId = columnId;
		this.cardTitle = cardTitle;
		this.cardContent = cardContent;
		this.prevCardId = prevCardId;
	}

	public Card toCard() {
		return new Card(null, cardTitle, cardContent, columnId, 1L, prevCardId);
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
