package org.codesquad.todo.controller.dto;

import org.codesquad.todo.domain.card.Card;

public class CardSaveResponseDto {
	private Long cardId;
	private Long columnId;
	private String cardTitle;
	private String cardContent;

	public CardSaveResponseDto() {
	}

	public CardSaveResponseDto(Long cardId, Long columnId, String cardTitle, String cardContent) {
		this.cardId = cardId;
		this.columnId = columnId;
		this.cardTitle = cardTitle;
		this.cardContent = cardContent;
	}

	public static CardSaveResponseDto from(Card card) {
		return new CardSaveResponseDto(card.getId(), card.getColumnId(), card.getTitle(), card.getContent());
	}

	public Long getCardId() {
		return cardId;
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
