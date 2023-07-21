package org.codesquad.todo.controller.dto;

public class CardMoveRequestDto {

	private Long changedColumnId;
	private Long topCardId;
	private Long bottomCardId;

	public CardMoveRequestDto() {
	}

	public CardMoveRequestDto(Long changedColumnId, Long topCardId, Long bottomCardId) {
		this.changedColumnId = changedColumnId;
		this.topCardId = topCardId;
		this.bottomCardId = bottomCardId;
	}

	public Long getChangedColumnId() {
		return changedColumnId;
	}

	public Long getTopCardId() {
		return topCardId;
	}

	public Long getBottomCardId() {
		return bottomCardId;
	}
}
