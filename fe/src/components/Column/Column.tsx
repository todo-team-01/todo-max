import { useState } from "react";
import { Card } from "../types";
import { ColumnHeader } from "./ColumnHeader";

interface ColumnDataProps {
  columnId: number;
  columnName: string;
  cards: Card[];
}

export const Column = ({ columnId, columnName, cards }: ColumnDataProps) => {
  const [isAdding, setIsAdding] = useState<boolean>(false);
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
    </div>
  );
};
