package org.codesquad.todo.domain.card;

public class Card {
	private final Long id;
	private final String title;
	private final String content;
	private final Long columnId;
	private final Long memberId;
	private final Long prevCardId;

	public Card(Long id, String title, String content, Long columnId, Long memberId, Long prevCardId) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.columnId = columnId;
		this.memberId = memberId;
		this.prevCardId = prevCardId;
	}

	public Card createInstanceWithId(Long id) {
		return new Card(id, this.title, this.content, this.columnId, this.memberId, this.prevCardId);
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

	public Long getMemberId() {
		return memberId;
	}

	public Long getPrevCardId() {
		return prevCardId;
	}
}
