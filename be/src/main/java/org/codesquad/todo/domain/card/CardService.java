package org.codesquad.todo.domain.card;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CardService {
	private final CardAppender cardAppender;
	private final CardRemover cardRemover;
	private final CardManager cardManager;

	public CardService(CardAppender cardAppender, CardRemover cardRemover, CardManager cardManager) {
		this.cardAppender = cardAppender;
		this.cardRemover = cardRemover;
		this.cardManager = cardManager;
	}

	public Long saveCard(Card card) {
		return cardAppender.append(card);
	}

	public int modifyCard(Long id, String title, String content) {
		return cardManager.updateCard(id, title, content);
	}

	public int deleteCardById(Long id) {
		return cardRemover.delete(id);
	}

	public int moveCard(Long id, Long columnId, Long topCardId, Long bottomCardId) {
		return cardManager.move(id, columnId, topCardId, bottomCardId);
	}
}

