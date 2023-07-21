package org.codesquad.todo.controller.dto;

public class CardModifyRequestDto {
	private String title;
	private String content;

	public CardModifyRequestDto() {
	}

	public CardModifyRequestDto(String title, String content) {
		this.title = title;
		this.content = content;
	}

	public String getTitle() {
		return this.title;
	}

	public String getContent() {
		return this.content;
	}
}
