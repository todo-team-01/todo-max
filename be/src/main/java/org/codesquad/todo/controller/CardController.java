package org.codesquad.todo.controller;

import org.codesquad.todo.domain.card.CardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CardController {
	private final CardService cardService;

	public CardController(CardService cardService) {
		this.cardService = cardService;
	}

	@DeleteMapping("/cards/{id}")
	public ResponseEntity<Void> deleteCard(@PathVariable Long id) {
		cardService.deleteCardById(id);
		return ResponseEntity.ok().build();
	}

}
