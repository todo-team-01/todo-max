import { COLOR_VARIANTS } from "@constants/colors";
import { useEffect, useState } from "react";
import ActionHistoryList from "./ActionHistoryList";
import { Button } from "./base/Button";
import { Text } from "./base/Text";
import { HistoryIcon } from "./icon/HistoryIcon";

export const Header = () => {
  const [isOpenActionHistory, setIsOpenActionHistory] = useState(false);
  const [isFixed, setIsFixed] = useState(true);

  const openActionHistory = () => setIsOpenActionHistory(true);
  const closeActionHistory = () => setIsOpenActionHistory(false);

  const handleScroll = () => {
    setIsFixed(window.scrollY === 0);
  };

  useEffect(() => {
    window.addEventListener("scroll", handleScroll);
    return () => {
      window.removeEventListener("scroll", handleScroll);
    };
  }, []);

  return (
    <div
      css={{
        width: "100%",
        paddingTop: isFixed ? "64px" : "0",
      }}
    >
      <div
        css={{
          background: "white",
          position: isFixed ? "fixed" : "relative",
          top: "0%",
          left: "5%",
          width: "90%",
          height: "64px",
          display: "flex",
          justifyContent: "space-between",
          alignItems: "center",
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
    </div>
  );
};

