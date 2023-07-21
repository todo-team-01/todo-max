import { Card, CardData, cardStyle } from "@components/Card/Card";
import { CardViewer } from "@components/Card/CardViewer";
import { NewCard } from "@components/Card/NewCard";
import { Position } from "@components/Main";
import React, { useState } from "react";
import { ColumnHeader } from "./ColumnHeader";

interface ColumnProps extends ColumnType {
  onCardChanged: () => void;
  setCloneCard: (cardData: CardData, initialPosition: Position) => void;
  cloneState: {
    hasClone: boolean;
    cardIndex: number;
    cloneCardData: CardData | undefined;
  };
}

export interface ColumnType {
  columnId: number;
  columnName: string;
  cards: CardData[];
}

export const Column = React.forwardRef(
  (
    {
      columnId,
      columnName,
      cards,
      onCardChanged,
      setCloneCard,
      cloneState,
    }: ColumnProps,
    ref: React.Ref<HTMLDivElement>
  ) => {
    const [isAdding, setIsAdding] = useState<boolean>(false);
    const onAddCardClick = () => setIsAdding((prev) => !prev);
    const onAddCancelClick = () => setIsAdding(false);
    const cardCount = cards.length;

    const isCloneValid = cloneState.hasClone && cloneState.cloneCardData;

    const GhostCard = (
      <div css={[cardStyle, { opacity: "0.5" }]}>
        <CardViewer cardData={cloneState.cloneCardData!} />
      </div>
    );

    return (
      <div
        ref={ref}
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
          onAddCardClick={onAddCardClick}
          onCardChanged={onCardChanged}
        />
        {isAdding && (
          <NewCard
            columnId={columnId}
            onAddCancelClick={onAddCancelClick}
            onCardChanged={onCardChanged}
          />
        )}
        {cards.map((cardData, index) => (
          <React.Fragment key={`${index}_${cardData.cardId}`}>
            {isCloneValid && cloneState.cardIndex === index && GhostCard}
            <Card
              key={`${index}_${cardData.cardId}`}
              cardData={cardData}
              onCardChanged={onCardChanged}
              setCloneCard={setCloneCard}
            />
          </React.Fragment>
        ))}
        {isCloneValid && cloneState.cardIndex === cards.length && GhostCard}
      </div>
    );
  }
);

