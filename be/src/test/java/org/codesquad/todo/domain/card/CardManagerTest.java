package org.codesquad.todo.domain.card;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.codesquad.todo.util.ServiceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

@ServiceTest
class CardManagerTest {

	@InjectMocks
	private CardManager cardManager;

	@Mock
	private CardRepository cardRepository;

	@Mock
	private CardReader cardReader;

	@Mock
	private CardValidator cardValidator;

	@DisplayName("카드를 수정할 때 수정할 카드 정보들을 입력하면 수정이 되고 수정한 카드들의 수를 반환한다.")
	@Test
	void updateCard() {
		// given
		Card card = new Card(null, "Git 사용해 보기", "add, commit", 1L, 1024L);
		given(cardReader.findById(any())).willReturn(card.createInstanceWithId(1L));
		given(cardRepository.update(any())).willReturn(1);

		// when
		int updatedCount = cardManager.updateCard(1L, "바꿀 제목", "바꿀 내용");

		// then
		assertThat(updatedCount).isEqualTo(1);
	}

	@DisplayName("컬럼의 맨 위로 카드를 이동 시 해당 컬럼에서 가장 큰 position 값에 1024를 더한 값을 position으로 갖는다.")
	@Test
	void moveCardWithNoTopCard() {
		// given
		given(cardReader.findById(any()))
			.willReturn(new Card(1L, "이동할 카드", "이동할 카드", 1L, 1024L));
		given(cardReader.findByIdAndColumn(any(), any()))
			.willReturn(new Card(2L, "맨 위에 있는 카드", "맨 위에 있는 카드", 1L, 2048L));
		given(cardRepository.updatePosition(1L, 1L, 3072L)).willReturn(1);

		// when
		int updated = cardManager.move(1L, 1L, null, 2L);

		// then
		assertThat(updated).isEqualTo(1);
	}

	@DisplayName("카드 이동 시 position 값을 업데이트한다.")
	@Test
	void moveCard() {
		// given
		given(cardReader.findById(1L))
			.willReturn(new Card(1L, "이동할 카드", "이동할 카드", 1L, 1024L));
		given(cardReader.findByIdAndColumn(any(), any()))
			.willReturn(new Card(3L, "top card", "위에 있는 카드", 1L, 3072L))
			.willReturn(new Card(2L, "bottom card", "밑에 있는 카드", 1L, 2048L));

		given(cardRepository.updatePosition(1L, 1L, 2560L)).willReturn(1);

		// when
		int updated = cardManager.move(1L, 1L, 3L, 2L);

		// then
		assertThat(updated).isEqualTo(1);
	}

	@DisplayName("카드 이동 시 position 간격이 1이하인 경우 해당 컬럼의 모든 포지션 값의 간격을 1024로 초기화한다.")
	@Test
	void moveCardWithRefresh() {
		// given
		given(cardReader.findById(1L))
			.willReturn(new Card(1L, "이동할 카드", "이동할 카드", 1L, 1024L));
		given(cardReader.findByIdAndColumn(2L, 1L))
			.willReturn(new Card(2L, "밑에 있는 카드", "밑에 있는 카드", 1L, 100L));
		given(cardReader.findByIdAndColumn(3L, 1L))
			.willReturn(new Card(3L, "위에 있는 카드", "위에 있는 카드", 1L, 102L));

		given(cardRepository.updatePosition(1L, 1L, 101L)).willReturn(1);
		given(cardRepository.refreshPositionsByColumnId(1L)).willReturn(3);

		// when
		int updated = cardManager.move(1L, 1L, 3L, 2L);

		// then
		assertThat(updated).isEqualTo(3);
	}

	@DisplayName("빈 컬럼으로 카드 이동 시 카드의 position을 1024로 할당한다.")
	@Test
	void moveToEmptyColumn() {
		// given
		given(cardReader.findById(1L))
			.willReturn(new Card(1L, "이동할 카드", "이동할 카드", 1L, 1024L));

		given(cardRepository.updatePosition(1L, 2L, 1024L)).willReturn(1);

		// when
		int updated = cardManager.move(1L, 2L, null, null);

		// then
		assertThat(updated).isEqualTo(1);
	}

}
