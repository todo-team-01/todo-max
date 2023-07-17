import { Card, CardData } from "@components/Card/Card";
import { NewCard } from "@components/Card/NewCard";
import { useState } from "react";
import { ColumnHeader } from "./ColumnHeader";

export interface ColumnDataProps {
  columnId: number;
  columnName: string;
  cards: CardData[];
  onCardChanged: () => void;
}

export const Column = ({
  columnId,
  columnName,
  cards,
  onCardChanged,
}: ColumnDataProps) => {
  const [isAdding, setIsAdding] = useState<boolean>(false);
  const handleClickAddCard = () => setIsAdding((prev) => !prev);
  const onAddCancelClick = () => setIsAdding(false);
  const cardCount = cards.length;
  const prevFirstCardId = cards[0]?.cardId;

  return (
    <div css={{ display: "flex", flexDirection: "column", gap: "10px" }}>
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
        <Card
          key={`${index}_${cardData.cardId}`}
          cardData={cardData}
          onCardChanged={onCardChanged}
        />
      ))}
    </div>
  );
};
