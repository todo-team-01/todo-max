package org.codesquad.todo.controller.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.codesquad.todo.domain.card.Card;

public class CardResponseDTO {
	private Long cardId;
	private String cardTitle;
	private String cardContent;

	public CardResponseDTO(Long cardId, String cardTitle, String cardContent) {
		this.cardId = cardId;
		this.cardTitle = cardTitle;
		this.cardContent = cardContent;
	}

	public static List<CardResponseDTO> from(List<Card> cards) {
		return cards.stream()
			.map(CardResponseDTO::from)
			.collect(Collectors.toUnmodifiableList());
	}

	public static CardResponseDTO from(Card card) {
		return new CardResponseDTO(card.getId(), card.getTitle(), card.getContent());
	}

	public Long getCardId() {
		return cardId;
	}

	public String getCardTitle() {
		return cardTitle;
	}

	public String getCardContent() {
		return cardContent;
	}
}
