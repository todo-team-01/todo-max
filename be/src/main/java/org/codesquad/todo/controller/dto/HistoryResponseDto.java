package org.codesquad.todo.controller.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.codesquad.todo.domain.history.History;

public class HistoryResponseDto {
	private Long id;
	private LocalDateTime localDateTime;
	private String historyContent;

	public HistoryResponseDto() {
	}

	public HistoryResponseDto(Long id, LocalDateTime localDateTime, String historyContent) {
		this.id = id;
		this.localDateTime = localDateTime;
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

	public Long getId() {
		return this.id;
	}

	public LocalDateTime getLocalDateTime() {
		return this.localDateTime;
	}

	public String getHistoryContent() {
		return this.historyContent;
	}
}
