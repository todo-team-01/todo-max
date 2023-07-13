package org.codesquad.todo.controller.dto;

import org.codesquad.todo.domain.column.Column;

public class ColumnUpdateRequestDTO {
	private String changedColumnName;

	public ColumnUpdateRequestDTO() {
	}

	public ColumnUpdateRequestDTO(String changedColumnName) {
		this.changedColumnName = changedColumnName;
	}

	public Column toColumn(Long id) {
		return new Column(id, changedColumnName);
	}

	public String getChangedColumnName() {
		return changedColumnName;
	}
}
