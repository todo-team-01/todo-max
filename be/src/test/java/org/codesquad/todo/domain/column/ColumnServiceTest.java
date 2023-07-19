package org.codesquad.todo.domain.column;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.List;
import java.util.stream.Collectors;

import org.codesquad.todo.domain.card.Card;
import org.codesquad.todo.domain.card.CardReader;
import org.codesquad.todo.util.ServiceTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;

@ServiceTest
public class ColumnServiceTest {
	@InjectMocks
	private ColumnService columnService;

	@Mock
	private ColumnReader columnReader;

	@Mock
	private CardReader cardReader;

	@Mock
	private ColumnRemover columnRemover;

	@Mock
	private ColumnManager columnManager;

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
			new Card(1L, "첫 번쨰 제목", "첫 번째 내용", 1L, 1024L),
			new Card(2L, "두 번쨰 제목", "두 번째 내용", 1L, 2048L),
			new Card(3L, "세 번쨰 제목", "세 번째 내용", 1L, 3072L));
		BDDMockito.given(cardReader.findAllByColumnId(1L)).willReturn(cardsWithColumIdFirst);

		List<Card> cardsWithColumnSecond = List.of(
			new Card(4L, "첫 번쨰 제목", "첫 번째 내용", 2L, 1024L),
			new Card(5L, "두 번쨰 제목", "두 번째 내용", 2L, 2048L),
			new Card(6L, "세 번쨰 제목", "세 번째 내용", 2L, 3072L));
		BDDMockito.given(cardReader.findAllByColumnId(2L)).willReturn(cardsWithColumnSecond);

		// when
		List<ColumnWithCard> columnWithCards = columnService.findAll();

		// then
		Assertions.assertAll(
			() -> assertThat(columnWithCards.size()).isEqualTo(3),
			() -> assertThat(columnWithCards.stream()
				.map(c -> c.getCards().size())
				.collect(Collectors.toUnmodifiableList()))
				.isEqualTo(List.of(3, 3, 0))
		);
	}

	@DisplayName("Column을 삭제한다.")
	@Test
	void delete() {
		// given
		Long id = 1L;
		given(columnRemover.deleteColumn(id)).willReturn(1);

		// when
		int deletedCount = columnService.delete(id);

		// then
		assertThat(deletedCount).isEqualTo(1);
	}

	@DisplayName("칼럼을 수정할 때 수정할 칼럼 정보들을 입력하면 수정이 되고 수정한 칼럼 갯수를 반환한다.")
	@Test
	void modify() {
		// given
		Long id = 1L;
		String name = "새로운 내용";
		Column column = new Column(id, name);
		given(columnManager.updateColumn(any())).willReturn(1);

		// when
		int updatedCount = columnService.update(column);

		// then
		assertThat(updatedCount).isEqualTo(1);
	}
}
