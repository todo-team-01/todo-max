package org.codesquad.todo.controller.dto;

import org.codesquad.todo.domain.column.Column;

public class ColumnSaveRequestDTO {
	private String columnName;

	public ColumnSaveRequestDTO() {
	}

	public ColumnSaveRequestDTO(String columnName) {
		this.columnName = columnName;
	}

	public Column toColumn() {
		return new Column(null, columnName);
	}

	public String getColumnName() {
		return this.columnName;
	}
}
