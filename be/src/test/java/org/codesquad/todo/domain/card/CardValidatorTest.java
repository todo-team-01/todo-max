package org.codesquad.todo.domain.card;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import java.util.List;

import org.codesquad.todo.config.ColumnNotFoundException;
import org.codesquad.todo.config.InvalidCardException;
import org.codesquad.todo.domain.column.ColumnValidator;
import org.codesquad.todo.util.ServiceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

@ServiceTest
public class CardValidatorTest {

	@InjectMocks
	private CardValidator cardValidator;

	@Mock
	private ColumnValidator columnValidator;

	@Mock
	private CardReader cardReader;

	@Mock
	private CardRepository cardRepository;

	@DisplayName("카드 정보를 가지고 카드 생성을 하는 검증을 한다.")
	@Test
	void verifyCard() {
		// given
		Card card = new Card(null, "Git 사용해 보기", "add, commit", 1L, 1024L);
		given(columnValidator.exist(any())).willReturn(true);

		// when

		// then
		assertDoesNotThrow(() -> cardValidator.verifyCard(card));
	}

	@DisplayName("카드를 검증할 때 해당 칼럼이 없다면 에러를 반환한다.")
	@Test
	void verifyCardFail2() {
		// given
		Card card = new Card(null, "Git 사용해 보기", "add, commit", 1L, 2048L);
		given(columnValidator.exist(any())).willReturn(false);

		// when

		// then
		assertThrows(ColumnNotFoundException.class, () -> cardValidator.verifyCard(card));
	}

	@DisplayName("입력받은 bottom card의 position 값이 해당하는 컬럼에서 최댓값인 경우 예외가 발생하지 않는다.")
	@Test
	void isMax() {
		// given
		given(cardRepository.isMax(any(), any())).willReturn(true);

		// when // then
		assertDoesNotThrow(() -> cardValidator.validateMaxCardId(1L, 1L));
	}

	@DisplayName("입력받은 bottom card의 position 값이 해당하는 컬럼에서 최댓값이 아니라면 예외가 발생한다.")
	@Test
	void isMaxFail() {
		// given
		given(cardRepository.isMax(any(), any())).willReturn(false);

		// when // then
		assertThrows(InvalidCardException.class,
			() -> cardValidator.validateMaxCardId(1L, 1L));
	}

	@DisplayName("입력받은 top card의 position 값이 해당하는 컬럼에서 최솟값인 경우 예외가 발생하지 않는다.")
	@Test
	void isMin() {
		// given
		given(cardRepository.isMin(any(), any())).willReturn(true);

		// when // then
		assertDoesNotThrow(() -> cardValidator.validateMinCardId(1L, 1L));
	}

	@DisplayName("입력받은 top card의 position 값이 해당하는 컬럼에서 최솟값이 아닐 경우 예외가 발생한다")
	@Test
	void isMinFail() {
		// given
		given(cardRepository.isMin(any(), any())).willReturn(false);

		// when // then
		assertThrows(InvalidCardException.class,
			() -> cardValidator.validateMinCardId(1L, 1L));
	}

	@DisplayName("입력받은 top card와 bottom card의 rank 값의 차이가 1인 경우 예외가 발생하지 않는다.")
	@Test
	void validateSequentialCards() {
		// given
		given(cardReader.findRankingById(1L, 2L, 3L))
			.willReturn(List.of(1L, 2L));

		// when // then
		assertDoesNotThrow(() -> cardValidator.validateSequentialCards(1L, 2L, 3L));
	}

	@DisplayName("입력받은 top card와 bottom card의 rank 값이 1이상 차이 나면 예외가 발생한다.")
	@Test
	void validateSequentialCardsFail() {
		// given
		given(cardReader.findRankingById(1L, 2L, 3L))
			.willReturn(List.of(3L, 1L));

		// when // then
		assertThrows(InvalidCardException.class,
			() -> cardValidator.validateSequentialCards(1L, 2L, 3L));
	}

	@DisplayName("비어있지 않은 컬럼으로 이동 시 top 또는 bottom cardId가 null인 경우 예외가 발생한다.")
	@Test
	void validateNoCardInColumn() {
		// given
		given(cardRepository.existsInColumn(any())).willReturn(true);

		// when // then
		assertThrows(InvalidCardException.class,
			() -> cardValidator.validateNoCardInColumn(1L));
	}
}
