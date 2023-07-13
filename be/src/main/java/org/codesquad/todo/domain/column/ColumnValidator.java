package org.codesquad.todo.domain.column;

import org.springframework.stereotype.Component;

@Component
public class ColumnValidator {
	private final ColumnRepository columnRepository;

	public ColumnValidator(ColumnRepository columnRepository) {
		this.columnRepository = columnRepository;
	}

	public Boolean exist(Long columnId) {
		return columnRepository.exist(columnId);
	}
}
