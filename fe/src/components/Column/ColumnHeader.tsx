import { Text } from "@components/base/Text";
import { useFetch } from "hooks/useFetch";
import { useState } from "react";
import { Badge } from "./Badge";
import { ColumnButtons } from "./ColumnButtons";
import { NameEditor } from "./NameEditor";

interface ColumnHeaderProps {
  columnId: number;
  columnName: string;
  cardCount: number;
  onAddCardClick: () => void;
  onCardChanged: () => void;
}

export const ColumnHeader = ({
  columnId,
  columnName,
  cardCount,
  onAddCardClick,
  onCardChanged,
}: ColumnHeaderProps) => {
  const { fetch: deleteColumnFetch } = useFetch({
    url: `/api/columns/${columnId}`,
    method: "delete",
  });
  const [status, setStatus] = useState<"viewer" | "editor">("viewer");

  const onNameDoubleClick = () => setStatus("editor");
  const onNameOutsideClick = () => setStatus("viewer");
  const onDeleteColumnClick = async () => {
    await deleteColumnFetch();
    onCardChanged();
  };

  return (
    <div css={headerStyle}>
      <div css={titleStyle} onDoubleClick={onNameDoubleClick}>
        {status === "viewer" ? (
          <>
            <Text typography="displayBold16" color="textBold">
              {columnName}
            </Text>
            <Badge cardCount={cardCount} />
          </>
        ) : (
          <NameEditor
            columnId={columnId}
            columnName={columnName}
            onNameOutsideClick={onNameOutsideClick}
            onCardChanged={onCardChanged}
          />
        )}
      </div>
      <ColumnButtons
        onAddButtonClick={onAddCardClick}
        onDeleteButtonClick={onDeleteColumnClick}
      />
    </div>
  );
};

const headerStyle = {
  display: "flex",
  justifyContent: "space-between",
  padding: "0 16px",
  minWidth: "300px",
  gap: "4px",
};

const titleStyle = { display: "flex", gap: "8px", alignItems: "center" };
