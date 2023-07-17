package org.codesquad.todo.domain.card;

import org.codesquad.todo.config.ColumnNotFoundException;
import org.codesquad.todo.domain.column.ColumnValidator;
import org.springframework.stereotype.Component;

@Component
public class CardValidator {
	private final ColumnValidator columnValidator;

	public CardValidator(ColumnValidator columnValidator) {
		this.columnValidator = columnValidator;
	}

	public void verifyCard(Card card) {
		if (!columnValidator.exist(card.getColumnId())) {
			throw new ColumnNotFoundException();
		}
	}
}
