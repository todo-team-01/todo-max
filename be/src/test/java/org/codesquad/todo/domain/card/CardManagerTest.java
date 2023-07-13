package org.codesquad.todo.domain.card;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CardManagerTest {

	@InjectMocks
	private CardManager cardManager;

	@Mock
	private CardRepository cardRepository;

	@Mock
	private CardReader cardReader;

	@DisplayName("이전 카드의 ID를 수정할 때 변경할 카드 아이디와 수정할 이전 카드 아이디를 입력하면 수정한 카드들의 수를 반환한다.")
	@Test
	void updatePrevCardId() {
		// given
		Card card = new Card(null, "Git 사용해 보기", "add, commit", 1L, 1L, null);
		cardRepository.save(card);
		given(cardReader.findById(any())).willReturn(card.createInstanceWithId(1L).createInstanceWithPrevId(3L));
		given(cardRepository.update(any())).willReturn(1);

		// when
		int updatedCount = cardManager.updatePrevCardId(1L, 3L);

		// then
		assertThat(updatedCount).isEqualTo(1);
	}

	@DisplayName("카드를 수정할 때 수정할 카드 정보들을 입력하면 수정이 되고 수정한 카드들의 수를 반환한다.")
	@Test
	void updateCard() {
		// given
		Card card = new Card(null, "Git 사용해 보기", "add, commit", 1L, 1L, null);
		cardRepository.save(card);
		given(cardReader.findById(any())).willReturn(card.createInstanceWithId(1L).createInstanceWithPrevId(3L));
		given(cardRepository.update(any())).willReturn(1);

		// when
		int updatedCount = cardManager.updateCard(1L, "바꿀 제목", "바꿀 내용");

		// then
		assertThat(updatedCount).isEqualTo(1);
	}

	@DisplayName("자식이 있는 카드를 삭제하는 경우 자식 카드의 부모를 수정한 후에 1을 반환한다.")
	@Test
	void updateCardBeforeDeleteWithChild() {
		// given
		Card card1 = new Card(null, "변경 전 타이틀", "변경 전 내용", 1L, 1L, 2L);
		Card card2 = new Card(null, "변경 후 타이틀", "변경 후 내용", 1L, 1L, null);
		given(cardReader.findWithChildById(any())).willReturn(List.of(
			card1.createInstanceWithId(1L).createInstanceWithPrevId(2L), card2.createInstanceWithId(2L)));
		given(cardRepository.updateBeforeDelete(any(), any())).willReturn(1);

		// when
		int updated = cardManager.updateCardBeforeDelete(2L);

		// then
		assertThat(updated).isEqualTo(1);
	}

	@DisplayName("자식 카드가 없는 경우 수정하지 않고 0을 반환한다.")
	@Test
	void updateCardBeforeDeleteWithNoChild() {
		// given
		Card card = new Card(null, "Git 사용해 보기", "add, commit", 1L, 1L, null);
		given(cardReader.findWithChildById(any())).willReturn(List.of(card.createInstanceWithId(1L)));

		// when
		int updated = cardManager.updateCardBeforeDelete(1L);

		// then
		assertThat(updated).isEqualTo(0);
	}

}
