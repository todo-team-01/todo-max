package org.codesquad.todo.domain.column;

import java.util.List;
import java.util.stream.Collectors;

import org.assertj.core.api.Assertions;
import org.codesquad.todo.domain.card.Card;
import org.codesquad.todo.domain.card.CardReader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ColumnServiceTest {
	@InjectMocks
	private ColumnService columnService;

	@Mock
	private ColumnReader columnReader;

	@Mock
	private CardReader cardReader;

	@DisplayName("모든 Column, Card를 정렬된 ColumnWithCard에 담아 반환한다.")
	@Test
	void findAll() {
		// given
		List<Column> columns = List.of(
			new Column(1L, "내일 할일"),
			new Column(2L, "오늘 할 일"),
			new Column(3L, "내일 할 일"));
		BDDMockito.given(columnReader.findAll()).willReturn(columns);

		List<Card> cardsWithColumIdFirst = List.of(
			new Card(1L, "첫 번쨰 제목", "첫 번째 내용", 1L, null, 2L),
			new Card(2L, "두 번쨰 제목", "두 번째 내용", 1L, null, 3L),
			new Card(3L, "세 번쨰 제목", "세 번째 내용", 1L, null, null));
		BDDMockito.given(cardReader.findAllByColumnId(1L)).willReturn(cardsWithColumIdFirst);

		List<Card> cardsWithColumnSecond = List.of(
			new Card(4L, "첫 번쨰 제목", "첫 번째 내용", 2L, null, 5L),
			new Card(5L, "두 번쨰 제목", "두 번째 내용", 2L, null, 6L),
			new Card(6L, "세 번쨰 제목", "세 번째 내용", 2L, null, null));
		BDDMockito.given(cardReader.findAllByColumnId(2L)).willReturn(cardsWithColumnSecond);

		// when
		List<ColumnWithCard> columnWithCards = columnService.findAll();

		// then
		Assertions.assertThat(columnWithCards.size()).isEqualTo(3);
		Assertions.assertThat(columnWithCards.stream()
				.map(c -> c.getCards().size())
				.collect(Collectors.toUnmodifiableList()))
			.isEqualTo(List.of(3, 3, 0));
	}
}
