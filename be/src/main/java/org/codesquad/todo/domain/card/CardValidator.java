package org.codesquad.todo.domain.card;

import org.codesquad.todo.config.ColumnNotFoundException;
import org.codesquad.todo.config.MemberNotFoundException;
import org.codesquad.todo.domain.column.ColumnValidator;
import org.codesquad.todo.domain.member.MemberValidator;
import org.springframework.stereotype.Component;

@Component
public class CardValidator {
	private final ColumnValidator columnValidator;
	private final MemberValidator memberValidator;

	public CardValidator(ColumnValidator columnValidator, MemberValidator memberValidator) {
		this.columnValidator = columnValidator;
		this.memberValidator = memberValidator;
	}

	public void verifyCard(Card card) {
		if (!memberValidator.exist(card.getMemberId())) {
			throw new MemberNotFoundException();
		}

		if (!columnValidator.exist(card.getColumnId())) {
			throw new ColumnNotFoundException();
		}
	}
}
