package org.codesquad.todo.domain.card;

import org.codesquad.todo.config.ColumnNotFoundException;
import org.codesquad.todo.domain.column.ColumnValidator;
import org.springframework.stereotype.Component;

@Component
public class CardValidator {
	private final ColumnValidator columnValidator;
	private final CardRepository cardRepository;

	public CardValidator(ColumnValidator columnValidator, CardRepository cardRepository) {
		this.columnValidator = columnValidator;
		this.cardRepository = cardRepository;
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
}
