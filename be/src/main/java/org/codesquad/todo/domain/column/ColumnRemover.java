package org.codesquad.todo.domain.column;

import org.springframework.stereotype.Component;

@Component
public class ColumnRemover {
	private final ColumnRepository columnRepository;

	public ColumnRemover(ColumnRepository columnRepository) {
		this.columnRepository = columnRepository;
	}

	public int deleteColumn(Long columnId) {
		return columnRepository.delete(columnId);
	}
}
