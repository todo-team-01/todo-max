package org.codesquad.todo.domain.history;

import java.time.LocalDateTime;

public class History {
	private final Long id;
	private final String content;
	private final LocalDateTime createdAt;
	private final boolean isDeleted;

	public History(Long id, String content, LocalDateTime createdAt, boolean isDeleted) {
		this.id = id;
		this.content = content;
		this.createdAt = createdAt;
		this.isDeleted = isDeleted;
	}

	public History(String content) {
		this.id = null;
		this.content = content;
		this.createdAt = LocalDateTime.now();
		this.isDeleted = false;
	}

	public Long getId() {
		return id;
	}

	public String getContent() {
		return content;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public boolean getIsDeleted() {
		return isDeleted;
	}
}
