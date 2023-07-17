import { RemoveModal } from "@components/base/RemoveModal";
import { dropShadow, radius } from "@constants/objectStyle";
import { css } from "@emotion/react";
import { useFetch } from "hooks/useFetch";
import { useState } from "react";
import { CardEditor } from "./CardEditor";
import { CardViewer } from "./CardViewer";

export interface CardData {
  cardId: number;
  title: string;
  content: string;
  writer: string;
}

interface CardProps {
  cardData: CardData;
  onCardChanged: () => void;
}

export const Card = ({ cardData, onCardChanged }: CardProps) => {
  const [status, setStatus] = useState<"viewer" | "editor">("viewer");
  const [isOpenModal, setIsOpenModal] = useState(false);

  const { fetch: fetchDelete } = useFetch({
    url: `/api/cards/${cardData.cardId}`,
    method: "delete",
  });

  const onClickEdit = () => {
    setStatus("editor");
  };

  const exitEdit = () => {
    setStatus("viewer");
  };

  const onSubmit = () => {
    onCardChanged();
    exitEdit();
  };

  const openModal = () => {
    setIsOpenModal(true);
  };

  const closeModal = () => {
    setIsOpenModal(false);
  };

  const onClickRemove = async () => {
    await fetchDelete();
    onCardChanged();
  };

  return (
    <div css={cardStyle}>
      {status === "viewer" ? (
        <>
          <CardViewer
            onClickEdit={onClickEdit}
            onClickRemove={openModal}
            cardData={cardData}
          />
          <RemoveModal
            isOpen={isOpenModal}
            removeHandler={onClickRemove}
            closeHandler={closeModal}
            text={"카드 삭제"}
          />
        </>
      ) : (
        <CardEditor
          cardData={cardData}
          type="edit"
          onCancel={exitEdit}
          onSubmit={onSubmit}
        />
      )}
    </div>
  );
};

export const cardStyle = () => {
  return css`
    width: 300px;
    height: 88px;
    padding: 16px;
    ${radius.radius8}
    ${dropShadow.normal}
  `;
};
