package org.codesquad.todo.domain.card;

import org.springframework.stereotype.Component;

@Component
public class CardManager {
	private final CardRepository cardRepository;
	private final CardReader cardReader;

	public CardManager(CardRepository cardRepository, CardReader cardReader) {
		this.cardRepository = cardRepository;
		this.cardReader = cardReader;
	}

	public int updatePrevCardId(Long nextCardId, Long prevCardId) {
		Card findNextCard = cardReader.findById(nextCardId);
		return cardRepository.update(findNextCard.createInstanceWithPrevId(prevCardId));
	}
}
