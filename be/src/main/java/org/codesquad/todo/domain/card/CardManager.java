package org.codesquad.todo.domain.card;

import java.util.List;

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

	public int updateCardBeforeDelete(Long id) {
		List<Card> cards = cardReader.findWithChildById(id);

		if (cards.size() == 2) {
			int cardToDeleteIndex = 0;
			int childCardIndex = 0;
			for (int i = 0; i < cards.size(); i++) {
				if (cards.get(i).getId().equals(id)) {
					cardToDeleteIndex = i;
				} else {
					childCardIndex = i;
				}
			}
			return cardRepository.updateBeforeDelete(cards.get(childCardIndex).getId(),
				cards.get(cardToDeleteIndex).getPrevCardId());
		}

		return 0;
	}
}
