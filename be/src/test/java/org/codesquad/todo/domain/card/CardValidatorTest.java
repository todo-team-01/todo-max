package org.codesquad.todo.domain.card;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import org.codesquad.todo.config.ColumnNotFoundException;
import org.codesquad.todo.domain.column.ColumnValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CardValidatorTest {
	@InjectMocks
	private CardValidator cardValidator;

	@Mock
	private ColumnValidator columnValidator;

	@DisplayName("카드 정보를 가지고 카드 생성을 하는 검증을 한다.")
	@Test
	void verifyCard() {
		// given
		Card card = new Card(null, "Git 사용해 보기", "add, commit", 1L, 1024L);
		given(columnValidator.exist(any())).willReturn(true);

		// when

		// then
		Assertions.assertDoesNotThrow(() -> cardValidator.verifyCard(card));
	}

	@DisplayName("카드를 검증할 때 해당 칼럼이 없다면 에러를 반환한다.")
	@Test
	void verifyCardFail2() {
		// given
		Card card = new Card(null, "Git 사용해 보기", "add, commit", 1L, 2048L);
		given(columnValidator.exist(any())).willReturn(false);

		// when

		// then
		Assertions.assertThrows(ColumnNotFoundException.class, () -> cardValidator.verifyCard(card));
	}
}
