import { colors } from "@constants/colors";
import { Button } from "./base/Button";
import { ClosedIcon } from "./icon/ClosedIcon";
import { EditIcon } from "./icon/EditIcon";
import { useState } from "react";
import { useFetch } from "hooks/useFetch";

export interface CardData {
  cardId: number;
  title: string;
  text: string;
  writer: string;
}

interface CardProps {
  cardData?: CardData;
  cardStatus?: "editing" | "normal";
  reFetch: () => void;
}

export const Card = ({
  cardData,
  cardStatus = "normal",
  reFetch,
}: CardProps) => {
  const [status, setStatus] = useState(cardStatus);

  const { errorMsg, loading, fetch } = useFetch({
    url: `/cards/${cardData?.cardId}`,
    method: "delete",
    autoFetch: false,
  });

  const handleClickRemove = async () => {
    await fetch();
    reFetch();
  };

  const handleClickEdit = () => {
    setStatus("editing");
  };

  const handleClickCancelEdit = () => {
    setStatus("normal");
  };

  return (
    <div
      css={{
        border: "1px solid black",
      }}
    >
      <div css={{ display: "flex", justifyContent: "space-between" }}>
        <div css={{ display: "flex", flexDirection: "column" }}>
          {status === "editing" ? (
            <>
              <input type="text" defaultValue={cardData && cardData.title} />
              <textarea defaultValue={cardData && cardData.text} />
            </>
          ) : (
            <>
              <div>{cardData && cardData.text}</div>
              <div>{cardData && cardData.title}</div>
            </>
          )}
          <div> {cardData && `author by ${cardData.writer}`}</div>
        </div>
        {status !== "editing" && (
          <div css={{ right: "0" }}>
            <Button onClick={handleClickRemove}>
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
          <Button variant="blue" text="등록" onClick={() => {}} />
        </div>
      )}
    </div>
  );
};
