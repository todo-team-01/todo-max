import { Button } from "@components/base/Button";
import { COLOR_VARIANTS } from "@constants/colors";
import { Medium } from "@constants/font";
import { css } from "@emotion/react";
import { useFetch } from "hooks/useFetch";
import { useEffect, useRef, useState } from "react";
import { CardData } from "./Card";

interface CardEditorProps {
  cardData?: CardData;
  columnId?: number;
  type: "add" | "edit";
  onCancel: () => void;
  onSubmit: () => void;
}

export const CardEditor = ({
  cardData,
  columnId,
  type,
  onCancel,
  onSubmit,
}: CardEditorProps) => {
  const [title, setTitle] = useState(cardData?.cardTitle || "");
  const [content, setContent] = useState(cardData?.cardContent || "");
  const textAreaRef = useRef<HTMLTextAreaElement>(null);

  const isAllFilled = !!title && !!content;

  useEffect(() => {
    if (textAreaRef.current) {
      textAreaRef.current.style.height =
        textAreaRef.current.scrollHeight + "px";
    }
  }, [content]);

  const { fetch: fetchAdd } = useFetch({
    url: "/api/cards",
    method: "post",
  });

  const { fetch: fetchUpdate } = useFetch({
    url: `/api/cards/${cardData?.cardId}`,
    method: "put",
  });

  const handleTitleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setTitle(e.target.value);
  };

  const handleContentChange = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    setContent(e.target.value);
  };

  const handleClickCancel = () => {
    onCancel();
    setTitle(cardData?.cardTitle || "");
    setContent(cardData?.cardContent || "");
  };

  const handleClickSubmit = async () => {
    const fetchType = {
      add: () =>
        fetchAdd({
          columnId: columnId,
          cardTitle: title,
          cardContent: content,
        }),
      edit: () =>
        fetchUpdate({
          changedCardTitle: title,
          changedCardContent: content,
        }),
    };

    await fetchType[type]();
    onSubmit();
  };

  return (
    <div
      css={{
        display: "flex",
        flexDirection: "column",
        justifyContent: "space-between",
        minHeight: "88px",
        height: "auto",
        gap: "8px",
      }}
    >
      <div css={{ display: "flex", flexDirection: "column", gap: "8px" }}>
        <input
          css={css(
            {
              border: "none",
              height: "17px",
              color: COLOR_VARIANTS.textStrong,
            },
            Medium.R
          )}
          type="text"
          maxLength={25}
          placeholder={"제목을 입력하세요"}
          value={title}
          onChange={handleTitleChange}
        />
        <textarea
          ref={textAreaRef}
          css={css(
            {
              border: "none",
              height: "17px",
              boxSizing: "border-box",
              color: COLOR_VARIANTS.textDefault,
            },
            Medium.R
          )}
          maxLength={500}
          placeholder={"내용을 입력하세요"}
          value={content}
          onChange={handleContentChange}
        />
      </div>
      <div css={{ display: "flex", justifyContent: "space-around" }}>
        <Button pattern="text" variant="gray" onClick={handleClickCancel}>
          <span>취소</span>
        </Button>
        <Button
          pattern="text"
          variant="blue"
          onClick={handleClickSubmit}
          disabled={!isAllFilled}
        >
          <span>{type === "edit" ? "저장" : "등록"}</span>
        </Button>
      </div>
    </div>
  );
};
