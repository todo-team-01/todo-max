package org.codesquad.todo.config;

import org.springframework.http.HttpStatus;

public class ColumnNotFoundException extends ApiException {
	public static final String MESSAGE = "칼럼을 찾을 수 없습니다.";

	public ColumnNotFoundException() {
		super(HttpStatus.BAD_REQUEST, MESSAGE);
	}
}
