package org.codesquad.todo.domain.column;

import java.util.List;
import java.util.stream.Collectors;

import org.codesquad.todo.domain.card.CardReader;
import org.springframework.stereotype.Service;

@Service
public class ColumnService {
	private final ColumnReader columnReader;
	private final CardReader cardReader;

	public ColumnService(ColumnReader columnReader, CardReader cardReader) {
		this.columnReader = columnReader;
		this.cardReader = cardReader;
	}

	public List<ColumnWithCard> findAll() {
		return columnReader.findAll().stream()
			.map(c -> new ColumnWithCard(c.getId(), c.getName(), cardReader.findAllByColumnId(c.getId())))
			.collect(Collectors.toUnmodifiableList());
	}
}
