package org.codesquad.todo.domain.card;

import org.springframework.stereotype.Component;

@Component
public class CardAppender {
	private final CardRepository cardrepository;
	private final CardValidator cardValidator;

	public CardAppender(CardRepository cardRepository, CardValidator cardValidator) {
		this.cardrepository = cardRepository;
		this.cardValidator = cardValidator;
	}

	public Card append(Card card) {
		cardValidator.verifyCard(card);
		return cardrepository.save(card);
	}
}
