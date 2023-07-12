package org.codesquad.todo.domain.card;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CardAppenderTest {
	@InjectMocks
	private CardAppender cardAppender;

	@Mock
	private CardRepository cardRepository;

	@Mock
	private CardValidator cardValidator;

	@DisplayName("카드 정보를 받아 저장한다.")
	@Test
	void append() {
		// given
		Card card = new Card(null, "Git 사용해 보기", "add, commit", 1L, 1L, null);
		willDoNothing().given(cardValidator).verifyCard(any());
		given(cardRepository.save(any())).willReturn(card.createInstanceWithId(1L));

		// when
		Long savedId = cardAppender.append(card).getId();

		// then
		assertThat(savedId).isEqualTo(1L);
	}
}
