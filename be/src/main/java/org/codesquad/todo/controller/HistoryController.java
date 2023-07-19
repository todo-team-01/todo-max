package org.codesquad.todo.controller;

import java.util.List;

import org.codesquad.todo.controller.dto.HistoryResponseDto;
import org.codesquad.todo.domain.history.HistoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HistoryController {
	private final HistoryService historyService;

	public HistoryController(HistoryService historyService) {
		this.historyService = historyService;
	}

	@GetMapping("/histories")
	public ResponseEntity<List<HistoryResponseDto>> findAll() {
		return ResponseEntity.ok()
			.body(HistoryResponseDto.from(historyService.findAll()));
	}

	@DeleteMapping("/histories")
	public ResponseEntity<Void> deleteAll() {
		historyService.deleteAll();
		return ResponseEntity.ok().build();
	}
}
