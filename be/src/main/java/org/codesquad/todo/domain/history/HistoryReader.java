package org.codesquad.todo.domain.history;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class HistoryReader {
	private final HistoryRepository historyRepository;

	public HistoryReader(HistoryRepository historyRepository) {
		this.historyRepository = historyRepository;
	}

	public List<History> findAll() {
		return historyRepository.findAll();
	}
}
