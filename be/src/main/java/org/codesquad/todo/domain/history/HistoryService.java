package org.codesquad.todo.domain.history;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class HistoryService {
	private final HistoryReader historyReader;
	private final HistoryRemover historyRemover;

	public HistoryService(HistoryReader historyReader, HistoryRemover historyRemover) {
		this.historyReader = historyReader;
		this.historyRemover = historyRemover;
	}

	@Transactional(readOnly = true)
	public List<History> findAll() {
		return historyReader.findAll();
	}

	public int deleteAll() {
		return historyRemover.deleteHistory();
	}
}
