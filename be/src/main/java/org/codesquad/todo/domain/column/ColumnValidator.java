package org.codesquad.todo.domain.column;

import org.springframework.stereotype.Component;

@Component
public class ColumnValidator {
	private final ColumnRepository columnRepository;

	public ColumnValidator(ColumnRepository columnRepository) {
		this.columnRepository = columnRepository;
	}

	public Boolean isExist(Long columnId) {
		return columnRepository.isExist(columnId);
	}
}
