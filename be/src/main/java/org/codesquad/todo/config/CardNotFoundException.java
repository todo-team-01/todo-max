package org.codesquad.todo.config;

import org.springframework.http.HttpStatus;

public class CardNotFoundException extends ApiException {
	public static final String MESSAGE = "카드를 찾을 수 없습니다.";

	public CardNotFoundException() {
		super(HttpStatus.NOT_FOUND, MESSAGE);
	}
}
