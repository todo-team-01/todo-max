package org.codesquad.todo.domain.column;

import org.springframework.stereotype.Component;

@Component
public class ColumnAppender {
	private final ColumnRepository columnRepository;

	public ColumnAppender(ColumnRepository columnRepository) {
		this.columnRepository = columnRepository;
	}

	public Long append(Column column) {
		return columnRepository.save(column);
	}
}
