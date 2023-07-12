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
public class CardServiceTest {
	@InjectMocks
	private CardService cardService;

	@Mock
	private CardAppender cardAppender;

	@Mock
	private CardManager cardManager;

	@DisplayName("카드를 저장한다")
	@Test
	void saveCard() {
		// given
		Long nextId = 1L;
		Card card = new Card(null, "Git 사용해 보기", "add, commit", 1L, 1L, null);
		given(cardAppender.append(any())).willReturn(card.createInstanceWithId(1L));
		given(cardManager.updatePrevCardId(any(), any())).willReturn(1);

		// when
		Card savedCard = cardService.saveCard(card, nextId);

		// then
		assertThat(savedCard.getId()).isEqualTo(1L);
		assertThat(savedCard.getTitle()).isEqualTo(card.getTitle());
		assertThat(savedCard.getContent()).isEqualTo(card.getContent());
		assertThat(savedCard.getMemberId()).isEqualTo(card.getMemberId());
		assertThat(savedCard.getPrevCardId()).isEqualTo(card.getPrevCardId());
	}
}
