package org.codesquad.todo.domain.card;

import org.springframework.stereotype.Component;

@Component
public class CardManager {
	private final CardRepository cardRepository;
	private final CardReader cardReader;
	private final CardValidator cardValidator;

	public CardManager(CardRepository cardRepository, CardReader cardReader, CardValidator cardValidator) {
		this.cardRepository = cardRepository;
		this.cardReader = cardReader;
		this.cardValidator = cardValidator;
	}

	public int updateCard(Long id, String title, String content) {
		Card card = cardReader.findById(id);
		return cardRepository.update(card.createInstanceWithTitleAndContent(title, content));
	}

	public int move(Long cardId, Long columnId, Long topCardId, Long bottomCardId) {
		cardValidator.validateColumn(columnId);

		if (topCardId == null) {
			Long bottomPosition = cardReader.findById(bottomCardId).getPosition();
			return cardRepository.updatePosition(cardId, columnId, bottomPosition + 1024);
		}

		Long topPosition = cardReader.findById(topCardId).getPosition();
		Long bottomPosition = bottomCardId == null ? 0L : cardReader.findById(bottomCardId).getPosition();
		Long newPosition = (topPosition + bottomPosition) / 2;
		int updated = cardRepository.updatePosition(cardId, columnId, newPosition);

		if (topPosition - newPosition == 1 || newPosition - bottomPosition == 1) {
			return cardRepository.refreshPositionsByColumnId(columnId);
		}

		return updated;
	}
}
