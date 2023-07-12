package org.codesquad.todo.domain.card;

import org.codesquad.todo.config.CardNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CardReader {
	private final CardValidator cardValidator;
	private final CardRepository cardRepository;

	public CardReader(CardValidator cardValidator, CardRepository cardRepository) {
		this.cardValidator = cardValidator;
		this.cardRepository = cardRepository;
	}

	public Card findById(Long id) {
		return cardRepository.findById(id)
			.orElseThrow(CardNotFoundException::new);
	}
}
