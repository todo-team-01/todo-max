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
		Long validId = cardReader.findById(cardId).getId();

		if (topCardId == null && bottomCardId == null) {
			cardValidator.validateNoCardInColumn(columnId);
			return cardRepository.updatePosition(validId, columnId, 1024L);
		}

		if (topCardId == null) {
			Long bottomPosition = cardReader.findByIdAndColumn(bottomCardId, columnId).getPosition();
			cardValidator.validateMaxCardId(columnId, bottomCardId);
			return cardRepository.updatePosition(validId, columnId, bottomPosition + 1024);
		}

		Long topPosition = cardReader.findByIdAndColumn(topCardId, columnId).getPosition();

		if (bottomCardId == null) {
			cardValidator.validateMinCardId(columnId, topCardId);
			return cardRepository.updatePosition(validId, columnId, topPosition / 2);
		}

		cardValidator.validateSequentialCards(columnId, topCardId, bottomCardId);

		Long bottomPosition = cardReader.findByIdAndColumn(bottomCardId, columnId).getPosition();
		Long newPosition = (topPosition + bottomPosition) / 2;
		int updated = cardRepository.updatePosition(validId, columnId, newPosition);

		if (topPosition - newPosition == 1 || newPosition - bottomPosition == 1) {
			return cardRepository.refreshPositionsByColumnId(columnId);
		}

		return updated;
	}
}
