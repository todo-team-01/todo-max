package org.codesquad.todo.controller;

import java.net.URI;

import org.codesquad.todo.controller.dto.CardModifyRequestDto;
import org.codesquad.todo.controller.dto.CardMoveRequestDto;
import org.codesquad.todo.controller.dto.CardSaveRequestDto;
import org.codesquad.todo.controller.dto.CardSaveResponseDto;
import org.codesquad.todo.domain.card.CardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CardController {
	private final CardService cardService;

	public CardController(CardService cardService) {
		this.cardService = cardService;
	}

	@PostMapping("/cards")
	public ResponseEntity<CardSaveResponseDto> saveCard(@RequestBody CardSaveRequestDto cardSaveRequestDto) {
		Long cardId = cardService.saveCard(cardSaveRequestDto.toCard());
		return ResponseEntity.created(URI.create("/cards/" + cardId))
			.body(new CardSaveResponseDto(cardId));
	}

	@PutMapping("/cards/{id}")
	public ResponseEntity<Void> modifyCard(@PathVariable Long id,
		@RequestBody CardModifyRequestDto cardModifyRequestDto) {
		cardService.modifyCard(id, cardModifyRequestDto.getChangedCardTitle(),
			cardModifyRequestDto.getChangedCardContent());
		return ResponseEntity.ok().build();
	}

	@PatchMapping("/cards/{id}")
	public ResponseEntity<Void> moveCard(@PathVariable Long id,
		@RequestBody CardMoveRequestDto cardMoveRequestDto) {
		cardService.moveCard(id, cardMoveRequestDto.getChangedColumnId(), cardMoveRequestDto.getTopCardId(),
			cardMoveRequestDto.getBottomCardId());
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/cards/{id}")
	public ResponseEntity<Void> deleteCard(@PathVariable Long id) {
		cardService.deleteCardById(id);
		return ResponseEntity.ok().build();
	}
}

