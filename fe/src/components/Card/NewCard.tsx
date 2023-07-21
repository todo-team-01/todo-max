import { cardStyle } from "./Card";
import { CardEditor } from "./CardEditor";

interface NewCardProps {
  columnId: number;
  onAddCancelClick: () => void;
  onCardChanged: () => void;
}

export const NewCard = ({
  columnId,
  onAddCancelClick,
  onCardChanged,
}: NewCardProps) => {
  const onSubmit = () => {
    onCardChanged();
    onAddCancelClick();
  };

  return (
    <div css={cardStyle}>
      <CardEditor
        type="add"
        onCancel={onAddCancelClick}
        onSubmit={onSubmit}
        columnId={columnId}
      />
    </div>
  );
};
