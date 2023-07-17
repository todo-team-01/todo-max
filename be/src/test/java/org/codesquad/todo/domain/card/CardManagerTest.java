package org.codesquad.todo.domain.card;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

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

	@DisplayName("카드를 수정할 때 수정할 카드 정보들을 입력하면 수정이 되고 수정한 카드들의 수를 반환한다.")
	@Test
	void updateCard() {
		// given
		Card card = new Card(null, "Git 사용해 보기", "add, commit", 1L, 1024L);
		cardRepository.save(card);
		given(cardReader.findById(any())).willReturn(card.createInstanceWithId(1L));
		given(cardRepository.update(any())).willReturn(1);

		// when
		int updatedCount = cardManager.updateCard(1L, "바꿀 제목", "바꿀 내용");

		// then
		assertThat(updatedCount).isEqualTo(1);
	}
}
