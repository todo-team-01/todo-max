package org.codesquad.todo.config;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ApiException.class)
	public ResponseEntity<ErrorResponse> apiExceptionHandler(ApiException e) {
		return ResponseEntity.status(e.getHttpStatus())
			.body(ErrorResponse.from(e));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> exceptionHandler(Exception e) {
		e.printStackTrace();
		return ResponseEntity.internalServerError()
			.body(ErrorResponse.createServerError());
	}
}
