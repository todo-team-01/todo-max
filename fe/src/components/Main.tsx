import { useFetch } from "hooks/useFetch";
import _, { debounce } from "lodash";
import { useEffect, useRef, useState } from "react";
import { CardData } from "./Card/Card";
import { CloneCard } from "./Card/CloneCard";
import Column from "./Column";

export interface Position {
  x: number;
  y: number;
}

interface Card {
  cardId: number;
  title: string;
  content: string;
  writer: string;
}

interface Column {
  columnId: number;
  columnName: string;
  cards: Card[];
}

export const Main = () => {
  const mainRef = useRef<HTMLDivElement>(null);
  const [mousePosition, setMousePosition] = useState({
    columnIndex: -1,
    cardIndex: -1,
  });

  const [initialPosition, setInitialPosition] = useState<Position>({
    x: 0,
    y: 0,
  });
  const [columnList, setColumnList] = useState<Column[]>([]);
  const [cloneCardData, setCloneCardData] = useState<CardData>();
  const [bodyContent, setBodyContent] = useState({});

  const { response, errorMsg, loading, fetch } = useFetch({
    url: "/api",
    method: "get",
    autoFetch: true,
  });

  const { fetch: fetchCardPatch } = useFetch({
    url: `/api/cards/${cloneCardData?.cardId}`,
    method: "patch",
    body: bodyContent,
  });

  useEffect(() => {
    updateBodyContent();
  }, [mousePosition]);

  useEffect(() => {
    response && setColumnList(response);
  }, [response]);

  const updateBodyContent = () => {
    if (mousePosition.columnIndex === -1 || mousePosition.cardIndex === -1) {
      return;
    }

    const currentColumnId = columnList[mousePosition.columnIndex].columnId;
    const currentCards = columnList[mousePosition.columnIndex].cards;

    const topCardId =
      mousePosition.cardIndex > 0
        ? currentCards[mousePosition.cardIndex - 1].cardId
        : null;
    const bottomCardId =
      mousePosition.cardIndex < currentCards.length - 1
        ? currentCards[mousePosition.cardIndex + 1].cardId
        : null;

    const result = {
      changedColumnId: currentColumnId,
      TopCardId: topCardId,
      BottomCardId: bottomCardId,
    };

    setBodyContent(result);
  };

  const onCardChanged = async () => {
    await fetch();
  };

  const setCloneCard = (cardData: CardData, initialPosition: Position) => {
    setCloneCardData((prev) => {
      if (!_.isEqual(prev, cardData)) {
        return cardData;
      }
      return prev;
    });

    setInitialPosition(initialPosition);
  };

  const resetCloneCard = () => {
    setCloneCardData(undefined);
    setMousePosition({
      columnIndex: -1,
      cardIndex: -1,
    });
  };

  const onMouseMove = debounce((event: React.MouseEvent) => {
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
        mousePosition.columnIndex !== columnIndex ||
        mousePosition.cardIndex !== cardIndex;

      if (isMousePositionChanged) {
        setMousePosition({ columnIndex, cardIndex });
      }
    }
  }, 30);

  const onMouseUp = async () => {
    if (mousePosition.columnIndex === -1 || mousePosition.cardIndex === -1) {
      return;
    }

    resetCloneCard();
    await fetchCardPatch();
    await onCardChanged();
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
              hasClone: index === mousePosition.columnIndex,
              cardIndex: mousePosition.cardIndex,
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
