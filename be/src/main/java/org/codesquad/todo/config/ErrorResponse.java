package org.codesquad.todo.config;

import org.springframework.http.HttpStatus;

public class ErrorResponse {
	private final HttpStatus httpStatus;
	private final String message;

	public ErrorResponse(HttpStatus httpStatus, String message) {
		this.httpStatus = httpStatus;
		this.message = message;
	}

	public int getStatus() {
		return httpStatus.value();
	}

	public String getMessage() {
		return message;
	}
}
