import { useFetch } from "hooks/useFetch";
import Column from "./Column";
import { useEffect, useState } from "react";

export const Main = () => {
  const [columnList, setColumnList] = useState([]);
  const { response, errorMsg, loading, fetch } = useFetch({
    url: "/",
    method: "get",
    autoFetch: true,
  });

  useEffect(() => {
    if (response) {
      setColumnList(response);
    }
  }, [response]);

  const reFetch = async () => {
    await fetch();
  };

  if (loading) return <div>Loading...</div>;
  if (errorMsg) return <div>{errorMsg}</div>;

  return (
    <div css={{ display: "flex" }}>
      {columnList &&
        columnList.map(({ columnId, columnName, cards }) => (
          <Column
            key={columnId}
            columnId={columnId}
            columnName={columnName}
            cards={cards}
            reFetch={reFetch}
          />
        ))}
    </div>
  );
};
