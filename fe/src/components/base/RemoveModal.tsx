import { dropShadow, radius } from "@constants/objectStyle";
import { css } from "@emotion/react";
import { useEffect, useRef } from "react";
import { Button } from "./Button";

interface CancelModalProps {
  text: string;
  isOpen: boolean;
  removeHandler: () => void;
  closeHandler: () => void;
}

export const RemoveModal = ({
  text,
  isOpen,
  removeHandler,
  closeHandler,
}: CancelModalProps) => {
  const modalRef = useRef<HTMLDialogElement>(null);

  useEffect(() => {
    isOpen && modalRef.current?.showModal();
  }, [isOpen]);

  const handleClickClose = () => {
    closeHandler();
    modalRef.current?.close();
  };

  return (
    <dialog
      css={css`
        width: 320px;
        height: 126px;
        padding: 24px;
        box-sizing: border-box;
        border: none;
        &::backdrop {
          background: rgba(0, 0, 0, 0.3);
        }
        ${radius.radius8}
        ${dropShadow.up}
      `}
      ref={modalRef}
      onClose={handleClickClose}
    >
      <div
        css={{
          height: "100%",
          display: "flex",
          flexDirection: "column",
          justifyContent: "space-between",
        }}
      >
        <div>{text}</div>
        <div css={{ display: "flex", justifyContent: "space-between" }}>
          <Button pattern="text" variant="gray" onClick={handleClickClose}>
            <span>취소</span>
          </Button>
          <Button pattern="text" variant="red" onClick={removeHandler}>
            <span>삭제</span>
          </Button>
        </div>
      </div>
    </dialog>
  );
};
