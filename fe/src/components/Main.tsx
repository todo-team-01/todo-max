import { useFetch } from "hooks/useFetch";
import { Column } from "./Column";

export const Main = () => {
  const { response, errorMsg, loading } = useFetch({ url: "/", method: "get" });

  if (loading) return <div>Loading...</div>;
  if (errorMsg) return <div>{errorMsg}</div>;

  return (
    <div>
      {response.map(({ columnId, columnName, cards }) => (
        <Column
          key={columnId}
          columnId={columnId}
          columnName={columnName}
          cards={cards}
        />
      ))}
    </div>
  );
};
