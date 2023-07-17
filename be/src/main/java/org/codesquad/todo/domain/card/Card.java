package org.codesquad.todo.domain.card;

public class Card {
	private final Long id;
	private final String title;
	private final String content;
	private final Long columnId;
	private final Long position;

	public Card(Long id, String title, String content, Long columnId, Long position) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.columnId = columnId;
		this.position = position;
	}

	public Card createInstanceWithId(Long id) {
		return new Card(id, this.title, this.content, this.columnId, this.position);
	}

	public Card createInstanceWithTitleAndContent(String title, String content) {
		return new Card(this.id, title, content, this.columnId, this.position);
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

	public Long getPosition() {
		return position;
	}
}
