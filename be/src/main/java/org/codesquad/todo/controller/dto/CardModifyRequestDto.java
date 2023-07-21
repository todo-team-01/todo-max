package org.codesquad.todo.controller.dto;

public class CardModifyRequestDto {
	private String changedCardTitle;
	private String changedCardContent;

	public CardModifyRequestDto() {
	}

	public CardModifyRequestDto(String changedCardTitle, String changedCardContent) {
		this.changedCardTitle = changedCardTitle;
		this.changedCardContent = changedCardContent;
	}

	public String getChangedCardTitle() {
		return this.changedCardTitle;
	}

	public String getChangedCardContent() {
		return this.changedCardContent;
	}
}
