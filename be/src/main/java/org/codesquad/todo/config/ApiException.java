package org.codesquad.todo.config;

import org.springframework.http.HttpStatus;

public class ApiException extends RuntimeException {

	private final HttpStatus httpStatus;
	private final String message;

	public ApiException(HttpStatus httpStatus, String message) {
		this.httpStatus = httpStatus;
		this.message = message;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public String getMessage() {
		return message;
	}
}
