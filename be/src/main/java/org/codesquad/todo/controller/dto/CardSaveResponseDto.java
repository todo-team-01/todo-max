package org.codesquad.todo.controller.dto;

public class CardSaveResponseDto {
	private Long cardId;

	public CardSaveResponseDto() {
	}

	public CardSaveResponseDto(Long cardId) {
		this.cardId = cardId;
	}

	public Long getCardId() {
		return cardId;
	}
}
