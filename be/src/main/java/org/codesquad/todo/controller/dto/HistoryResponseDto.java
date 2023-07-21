package org.codesquad.todo.controller.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.codesquad.todo.domain.history.History;

public class HistoryResponseDto {
	private Long historyId;
	private LocalDateTime historyCreatedAt;
	private String historyContent;

	public HistoryResponseDto() {
	}

	public HistoryResponseDto(Long historyId, LocalDateTime historyCreatedAt, String historyContent) {
		this.historyId = historyId;
		this.historyCreatedAt = historyCreatedAt;
		this.historyContent = historyContent;
	}

	public static List<HistoryResponseDto> from(List<History> histories) {
		return histories.stream()
			.map(HistoryResponseDto::from)
			.collect(Collectors.toUnmodifiableList());
	}

	public static HistoryResponseDto from(History history) {
		return new HistoryResponseDto(history.getId(), history.getCreatedAt(), history.getContent());
	}

	public Long getHistoryId() {
		return this.historyId;
	}

	public LocalDateTime getHistoryCreatedAt() {
		return this.historyCreatedAt;
	}

	public String getHistoryContent() {
		return this.historyContent;
	}
}
