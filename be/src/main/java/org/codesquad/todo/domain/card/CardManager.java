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

	public int updatePrevCardId(Long updateTargetId, Long prevCardId) {
		Card findNextCard = cardReader.findById(updateTargetId);
		return cardRepository.update(findNextCard.createInstanceWithPrevId(prevCardId));
	}

	public int updateCard(Long id, String title, String content) {
		Card card = cardReader.findById(id);
		return cardRepository.update(card.createInstanceWithTitleAndContent(title, content));
	}
}
