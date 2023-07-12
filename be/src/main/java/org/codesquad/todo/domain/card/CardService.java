package org.codesquad.todo.domain.card;

import java.util.Objects;

import org.springframework.stereotype.Service;

@Service
public class CardService {
	private final CardAppender cardAppender;
	private final CardRemover cardRemover;
	private final CardManager cardManager;

	public CardService(CardAppender cardAppender, CardRemover cardRemover, CardManager cardManager) {
		this.cardAppender = cardAppender;
		this.cardRemover = cardRemover;
		this.cardManager = cardManager;
	}

	public Card saveCard(Card card, Long nextCardId) {
		Card newCard = cardAppender.append(card);

		if (Objects.nonNull(nextCardId)) {
			cardManager.updatePrevCardId(nextCardId, newCard.getId());
		}

		return newCard;
	}

	public int modifyCard(Long id, String title, String content) {
		return cardManager.updateCard(id, title, content);
	}

	public void deleteCardById(Long id) {
		cardRemover.delete(id);
	}
}

