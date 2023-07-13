package org.codesquad.todo.domain.column;

import java.util.List;

import org.codesquad.todo.config.ColumnNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class ColumnReader {
	private final ColumnRepository columnRepository;

	public ColumnReader(ColumnRepository columnRepository) {
		this.columnRepository = columnRepository;
	}

	public Column findById(Long columnId) {
		return columnRepository.findById(columnId)
			.orElseThrow(ColumnNotFoundException::new);
	}

	public List<Column> findAll() {
		return columnRepository.findAll();
	}
}
