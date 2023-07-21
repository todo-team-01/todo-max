package org.codesquad.todo.domain.history;

import org.springframework.stereotype.Component;

@Component
public class HistoryAppender {
	private final HistoryRepository historyRepository;

	public HistoryAppender(HistoryRepository historyRepository) {
		this.historyRepository = historyRepository;
	}

	public Long append(History history) {
		return historyRepository.save(history);
	}
}
