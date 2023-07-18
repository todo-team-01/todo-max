import { css } from "@emotion/react";
import { useFetch } from "hooks/useFetch";
import { useEffect, useState } from "react";
import Column from "./Column";
import { FAB } from "./FAB";

export const Main = () => {
  const [columnList, setColumnList] = useState([]);
  const { response, errorMsg, loading, fetch } = useFetch({
    url: "/api",
    method: "get",
    autoFetch: true,
  });

  const onCardChanged = async () => {
    await fetch();
  };

  useEffect(() => {
    response && setColumnList(response);
  }, [response]);

  return (
    <div css={mainStyle}>
      {columnList &&
        columnList.map(({ columnId, columnName, cards }) => (
          <Column
            key={columnId}
            columnId={columnId}
            columnName={columnName}
            cards={cards}
            onCardChanged={onCardChanged}
          />
        ))}
      <FAB onColumnChanged={onCardChanged} />
    </div>
  );
};

const mainStyle = css({
  display: "flex",
  gap: "24px",
  width: "1280px",
  overflow: "hidden",
  overflowX: "scroll",
});
