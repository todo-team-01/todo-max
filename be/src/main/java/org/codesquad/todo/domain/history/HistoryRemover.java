package org.codesquad.todo.domain.history;

import org.springframework.stereotype.Component;

@Component
public class HistoryRemover {
	private final HistoryRepository historyRepository;

	public HistoryRemover(HistoryRepository historyRepository) {
		this.historyRepository = historyRepository;
	}

	public int deleteHistory() {
		return historyRepository.deleteAll();
	}
}
