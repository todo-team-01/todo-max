package org.codesquad.todo.domain.column;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class ColumnReader {
	private final ColumnRepository columnRepository;

	public ColumnReader(ColumnRepository columnRepository) {
		this.columnRepository = columnRepository;
	}

	public List<Column> findAll() {
		return columnRepository.findAll();
	}
}
