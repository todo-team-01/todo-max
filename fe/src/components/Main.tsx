import { useFetch } from "hooks/useFetch";
import _ from "lodash";
import { useCallback, useEffect, useRef, useState } from "react";
import { CardData } from "./Card/Card";
import { CloneCard } from "./Card/CloneCard";
import Column from "./Column";
import { ColumnType } from "./Column/Column";
import { FAB } from "./FAB";

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
  const columnRefs = useRef<HTMLDivElement[]>([]);
  const [targetPosition, setTargetPosition] = useState({
    columnIndex: -1,
    cardIndex: -1,
  });

  const [initialPosition, setInitialPosition] = useState<Position>({
    x: 0,
    y: 0,
  });
  const [cloneCardData, setCloneCardData] = useState<CardData>();
  const [autoScrollRafId, setAutoScrollRafId] = useState<number | null>(null);

  const {
    response: columnList,
    fetch: onCardChanged,
  }: { response: ColumnType[]; fetch: () => Promise<void> } = useFetch({
    url: "/api/columns",
    method: "get",
    autoFetch: true,
  });

  const [cardHeights, setCardHeights] = useState<number[][]>([]);

  useEffect(() => {
    calculateCardHeights();
  }, [columnList]);

  const calculateCardHeights = () => {
    const cardHeights = columnRefs.current.map((column) => {
      return Array.from(column.children).map(
        (child) => (child as HTMLElement).offsetHeight
      );
    });

    setCardHeights(cardHeights);
  };

  const { fetch: fetchCardPatch } = useFetch({
    url: `/api/cards/${cloneCardData?.cardId}`,
    method: "patch",
  });

  const getUpdateCardBody = () => {
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

    return result;
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

      // cardIndex를 계산합니다.
      let cardIndex = 0;
      let sumHeight = 0;
      while (
        sumHeight + cardHeights[columnIndex][cardIndex] <
          mousePos.y - mainRect.top &&
        cardIndex < currentColumn.cards.length
      ) {
        sumHeight += cardHeights[columnIndex][cardIndex];
        cardIndex++;
      }

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

      const cardUpdateBody = getUpdateCardBody();
      if (!_.isEmpty(cardUpdateBody)) {
        await fetchCardPatch(cardUpdateBody);
        await onCardChanged();
      }
    }
  };

  // TODO : scrollLeft, scrollRight은 스크롤이 더 진행될 수 없는 상황에서도
  //        화면에 좌,우 측 5% 위치에 있다면 반복적으로 실행된다. 최적화가 필요하다
  const scrollLeft = () => {
    window.scrollBy(-25, 0);
    setAutoScrollRafId(window.requestAnimationFrame(scrollLeft));
  };

  const scrollRight = () => {
    window.scrollBy(25, 0);
    setAutoScrollRafId(window.requestAnimationFrame(scrollRight));
  };

  const handleAutoScroll = (mouseXPosition: number) => {
    if (!cloneCardData || !mainRef.current) return;

    const vw = Math.max(
      document.documentElement.clientWidth || 0,
      window.innerWidth || 0
    );
    const scrollThreshold = 0.05; // 5%

    if (mouseXPosition <= vw * scrollThreshold) {
      if (autoScrollRafId === null) {
        setAutoScrollRafId(window.requestAnimationFrame(scrollLeft));
      }
    } else if (mouseXPosition >= vw * (1 - scrollThreshold)) {
      if (autoScrollRafId === null) {
        setAutoScrollRafId(window.requestAnimationFrame(scrollRight));
      }
    } else if (autoScrollRafId !== null) {
      window.cancelAnimationFrame(autoScrollRafId);
      setAutoScrollRafId(null);
    }
  };

  const combinedOnMouseMove = useCallback(
    (event: React.MouseEvent) => {
      onMouseMove(event);
      handleAutoScroll(event.clientX);
    },
    [onMouseMove, handleAutoScroll]
  );

  return (
    <div
      css={{
        minWidth: "90%",
        height: "auto",
        display: "flex",
        gap: "24px",
        position: "absolute",
        paddingLeft: `${window.innerWidth * 0.05}px`,
        top: "64px",
        left: "0px",
        zIndex: "-1",
      }}
      ref={mainRef}
      onMouseUp={onMouseUp}
      onMouseMove={combinedOnMouseMove}
    >
      {columnList &&
        columnList.map(({ columnId, columnName, cards }, index) => (
          <Column
            ref={(ref) => {
              if (ref !== null) {
                columnRefs.current[index] = ref;
              }
            }}
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
      {cloneCardData && (
        <CloneCard cardData={cloneCardData} initialPosition={initialPosition} />
      )}
      <FAB onColumnChanged={onCardChanged} />
    </div>
  );
};
