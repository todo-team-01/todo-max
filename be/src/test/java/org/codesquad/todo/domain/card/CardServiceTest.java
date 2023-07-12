package org.codesquad.todo.domain.card;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CardServiceTest {
	@InjectMocks
	private CardService cardService;

	@Mock
	private CardAppender cardAppender;

	@Mock
	private CardManager cardManager;

	@DisplayName("카드를 저장할 때 카드의 정보들을 입력하면 저장하고 카드를 반환한다.")
	@Test
	void saveCard() {
		// given
		Long nextId = 1L;
		Card card = new Card(null, "Git 사용해 보기", "add, commit", 1L, 1L, null);
		given(cardAppender.append(any())).willReturn(card.createInstanceWithId(1L));
		given(cardManager.updatePrevCardId(any(), any())).willReturn(1);

		// when
		Card actual = cardService.saveCard(card, nextId);

		// then
		assertThat(actual.getId()).isEqualTo(1L);
		assertThat(actual).usingRecursiveComparison()
			.ignoringFields("id")
			.isEqualTo(card);
		then(cardManager).should(Mockito.times(1))
			.updatePrevCardId(nextId, actual.getId());
	}

	@DisplayName("카드를 수정할 때 수정할 카드 정보들을 입력하면 수정이 되고 수정한 카드 갯수를 반환한다.")
	@Test
	void modifyCard() {
		// given
		Long id = 1L;
		String title = "새로운 타이틀용";
		String content = "새로운 내용";
		given(cardManager.updateCard(any(), any(), any())).willReturn(1);

		// when
		int updatedCount = cardService.modifyCard(id, title, content);

		// then
		assertThat(updatedCount).isEqualTo(1);
	}
}
