import { Card, CardData } from "@components/Card";
import { NewCard } from "@components/NewCard";
import { useState } from "react";
import { ColumnHeader } from "./ColumnHeader";

export interface ColumnDataProps {
  columnId: number;
  columnName: string;
  cards: CardData[];
  updateColumnList: () => void;
}

export const Column = ({
  columnId,
  columnName,
  cards,
  updateColumnList,
}: ColumnDataProps) => {
  const [isAdding, setIsAdding] = useState<boolean>(false);
  const handleClickAddCard = () => setIsAdding((prev) => !prev);
  const handleClickCancelAdd = () => setIsAdding(false);
  const cardCount = cards.length;
  const prevFirstCardId = cards[0]?.cardId;

  return (
    <div>
      <ColumnHeader
        columnId={columnId}
        columnName={columnName}
        cardCount={cardCount}
        handleClickAddCard={handleClickAddCard}
      />
      {isAdding && (
        <NewCard
          columnId={columnId}
          handleClickCancelAdd={handleClickCancelAdd}
          nextCardId={prevFirstCardId}
          updateColumnList={updateColumnList}
        />
      )}
      {cards.map((cardData, index) => (
        <Card
          key={`${index}_${cardData.cardId}`}
          cardData={cardData}
          updateColumnList={updateColumnList}
        />
      ))}
    </div>
  );
};
