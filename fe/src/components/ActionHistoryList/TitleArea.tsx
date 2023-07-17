import { Button } from "@components/base/Button";
import { Text } from "@components/base/Text";
import { ClosedIcon } from "@components/icon/ClosedIcon";
import { COLOR_VARIANTS } from "@constants/colors";
import React from "react";

export const TitleArea: React.FC<{ handleClose: () => void }> = ({
  handleClose,
}) => {
  return (
    <div css={titleAreaStyle}>
      <Text typography="displayBold16" css={textStyle}>
        사용자 활동 기록
      </Text>
      <div>
        <Button pattern="icon" onClick={handleClose} css={closeButtonStyle}>
          <ClosedIcon size={16} rgb={COLOR_VARIANTS.textDefault} />
          <Text typography="displayBold14">닫기</Text>
        </Button>
      </div>
    </div>
  );
};

const titleAreaStyle = {
  display: "flex",
  height: "48px",
  alignItems: "center",
  justifyContent: "space-between",
  padding: "0 16px",
  gap: "4px",
};

const textStyle = {
  display: "flex",
  alignItems: "center",
  color: COLOR_VARIANTS.textStrong,
};

const closeButtonStyle = {
  justifyContent: "space-between",
  display: "flex",
  gap: "4px",
};
