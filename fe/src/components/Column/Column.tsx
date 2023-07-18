import { Card, CardData } from "@components/Card/Card";
import { NewCard } from "@components/Card/NewCard";
import { Position } from "@components/Main";
import React, { useState } from "react";
import { ColumnHeader } from "./ColumnHeader";

interface ColumnDataProps {
  columnId: number;
  columnName: string;
  cards: CardData[];
  onCardChanged: () => void;
  setCloneCard: (cardData: CardData, initialPosition: Position) => void;
  cloneState: {
    hasClone: boolean;
    cardIndex: number;
    cloneCardData: CardData | undefined;
  };
}

export const Column = ({
  columnId,
  columnName,
  cards,
  onCardChanged,
  setCloneCard,
  cloneState,
}: ColumnDataProps) => {
  const [isAdding, setIsAdding] = useState<boolean>(false);
  const handleClickAddCard = () => setIsAdding((prev) => !prev);
  const onAddCancelClick = () => setIsAdding(false);
  const cardCount = cards.length;
  const prevFirstCardId = cards[0]?.cardId;

  const isCloneValid = cloneState.hasClone && cloneState.cloneCardData;

  const CloneCard = (
    <div css={{ opacity: "0.5" }}>
      <Card
        cardData={cloneState.cloneCardData!}
        onCardChanged={onCardChanged}
        setCloneCard={setCloneCard}
      />
    </div>
  );

  return (
    <div
      css={{
        width: "332px",
        display: "flex",
        flexDirection: "column",
        gap: "10px",
      }}
    >
      <ColumnHeader
        columnId={columnId}
        columnName={columnName}
        cardCount={cardCount}
        handleClickAddCard={handleClickAddCard}
      />
      {isAdding && (
        <NewCard
          columnId={columnId}
          onAddCancelClick={onAddCancelClick}
          nextCardId={prevFirstCardId}
          onCardChanged={onCardChanged}
        />
      )}
      {cards.map((cardData, index) => (
        <React.Fragment key={`${index}_${cardData.cardId}`}>
          {isCloneValid && cloneState.cardIndex === index && CloneCard}
          <Card
            key={`${index}_${cardData.cardId}`}
            cardData={cardData}
            onCardChanged={onCardChanged}
            setCloneCard={setCloneCard}
          />
        </React.Fragment>
      ))}
      {isCloneValid && cloneState.cardIndex === cards.length && CloneCard}
    </div>
  );
};
