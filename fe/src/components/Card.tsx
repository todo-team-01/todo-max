import { colors } from "@constants/colors";
import { useFetch } from "hooks/useFetch";
import { useState } from "react";
import { Button } from "./base/Button";
import { RemoveModal } from "./base/RemoveModal";
import { ClosedIcon } from "./icon/ClosedIcon";
import { EditIcon } from "./icon/EditIcon";

export interface CardData {
  cardId: number;
  title: string;
  content: string;
  writer: string;
}

interface CardProps {
  cardData: CardData;
  updateColumnList: () => void;
}
export const Card = ({ cardData, updateColumnList }: CardProps) => {
  const [status, setStatus] = useState("normal");
  const [title, setTitle] = useState(cardData.title);
  const [content, setContent] = useState(cardData.content);
  const [isOpenModal, setIsOpenModal] = useState(false);

  const {
    errorMsg: errorMsgDelete,
    loading: loadingDelete,
    fetch: fetchDelete,
  } = useFetch({
    url: `/api/cards/${cardData.cardId}`,
    method: "delete",
  });

  const {
    errorMsg: errorMsgUpdate,
    loading: loadingUpdate,
    fetch: fetchUpdate,
  } = useFetch({
    url: `/api/cards/${cardData.cardId}`,
    method: "put",
    body: {
      changedCardTitle: title,
      changedCardContent: content,
    },
  });

  const openModal = () => {
    setIsOpenModal(true);
  };

  const closeModal = () => {
    setIsOpenModal(false);
  };

  const handleClickRemove = async () => {
    await fetchDelete();
    updateColumnList();
  };

  const handleClickUpdate = async () => {
    if (cardData.title !== title || cardData.content !== content) {
      setStatus("normal");
      await fetchUpdate();
      updateColumnList();
    }
  };

  const handleClickEdit = () => {
    setStatus("editing");
  };

  const handleClickCancelEdit = () => {
    setStatus("normal");
    setContent(cardData.content);
    setTitle(cardData.title);
  };

  const handleTitleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setTitle(e.target.value);
  };

  const handleTextChange = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    setContent(e.target.value);
  };

  return (
    <div css={{ border: "1px solid black" }}>
      <div css={{ display: "flex", justifyContent: "space-between" }}>
        <div css={{ display: "flex", flexDirection: "column" }}>
          {status === "editing" ? (
            <>
              <input
                type="content"
                value={title}
                onChange={handleTitleChange}
              />
              <textarea value={content} onChange={handleTextChange} />
            </>
          ) : (
            <>
              <div>{cardData && cardData.title}</div>
              <div>{cardData && cardData.content}</div>
            </>
          )}
          <div> {cardData && `author by ${cardData.writer}`}</div>
        </div>
        {status !== "editing" && (
          <div css={{ right: "0" }}>
            <Button onClick={openModal}>
              <ClosedIcon size={24} rgb={colors.textDefault} />
            </Button>
            <Button onClick={handleClickEdit}>
              <EditIcon size={24} rgb={colors.textDefault} />
            </Button>
          </div>
        )}
      </div>

      {status === "editing" && (
        <div css={{ display: "flex", justifyContent: "space-around" }}>
          <Button variant="gray" text="취소" onClick={handleClickCancelEdit} />
          <Button variant="blue" text="등록" onClick={handleClickUpdate} />
        </div>
      )}

      <RemoveModal
        isOpen={isOpenModal}
        removeHandler={handleClickRemove}
        closeHandler={closeModal}
        text={"카드 삭제"}
      />
    </div>
  );
};
