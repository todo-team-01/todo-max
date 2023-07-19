import { Button } from "@components/base/Button";
import { COLOR_VARIANTS } from "@constants/colors";
import { Medium } from "@constants/font";
import { css } from "@emotion/react";
import { useFetch } from "hooks/useFetch";
import { useState } from "react";
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
  const [title, setTitle] = useState(cardData?.title || "");
  const [content, setContent] = useState(cardData?.content || "");
  const isAllFilled = !!title && !!content;

  const { fetch: fetchAdd } = useFetch({
    url: "/api/cards",
    method: "post",
    body: {
      columnId: columnId,
      cardTitle: title,
      cardContent: content,
    },
  });

  const { fetch: fetchUpdate } = useFetch({
    url: `/api/cards/${cardData?.cardId}`,
    method: "put",
    body: {
      changedCardTitle: title,
      changedCardContent: content,
    },
  });

  const handleTitleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setTitle(e.target.value);
  };

  const handleContentChange = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    setContent(e.target.value);
  };

  const handleClickCancel = () => {
    onCancel();
    setTitle(cardData?.title || "");
    setContent(cardData?.content || "");
  };

  const handleClickSubmit = async () => {
    const fetchType = {
      add: fetchAdd,
      edit: fetchUpdate,
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
        height: "100%",
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
          placeholder={"제목을 입력하세요"}
          value={title}
          onChange={handleTitleChange}
        />
        <textarea
          css={css(
            {
              border: "none",
              height: "17px",
              color: COLOR_VARIANTS.textDefault,
            },
            Medium.R
          )}
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
