package org.codesquad.todo.config;

import org.springframework.http.HttpStatus;

public class CardInvalidSequentialException extends ApiException {

	public CardInvalidSequentialException() {
		super(HttpStatus.BAD_REQUEST, "위 또는 아래 위치한 카드가 유효하지 않습니다.");
	}
}
