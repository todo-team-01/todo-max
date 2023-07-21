package org.codesquad.todo.controller.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.codesquad.todo.domain.column.ColumnWithCard;

public class ColumnCardResponseDTO {
	private Long columnId;
	private String columnName;
	private List<CardResponseDTO> cards;

	public ColumnCardResponseDTO() {
	}

	public ColumnCardResponseDTO(Long columnId, String columnName, List<CardResponseDTO> cards) {
		this.columnId = columnId;
		this.columnName = columnName;
		this.cards = cards;
	}

	public static List<ColumnCardResponseDTO> from(List<ColumnWithCard> columnWithCards) {
		return columnWithCards.stream()
			.map(ColumnCardResponseDTO::from)
			.collect(Collectors.toUnmodifiableList());
	}

	public static ColumnCardResponseDTO from(ColumnWithCard columnWithCards) {
		return new ColumnCardResponseDTO(columnWithCards.getColumnId(), columnWithCards.getColumnName(),
			CardResponseDTO.from(columnWithCards.getCards()));
	}

	public Long getColumnId() {
		return columnId;
	}

	public String getColumnName() {
		return columnName;
	}

	public List<CardResponseDTO> getCards() {
		return cards;
	}
}
