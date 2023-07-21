package org.codesquad.todo.domain.card;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.codesquad.todo.util.ServiceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

@ServiceTest
public class CardServiceTest {
	@InjectMocks
	private CardService cardService;

	@Mock
	private CardAppender cardAppender;

	@Mock
	private CardManager cardManager;

	@Mock
	private CardRemover cardRemover;

	@DisplayName("카드를 저장할 때 카드의 정보들을 입력하면 저장하고 카드를 반환한다.")
	@Test
	void saveCard() {
		// given
		Long nextId = 1L;
		Card card = new Card(null, "Git 사용해 보기", "add, commit", 1L, 1024L);
		given(cardAppender.append(any())).willReturn(1L);

		// when
		Long actual = cardService.saveCard(card);

		// then
		assertThat(actual).isEqualTo(1L);
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

	@DisplayName("해당하는 ID의 카드를 삭제한다.")
	@Test
	void deleteCard() {
		// given
		Card card = new Card(null, "Git 사용해 보기", "add, commit", 1L, 1024L);
		given(cardAppender.append(any())).willReturn(1L);
		given(cardRemover.delete(any())).willReturn(1);

		Long savedId = cardService.saveCard(card);

		// when
		int deleted = cardService.deleteCardById(savedId);

		// then
		assertThat(deleted).isEqualTo(1);
	}
}
