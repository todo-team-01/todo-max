package org.codesquad.todo.config;

import java.util.Objects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.codesquad.todo.domain.card.Card;
import org.codesquad.todo.domain.card.CardReader;
import org.codesquad.todo.domain.column.Column;
import org.codesquad.todo.domain.column.ColumnReader;
import org.codesquad.todo.domain.history.History;
import org.codesquad.todo.domain.history.HistoryAppender;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class HistoryAspect {
	private final CardReader cardReader;
	private final ColumnReader columnReader;
	private final HistoryAppender historyAppender;

	public HistoryAspect(CardReader cardReader, ColumnReader columnReader, HistoryAppender historyAppender) {
		this.cardReader = cardReader;
		this.columnReader = columnReader;
		this.historyAppender = historyAppender;
	}

	@Around("execution(* org.codesquad.todo.domain.card.CardService.saveCard(..))")
	public Object converterContentSave(ProceedingJoinPoint joinPoint) throws Throwable {
		Object result = joinPoint.proceed();
		Card card = (Card)joinPoint.getArgs()[0];
		Column column = columnReader.findById(card.getColumnId());
		String content = String.format("%s을(를) %s에서 등록하였습니다.", card.getTitle(), column.getName());

		historyAppender.append(new History(content));
		return result;
	}

	@Around("execution(* org.codesquad.todo.domain.card.CardService.modifyCard(..))")
	public Object converterContentModify(ProceedingJoinPoint joinPoint) throws Throwable {
		String title = (String)joinPoint.getArgs()[1];

		int proceed = (int)joinPoint.proceed();

		String content = String.format("%s을(를) 변경하였습니다.", title);
		historyAppender.append(new History(content));
		return proceed;
	}

	@Around("execution(* org.codesquad.todo.domain.card.CardService.moveCard(..))")
	public Object converterContentMove(ProceedingJoinPoint joinPoint) throws Throwable {
		Long cardId = (Long)joinPoint.getArgs()[0];
		Long afterCardColumnId = (Long)joinPoint.getArgs()[1];
		Long beforeCardColumnId = cardReader.findById(cardId).getColumnId();
		String cardTitle = cardReader.findById(cardId).getTitle();

		Object result = joinPoint.proceed();
		if (Objects.equals(beforeCardColumnId, afterCardColumnId)) {
			return result;
		}

		String afterColumnName = columnReader.findById(afterCardColumnId).getName();
		String beforeColumnName = columnReader.findById(beforeCardColumnId).getName();
		String content = String.format("%s을(를) %s에서 %s으로 이동하였습니다.", cardTitle, beforeColumnName, afterColumnName);

		historyAppender.append(new History(content));

		return result;
	}

	@Around("execution(* org.codesquad.todo.domain.card.CardService.deleteCardById(..))")
	public Object converterContentDelete(ProceedingJoinPoint joinPoint) throws Throwable {
		Long id = (Long)joinPoint.getArgs()[0];
		Card deletedCard = cardReader.findById(id);

		int proceed = (int)joinPoint.proceed();

		String content = String.format("%s을(를)이 삭제 되었습니다.", deletedCard.getTitle());
		historyAppender.append(new History(content));
		return proceed;
	}
}
