import { colors } from "@constants/colors";
import { Button } from "./base/Button";
import { HistoryIcon } from "./icon/HistoryIcon";

export const Header = () => {
  const historyFun = () => {
    console.log("history");
  };

  return (
    <div>
      <div>TODO LIST</div>
      <Button onClick={historyFun}>
        <HistoryIcon size={24} rgb={colors.textDefault} />
      </Button>
    </div>
  );
};
