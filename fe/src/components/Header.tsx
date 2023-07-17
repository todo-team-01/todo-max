import { COLOR_VARIANTS } from "@constants/colors";
import { useState } from "react";
import ActionHistoryList from "./ActionHistoryList";
import { Button } from "./base/Button";
import { Text } from "./base/Text";
import { HistoryIcon } from "./icon/HistoryIcon";

export const Header = () => {
  const [isOpenActionHistory, setIsOpenActionHistory] = useState(false);

  const openActionHistory = () => setIsOpenActionHistory(true);
  const closeActionHistory = () => setIsOpenActionHistory(false);

  return (
    <div
      css={{
        display: "flex",
        width: "90%",
        height: "64px",
        justifyContent: "space-between",
        alignItems: "center",
        position: "relative",
      }}
    >
      <Text typography="displayBold24">TODO LIST</Text>
      <Button pattern="icon" onClick={openActionHistory}>
        <HistoryIcon size={24} rgb={COLOR_VARIANTS.textDefault} />
      </Button>
      {isOpenActionHistory && (
        <ActionHistoryList onClose={closeActionHistory} />
      )}
    </div>
  );
};
