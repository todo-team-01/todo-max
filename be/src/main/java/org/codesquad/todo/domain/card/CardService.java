package org.codesquad.todo.domain.card;

import org.springframework.stereotype.Service;

@Service
public class CardService {
	private final CardAppender cardAppender;

	public CardService(CardAppender cardAppender) {
		this.cardAppender = cardAppender;
	}

	public Card saveCard(Card card) {
		return cardAppender.append(card);
	}

	private CardRemover cardRemover;

	public CardService(CardRemover cardRemover) {
		this.cardRemover = cardRemover;
	}

	public void deleteCardById(Long id) {
		cardRemover.delete(id);
	}

}

