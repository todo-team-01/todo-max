package org.codesquad.todo.config;

import org.springframework.http.HttpStatus;

public class InvalidCardException extends ApiException {
	public static final String MESSAGE = "유효하지 않은 입력입니다.";

	public InvalidCardException() {
		super(HttpStatus.BAD_REQUEST, MESSAGE);
	}
}