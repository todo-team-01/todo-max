import { useFetch } from "hooks/useFetch";
import { useEffect, useState } from "react";
import Column from "./Column";

export const Main = () => {
  const [columnList, setColumnList] = useState([]);
  const { response, errorMsg, loading, fetch } = useFetch({
    url: "/api",
    method: "get",
    autoFetch: true,
  });

  useEffect(() => {
    response && setColumnList(response);
  }, [response]);

  const onCardChanged = async () => {
    await fetch();
  };

  // if (loading) return <div>Loading...</div>;
  // if (errorMsg) return <div>{errorMsg}</div>;

  return (
    <div css={{ display: "flex", gap: "24px" }}>
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
    </div>
  );
};
