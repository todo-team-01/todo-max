import { useState } from "react";
import { ColumnHeader } from "./ColumnHeader";
import { Card, CardData } from "@components/Card";

interface ColumnDataProps {
  columnId: number;
  columnName: string;
  cards: CardData[];
  reFetch: () => void;
}

export const Column = ({
  columnId,
  columnName,
  cards,
  reFetch,
}: ColumnDataProps) => {
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
      {isAdding && <Card cardStatus={"editing"} reFetch={reFetch} />}
      {cards.map((cardData, index) => (
        <Card
          key={`${index}_${cardData.cardId}`}
          cardData={cardData}
          reFetch={reFetch}
        />
      ))}
    </div>
  );
};
