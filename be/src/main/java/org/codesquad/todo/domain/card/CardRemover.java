package org.codesquad.todo.domain.card;

import org.springframework.stereotype.Component;

@Component
public class CardRemover {

	private CardRepository cardRepository;

	public CardRemover(CardRepository cardRepository) {
		this.cardRepository = cardRepository;
	}

	public void remove(Long id) {
		cardRepository.remove(id);
	}
}
