package org.codesquad.todo.controller;

import java.util.List;

import org.codesquad.todo.controller.dto.ColumnCardResponseDTO;
import org.codesquad.todo.domain.column.ColumnService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ColumnController {
	private final ColumnService columnService;

	public ColumnController(ColumnService columnService) {
		this.columnService = columnService;
	}

	@GetMapping("/columns")
	public ResponseEntity<List<ColumnCardResponseDTO>> showColumnWithCards() {
		return ResponseEntity.ok().body(ColumnCardResponseDTO.from(columnService.findAll()));
	}
}
