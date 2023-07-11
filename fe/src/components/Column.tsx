interface ColumnDataProps {
  columnId: number;
  columnName: string;
  cards: Card[];
}
interface ColumnHeaderrops {
  columnId: number;
  columnName: string;
}

interface Card {
  id?: number;
  name?: string;
  title?: string;
  text?: string;
  writer?: string;
}

export const Column = ({ columnId, columnName, cards }: ColumnDataProps) => {
  console.log(columnId, columnName, cards);
  return <div>Column</div>;
};
