import { useFetch } from "hooks/useFetch";
import { useState } from "react";
import { Button } from "./base/Button";

export const NewCard: React.FC<{
  columnId: number;
  nextCardId: number;
  handleClickCancelAdd: () => void;
  updateColumnList: () => void;
}> = ({ columnId, nextCardId, handleClickCancelAdd, updateColumnList }) => {
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");
  const { response: newCard, fetch: fetchAdd } = useFetch({
    url: "/api/cards",
    method: "post",
    body: {
      columnId: columnId,
      cardTitle: title,
      cardContent: content,
      nextCardId: nextCardId,
    },
  });

  const handleChangeTitle = (e: React.ChangeEvent<HTMLInputElement>) => {
    setTitle(e.target.value);
  };
  const handleChangeContent = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    setContent(e.target.value);
  };
  const handleClickAdd = async () => {
    await fetchAdd();
    handleClickCancelAdd();
    updateColumnList();
  };

  return (
    <div css={{ border: "1px solid black" }}>
      <div css={{ display: "flex", justifyContent: "space-between" }}>
        <div css={{ display: "flex", flexDirection: "column" }}>
          <input
            type="text"
            placeholder={"제목을 입력하세요"}
            onChange={handleChangeTitle}
          />
          <textarea
            placeholder={"내용을 입력하세요"}
            onChange={handleChangeContent}
          />
        </div>
      </div>
      <div css={{ display: "flex", justifyContent: "space-around" }}>
        <Button variant="gray" text="취소" onClick={handleClickCancelAdd} />
        <Button variant="blue" text="등록" onClick={handleClickAdd} />
      </div>
    </div>
  );
};
