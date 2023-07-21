package org.codesquad.todo.domain.card;

import java.util.List;

import org.codesquad.todo.config.ColumnNotFoundException;
import org.codesquad.todo.config.InvalidCardException;
import org.codesquad.todo.domain.column.ColumnValidator;
import org.springframework.stereotype.Component;

@Component
public class CardValidator {
	private final ColumnValidator columnValidator;
	private final CardRepository cardRepository;
	private final CardReader cardReader;

	public CardValidator(ColumnValidator columnValidator, CardRepository cardRepository, CardReader cardReader) {
		this.columnValidator = columnValidator;
		this.cardRepository = cardRepository;
		this.cardReader = cardReader;
	}

	public void verifyCard(Card card) {
		if (!columnValidator.exist(card.getColumnId())) {
			throw new ColumnNotFoundException();
		}
	}

	public void validateColumn(Long columnId) {
		if (!columnValidator.exist(columnId)) {
			throw new ColumnNotFoundException();
		}
	}

	public void validateMaxCardId(Long columnId, Long id) {
		if (!cardRepository.isMax(columnId, id)) {
			throw new InvalidCardException();
		}
	}

	public void validateMinCardId(Long columnId, Long id) {
		if (!cardRepository.isMin(columnId, id)) {
			throw new InvalidCardException();
		}
	}

	public void validateSequentialCards(Long columnId, Long topCardId, Long bottomCardId) {
		List<Long> rankings = cardReader.findRankingById(columnId, topCardId, bottomCardId);
		if (rankings.get(1) - rankings.get(0) != 1) {
			throw new InvalidCardException();
		}
	}

	public void validateNoCardInColumn(Long columnId) {
		if (cardRepository.existsInColumn(columnId)) {
			throw new InvalidCardException();
		}
	}
}
