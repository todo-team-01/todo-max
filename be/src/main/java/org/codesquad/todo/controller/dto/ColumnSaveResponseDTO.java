package org.codesquad.todo.controller.dto;

public class ColumnSaveResponseDTO {
	private Long columnId;

	public ColumnSaveResponseDTO() {
	}

	public ColumnSaveResponseDTO(Long columnId) {
		this.columnId = columnId;
	}

	public Long getColumnId() {
		return columnId;
	}
}
