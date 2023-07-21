package org.codesquad.todo.domain.card;

import org.springframework.stereotype.Component;

@Component
public class CardAppender {
	private final CardRepository cardRepository;
	private final CardValidator cardValidator;

	public CardAppender(CardRepository cardRepository, CardValidator cardValidator) {
		this.cardRepository = cardRepository;
		this.cardValidator = cardValidator;
	}

	public Long append(Card card) {
		cardValidator.verifyCard(card);
		return cardRepository.save(card);
	}
}
