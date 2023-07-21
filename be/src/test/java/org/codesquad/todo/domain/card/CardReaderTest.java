package org.codesquad.todo.domain.card;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import java.util.List;
import java.util.Optional;

import org.codesquad.todo.config.CardNotFoundException;
import org.codesquad.todo.util.ServiceTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

@ServiceTest
class CardReaderTest {
	@InjectMocks
	private CardReader cardReader;

	@Mock
	private CardRepository cardRepository;

	@DisplayName("카드 ID를 통해 해당 카드 정보를 가져온다")
	@Test
	void findById() {
		// given
		Long id = 1L;
		Card card = new Card(id, "title", "content", 1L, 1024L);

		given(cardRepository.findById(any())).willReturn(Optional.of(card));

		// when
		Card actual = cardReader.findById(id);

		// then
		assertThat(actual).usingRecursiveComparison().isEqualTo(card);

	}

	@DisplayName("카드를 조회할 때 저장 되어 있지 않는 카드 아이디를 입력 받으면 에러를 반환한다.")
	@Test
	void findByIdFail() {
		// given
		Long id = 1L;
		given(cardRepository.findById(any())).willReturn(Optional.empty());

		// when

		// then
		Assertions.assertThrows(CardNotFoundException.class, () -> cardReader.findById(id));
	}

	@Test
	void findAllByColumnId() {
		// given
		List<Card> cards = List.of(
			new Card(1L, "title1", "content1", 1L, 1024L),
			new Card(2L, "title2", "content2", 1L, 2048L),
			new Card(3L, "title3", "content3", 1L, 3072L));
		given(cardRepository.findAllByColumnId(any())).willReturn(cards);

		// when
		List<Card> actual = cardReader.findAllByColumnId(1L);

		// then
		Assertions.assertAll(
			() -> assertThat(actual.size()).isEqualTo(3L),
			() -> assertThat(actual).usingRecursiveComparison()
				.isEqualTo(cards)
		);
	}

	@DisplayName("컬럼 Id와 카드 ID를 통해 해당 카드 정보를 가져온다")
	@Test
	void findByIdAndColumn() {
		// given
		Long id = 1L;
		Card card = new Card(id, "title", "content", 1L, 1024L);

		given(cardRepository.findByIdAndColumn(any(),any())).willReturn(Optional.of(card));

		// when
		Card actual = cardReader.findByIdAndColumn(id,1L);

		// then
		assertThat(actual).usingRecursiveComparison().isEqualTo(card);
	}

	@DisplayName("카드를 조회할 때 컬럼에 저장 되어 있지 않는 카드 아이디를 입력 받으면 에러를 반환한다.")
	@Test
	void findByIdAndColumnFail() {
		// given
		Long id = 1L;
		Card card = new Card(id, "title", "content", 1L, 1024L);
		given(cardRepository.findByIdAndColumn(any(),any())).willReturn(Optional.empty());


		// then
		Assertions.assertThrows(CardNotFoundException.class, () -> cardReader.findByIdAndColumn(id,2L));
	}

}
