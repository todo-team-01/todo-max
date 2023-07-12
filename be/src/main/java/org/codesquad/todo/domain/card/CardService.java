package org.codesquad.todo.domain.card;

import org.springframework.stereotype.Service;

@Service
public class CardService {
	private final CardAppender cardAppender;
	private final CardRemover cardRemover;

	public CardService(CardAppender cardAppender, CardRemover cardRemover) {
		this.cardAppender = cardAppender;
		this.cardRemover = cardRemover;
	}

	public Card saveCard(Card card) {
		return cardAppender.append(card);
	}

	public int deleteCardById(Long id) {
		return cardRemover.delete(id);
	}

}

