package org.codesquad.todo.domain.column;

import org.springframework.stereotype.Component;

@Component
public class ColumnManager {
	private final ColumnRepository columnRepository;
	private final ColumnReader columnReader;

	public ColumnManager(ColumnRepository columnRepository, ColumnReader columnReader) {
		this.columnRepository = columnRepository;
		this.columnReader = columnReader;
	}

	public int updateColumn(Column column) {
		columnReader.findById(column.getId());
		return columnRepository.update(column);
	}
}
