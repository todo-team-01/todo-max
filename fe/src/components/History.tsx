import { colors } from "@constants/colors";
import { useFetch } from "hooks/useFetch";
import { useEffect, useRef, useState } from "react";
import { Button } from "./base/Button";
import { ClosedIcon } from "./icon/ClosedIcon";

interface HistoryData {
  historyId: number;
  historyContent: string;
}

export const History = ({ closeHandler }: { closeHandler: () => void }) => {
  const [historyData, setHistoryData] = useState([]);
  const { response, fetch } = useFetch({
    url: "/api/history",
    method: "get",
    autoFetch: true,
  });

  const { fetch: deleteFetch } = useFetch({
    url: "/api/history",
    method: "delete",
  });

  const historyRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    if (response) {
      setHistoryData(response);
    }

    setTimeout(() => {
      if (historyRef.current) {
        historyRef.current.style.right = "0px";
      }
    }, 0);
  }, [response]);

  const handleClose = () => {
    if (historyRef.current) {
      historyRef.current.style.right = "-350px";
    }

    historyRef.current?.addEventListener("transitionend", closeHandler);
  };

  const handleRemoveHistory = async () => {
    await deleteFetch();
    await fetch();
  };

  const createHighlightElement = (str: string) => {
    return (
      <div css={{ padding: "16px", borderBottom: "1px solid gray" }}>
        {str.split("'").map((part, index) =>
          index % 2 === 0 ? (
            part
          ) : (
            <b key={index} css={{ fontWeight: "bold" }}>
              {part}
            </b>
          )
        )}
      </div>
    );
  };

  return (
    <div
      ref={historyRef}
      css={{
        width: "350px",
        border: "1px solid black",
        position: "absolute",
        right: "-350px",
        top: "100%",
        boxSizing: "border-box",
        transition: "right 1s",
      }}
    >
      <div
        css={{
          display: "flex",
          justifyContent: "space-between",
          padding: "0 16px",
        }}
      >
        <div css={{ display: "flex", alignItems: "center" }}>
          사용자 활동 기록
        </div>
        <div>
          <Button pattern="icon" onClick={handleClose}>
            <ClosedIcon size={24} rgb={colors.textDefault} />
            <div>닫기</div>
          </Button>
        </div>
      </div>
      <div>
        {historyData.length ? (
          historyData.map((data: HistoryData, i) => (
            <div key={i}>{createHighlightElement(data.historyContent)}</div>
          ))
        ) : (
          <div css={{ padding: "16px", textAlign: "center" }}>
            사용자 활동 기록이 없습니다.
          </div>
        )}
      </div>
      <div css={{ display: "flex", justifyContent: "flex-end" }}>
        <Button pattern="text" onClick={handleRemoveHistory}>
          <span css={{ color: colors.red, fontWeight: "bold" }}>
            기록 전체 삭제
          </span>
        </Button>
      </div>
    </div>
  );
};
