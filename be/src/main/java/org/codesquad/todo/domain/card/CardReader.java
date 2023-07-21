package org.codesquad.todo.domain.card;

import java.util.List;

import org.codesquad.todo.config.CardNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CardReader {
	private final CardRepository cardRepository;

	public CardReader(CardRepository cardRepository) {
		this.cardRepository = cardRepository;
	}

	public Card findById(Long id) {
		return cardRepository.findById(id)
			.orElseThrow(CardNotFoundException::new);
	}

	public List<Card> findAllByColumnId(Long columnId) {
		return cardRepository.findAllByColumnId(columnId);
	}

	public Card findByIdAndColumn(Long id, Long columnId) {
		return cardRepository.findByIdAndColumn(id,columnId)
			.orElseThrow(CardNotFoundException::new);
	}

	public List<Long> findRankingById (Long columnId,Long topCardId,Long bottomCardId) {
		return cardRepository.findRankingById(columnId,topCardId,bottomCardId);
	}
}
