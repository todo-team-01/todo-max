package org.codesquad.todo.domain.card;

import org.codesquad.todo.config.ColumnNotFoundException;
import org.codesquad.todo.config.MemberNotFoundException;
import org.codesquad.todo.domain.column.ColumnReader;
import org.codesquad.todo.domain.member.MemberReader;
import org.springframework.stereotype.Component;

@Component
public class CardValidator {
	private final CardRepository cardRepository;
	private final ColumnReader columnReader;
	private final MemberReader memberReader;

	public CardValidator(CardRepository cardRepository, ColumnReader columnReader, MemberReader memberReader) {
		this.cardRepository = cardRepository;
		this.columnReader = columnReader;
		this.memberReader = memberReader;
	}

	public void verifyCard(Card card) {
		if (!memberReader.isExist(card.getMemberId())) {
			throw new MemberNotFoundException();
		}

		if (!columnReader.isExist(card.getColumnId())) {
			throw new ColumnNotFoundException();
		}
	}
}
