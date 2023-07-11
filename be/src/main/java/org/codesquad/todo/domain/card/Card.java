package org.codesquad.todo.domain.card;

public class Card {
	private final Long id;
	private final String title;
	private final String content;
	private final Long columnId;
	private final Long userId;
	private final Long prevCardId;

	public Card(Long id, String title, String content, Long columnId, Long userId, Long prevCardId) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.columnId = columnId;
		this.userId = userId;
		this.prevCardId = prevCardId;
	}

	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}

	public Long getColumnId() {
		return columnId;
	}

	public Long getUserId() {
		return userId;
	}

	public Long getPrevCardId() {
		return prevCardId;
	}
}
