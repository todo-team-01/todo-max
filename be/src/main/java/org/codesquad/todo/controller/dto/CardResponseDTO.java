package org.codesquad.todo.controller.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.codesquad.todo.domain.card.Card;

public class CardResponseDTO {
	private Long cardId;
	private String title;
	private String content;

	public CardResponseDTO(Long cardId, String title, String content) {
		this.cardId = cardId;
		this.title = title;
		this.content = content;
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

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}
}
