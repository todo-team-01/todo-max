package org.codesquad.todo.controller.dto;

import org.codesquad.todo.domain.column.Column;

public class ColumnSaveResponseDTO {
	private Long id;
	private String name;

	public ColumnSaveResponseDTO() {
	}

	public ColumnSaveResponseDTO(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public static ColumnSaveResponseDTO from(Column column) {
		return new ColumnSaveResponseDTO(column.getId(), column.getName());
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}
