package org.codesquad.todo.controller;

import java.util.List;

import org.codesquad.todo.controller.dto.ColumnCardResponseDTO;
import org.codesquad.todo.controller.dto.ColumnSaveRequestDTO;
import org.codesquad.todo.controller.dto.ColumnSaveResponseDTO;
import org.codesquad.todo.controller.dto.ColumnUpdateRequestDTO;
import org.codesquad.todo.domain.column.ColumnService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ColumnController {
	private final ColumnService columnService;

	public ColumnController(ColumnService columnService) {
		this.columnService = columnService;
	}

	@GetMapping("/columns")
	public ResponseEntity<List<ColumnCardResponseDTO>> showColumnWithCards() {
		return ResponseEntity.ok().body(ColumnCardResponseDTO.from(columnService.findAll()));
	}

	@PostMapping("/columns")
	public ResponseEntity<ColumnSaveResponseDTO> saveColumn(@RequestBody ColumnSaveRequestDTO columnSaveRequestDTO) {
		return ResponseEntity.ok()
			.body(new ColumnSaveResponseDTO(columnService.save(columnSaveRequestDTO.toColumn())));
	}

	@PutMapping("/columns/{id}")
	public ResponseEntity<Void> updateColumn(@PathVariable Long id,
		@RequestBody ColumnUpdateRequestDTO columnUpdateRequestDTO) {
		columnService.update(columnUpdateRequestDTO.toColumn(id));
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/columns/{id}")
	public ResponseEntity<Void> deleteColumn(@PathVariable Long id) {
		columnService.delete(id);
		return ResponseEntity.ok().build();
	}
}
