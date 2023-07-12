package org.codesquad.todo.controller;

import java.net.URI;

import org.codesquad.todo.controller.dto.CardSaveRequestDto;
import org.codesquad.todo.controller.dto.CardSaveResponseDto;
import org.codesquad.todo.domain.card.Card;
import org.codesquad.todo.domain.card.CardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CardController {
	private final CardService cardService;

	public CardController(CardService cardService) {
		this.cardService = cardService;
	}

	@PostMapping("/cards")
	public ResponseEntity<CardSaveResponseDto> saveCard(@RequestBody CardSaveRequestDto cardSaveRequestDto) {
		Card card = cardService.saveCard(cardSaveRequestDto.toCard());

		return ResponseEntity.created(URI.create("/cards/" + card.getId()))
			.body(CardSaveResponseDto.from(card));
	}
}

