package org.codesquad.todo.config;

import org.springframework.http.HttpStatus;

public class ErrorResponse {
	private final HttpStatus status;
	private final String message;

	public ErrorResponse(HttpStatus status, String message) {
		this.status = status;
		this.message = message;
	}

	public static ErrorResponse from(ApiException apiException) {
		return new ErrorResponse(apiException.getHttpStatus(), apiException.getMessage());
	}

	public static ErrorResponse createServerError() {
		return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러");
	}

	public int getStatus() {
		return status.value();
	}

	public String getMessage() {
		return message;
	}
}
