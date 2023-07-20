import { useFetch } from "hooks/useFetch";
import _ from "lodash";
import { useCallback, useEffect, useRef, useState } from "react";
import { CardData } from "./Card/Card";
import { CloneCard } from "./Card/CloneCard";
import Column from "./Column";
import { ColumnType } from "./Column/Column";

export interface Position {
  x: number;
  y: number;
}

export interface MoveCardBodyType {
  changedColumnId: number;
  TopCardId: number | null;
  BottomCardId: number | null;
}

export const Main = () => {
  const mainRef = useRef<HTMLDivElement>(null);
  const [targetPosition, setTargetPosition] = useState({
    columnIndex: -1,
    cardIndex: -1,
  });

  const [initialPosition, setInitialPosition] = useState<Position>({
    x: 0,
    y: 0,
  });
  const [cloneCardData, setCloneCardData] = useState<CardData>();
  const [cardUpdateBody, setCardUpdateBody] = useState({});

  const {
    response: columnList,
    fetch: onCardChanged,
  }: { response: ColumnType[]; fetch: () => Promise<void> } = useFetch({
    url: "/api/columns",
    method: "get",
    autoFetch: true,
  });

  const { fetch: fetchCardPatch } = useFetch({
    url: `/api/cards/${cloneCardData?.cardId}`,
    method: "patch",
    body: cardUpdateBody,
  });

  useEffect(() => {
    updateBodyContent();
  }, [targetPosition]);

  const updateBodyContent = () => {
    if (targetPosition.columnIndex === -1 || targetPosition.cardIndex === -1) {
      return;
    }

    const currentColumnId = columnList[targetPosition.columnIndex].columnId;
    const currentCards = columnList[targetPosition.columnIndex].cards;

    const topCardId =
      targetPosition.cardIndex > 0
        ? currentCards[targetPosition.cardIndex - 1].cardId
        : null;

    const bottomCardId =
      targetPosition.cardIndex < currentCards.length ||
      targetPosition.cardIndex === currentCards.length - 1
        ? currentCards[targetPosition.cardIndex].cardId
        : null;

    const result = {
      changedColumnId: currentColumnId,
      topCardId: topCardId,
      bottomCardId: bottomCardId,
    };

    setCardUpdateBody(result);
  };

  const setCloneCard = useCallback(
    (cardData: CardData, initialPosition: Position) => {
      setCloneCardData(cardData);
      setInitialPosition(initialPosition);
    },
    []
  );

  const resetCloneCard = () => {
    setCloneCardData(undefined);
    setTargetPosition({
      columnIndex: -1,
      cardIndex: -1,
    });
  };

  const onMouseMove = (event: React.MouseEvent) => {
    if (!cloneCardData || !mainRef.current) return;

    const mousePos = { x: event.clientX, y: event.clientY };

    const columnWidth = 332;
    const columnMargin = 24;
    const cardHeight = 88;
    const cardMargin = 10;

    // mainRef 영역의 경계를 가져옵니다.
    const mainRect = mainRef.current.getBoundingClientRect();

    // 마우스가 mainRef 영역에 있는지 확인합니다.
    const isMouseOverMainRef =
      mousePos.x > mainRect.left &&
      mousePos.x < mainRect.right &&
      mousePos.y > mainRect.top &&
      mousePos.y < mainRect.bottom;

    if (isMouseOverMainRef) {
      // columnIndex를 계산합니다.
      const columnIndex = Math.floor(
        (mousePos.x - mainRect.left) / (columnWidth + columnMargin)
      );

      const currentColumn = columnList[columnIndex];
      if (!currentColumn) return;

      // cardIndex를 계산하고 현재 컬럼의 카드 수를 초과하지 않도록 제한합니다.
      const cardIndex = Math.min(
        Math.floor((mousePos.y - mainRect.top) / (cardHeight + cardMargin)),
        currentColumn.cards.length
      );

      // 마우스 위치가 변경되었을 때에만 mousePosition을 업데이트합니다.
      const isMousePositionChanged =
        targetPosition.columnIndex !== columnIndex ||
        targetPosition.cardIndex !== cardIndex;

      if (isMousePositionChanged) {
        setTargetPosition({ columnIndex, cardIndex });
      }
    }
  };

  const onMouseUp = async () => {
    if (targetPosition.columnIndex !== -1 && targetPosition.cardIndex !== -1) {
      resetCloneCard();

      updateBodyContent();
      if (!_.isEmpty(cardUpdateBody)) {
        await fetchCardPatch();
        await onCardChanged();
      }
    }
  };

  return (
    <div
      ref={mainRef}
      css={{ display: "flex", gap: "24px" }}
      onMouseUp={onMouseUp}
      onMouseMove={onMouseMove}
    >
      {columnList &&
        columnList.map(({ columnId, columnName, cards }, index) => (
          <Column
            key={columnId}
            columnId={columnId}
            columnName={columnName}
            cards={cards}
            onCardChanged={onCardChanged}
            setCloneCard={setCloneCard}
            cloneState={{
              hasClone: index === targetPosition.columnIndex,
              cardIndex: targetPosition.cardIndex,
              cloneCardData: cloneCardData,
            }}
          />
        ))}
      {cloneCardData !== undefined && (
        <CloneCard cardData={cloneCardData} initialPosition={initialPosition} />
      )}
    </div>
  );
};
