import { useState } from "react";
import { ColumnHeader } from "./ColumnHeader";
import { Card, CardData } from "@components/Card";

interface ColumnDataProps {
  columnId: number;
  columnName: string;
  cards: CardData[];
}

export const Column = ({ columnId, columnName, cards }: ColumnDataProps) => {
  const [isAdding, setIsAdding] = useState<boolean>(true);
  const handleClickAddCard = () => setIsAdding(true);
  const cardCount = cards.length;

  return (
    <div>
      <ColumnHeader
        columnId={columnId}
        columnName={columnName}
        cardCount={cardCount}
        handleClickAddCard={handleClickAddCard}
      />
      {isAdding && <Card cardStatus={"editing"} />}
      {cards.map((cardData, index) => (
        <Card key={`${index}_${cardData.id}`} cardData={cardData} />
      ))}
    </div>
  );
};
