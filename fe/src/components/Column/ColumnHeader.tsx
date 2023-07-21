import { RemoveModal } from "@components/base/RemoveModal";
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
  const [isOpenModal, setIsOpenModal] = useState(false);
  const [status, setStatus] = useState<"viewer" | "editor">("viewer");

  const { fetch: deleteColumnFetch } = useFetch({
    url: `/api/columns/${columnId}`,
    method: "delete",
  });

  const onNameDoubleClick = () => setStatus("editor");
  const onNameOutsideClick = () => setStatus("viewer");

  const openModal = () => setIsOpenModal(true);
  const closeModal = () => setIsOpenModal(false);

  const onDeleteColumnClick = async () => {
    await deleteColumnFetch();
    closeModal();
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
        onDeleteButtonClick={openModal}
      />
      <RemoveModal
        isOpen={isOpenModal}
        removeHandler={onDeleteColumnClick}
        closeHandler={closeModal}
        text={`'${columnName}' 칼럼을 삭제할까요?`}
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

