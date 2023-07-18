import { COLOR_VARIANTS } from "@constants/colors";
import { css } from "@emotion/react";
import { useFetch } from "hooks/useFetch";
import { useEffect, useState } from "react";
import Column from "./Column";
import { Button } from "./base/Button";
import { PlusIcon } from "./icon/PlusIcon";

export const Main = () => {
  const [columnList, setColumnList] = useState([]);
  const columnCount = columnList.length;

  const { response, errorMsg, loading, fetch } = useFetch({
    url: "/api",
    method: "get",
    autoFetch: true,
  });
  const { fetch: columnAddFetch } = useFetch({
    url: "/api/columns",
    method: "post",
    body: {
      columnName: `새로운 컬럼 ${columnCount + 1}`,
    },
  });

  const onCardChanged = async () => {
    await fetch();
  };

  const onColumnAdd = async () => {
    await columnAddFetch();
    onCardChanged();
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
      <Button
        variant="blue"
        pattern="FAB"
        onClick={onColumnAdd}
        css={{ position: "fixed", bottom: "30px", right: "30px" }}
      >
        <PlusIcon size={32} rgb={COLOR_VARIANTS.textWhiteDefault} />
      </Button>
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
