import { Position } from "@components/Main";
import { RemoveModal } from "@components/base/RemoveModal";
import { dropShadow, radius } from "@constants/objectStyle";
import { css } from "@emotion/react";
import { useFetch } from "hooks/useFetch";
import React, { memo, useEffect, useState } from "react";
import { CardEditor } from "./CardEditor";
import { CardViewer } from "./CardViewer";

export interface CardData {
  cardId: number;
  cardTitle: string;
  cardContent: string;
  writer: string;
}

interface CardProps {
  cardData: CardData;
  onCardChanged: () => void;
  setCloneCard: (CardData: CardData, initialPosition: Position) => void;
}

export const Card = memo(
  ({ cardData, onCardChanged, setCloneCard }: CardProps) => {
    const [status, setStatus] = useState<"viewer" | "editor">("viewer");
    const [isMouseDown, setIsMouseDown] = useState(false);
    const [isDragging, setIsDragging] = useState(false);
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

    const onMouseDownHandler = (e: React.MouseEvent) => {
      const canMouseDown =
        status === "viewer" && (e.target as Element)?.tagName !== "BUTTON";

      if (canMouseDown) {
        e.preventDefault();
        setIsMouseDown(true);
      }
    };

    const onMouseMoveHandler = (e: React.MouseEvent) => {
      const canMouseMove =
        status === "viewer" &&
        (e.target as Element)?.tagName !== "BUTTON" &&
        isMouseDown;

      if (canMouseMove) {
        e.preventDefault();
        setCloneCard(cardData, { x: e.clientX, y: e.clientY });
        setIsDragging(true);
      }
    };

    const onMouseUpHandler = () => {
      setIsMouseDown(false);
      setIsDragging(false);
    };

    useEffect(() => {
      window.addEventListener("mouseup", onMouseUpHandler);
      return () => {
        window.removeEventListener("mouseup", onMouseUpHandler);
      };
    }, []);

    return (
      <div
        css={[cardStyle, isDragging && { display: "none" }]}
        onMouseDown={onMouseDownHandler}
        onMouseMove={onMouseMoveHandler}
      >
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
  }
);

export const cardStyle = () => {
  return css`
    width: 300px;
    height: auto;
    padding: 16px;
    ${radius.radius8}
    ${dropShadow.normal}
  `;
};
