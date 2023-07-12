package org.codesquad.todo.config;

import org.springframework.http.HttpStatus;

public class MemberNotFoundException extends ApiException {
	public static final String MESSAGE = "회원을 찾을 수 없습니다.";

	public MemberNotFoundException() {
		super(HttpStatus.NOT_FOUND, MESSAGE);
	}
}
