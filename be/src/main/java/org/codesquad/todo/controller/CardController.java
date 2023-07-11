package org.codesquad.todo.controller;

import org.codesquad.todo.domain.card.CardService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CardController {
	private final CardService cardService;

	public CardController(CardService cardService) {
		this.cardService = cardService;
	}

}
