package org.codesquad.todo.domain.column;

import org.springframework.stereotype.Component;

@Component
public class ColumnReader {
	private final ColumnRepository columnRepository;

	public ColumnReader(ColumnRepository columnRepository) {
		this.columnRepository = columnRepository;
	}

	public Boolean isExist(Long columnId) {
		return columnRepository.isExist(columnId);
	}
}
