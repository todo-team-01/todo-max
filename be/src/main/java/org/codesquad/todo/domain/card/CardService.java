package org.codesquad.todo.domain.card;

import org.springframework.stereotype.Service;

@Service
public class CardService {

	private CardRemover cardRemover;

	public CardService(CardRemover cardRemover) {
		this.cardRemover = cardRemover;
	}

	public void deleteCardById(Long id) {
		cardRemover.remove(id);
	}
}
