import { COLOR_VARIANTS } from "@constants/colors";
import { useFetch } from "hooks/useFetch";
import { Button } from "./base/Button";
import { PlusIcon } from "./icon/PlusIcon";

export const FAB = ({ onColumnChanged }: { onColumnChanged: () => void }) => {
  const { fetch: columnAddFetch } = useFetch({
    url: "/api/columns",
    method: "post",
  });

  const onColumnAdd = async () => {
    await columnAddFetch({ columnName: "새로운 컬럼" });
    onColumnChanged();
  };

  return (
    <Button
      variant="blue"
      pattern="FAB"
      onClick={onColumnAdd}
      css={{ position: "fixed", bottom: "30px", right: "30px" }}
    >
      <PlusIcon size={32} rgb={COLOR_VARIANTS.textWhiteDefault} />
    </Button>
  );
};
