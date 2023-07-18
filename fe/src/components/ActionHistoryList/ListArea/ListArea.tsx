import { COLOR_VARIANTS } from "@constants/colors";
import { css } from "@emotion/react";
import { HistoryData } from "../ActionHistoryList";
import { ActionText } from "./ActionText";
import { TimeStamp } from "./TimeStamp";

export const ListArea: React.FC<{ activeHistoryList: HistoryData[] }> = ({
  activeHistoryList,
}) => {
  return (
    <div css={listAreaStyle}>
      {activeHistoryList.map((data: HistoryData) => (
        <div key={data.historyId} css={actionItemStyle}>
          <div css={ProfileStyle}></div>
          <div css={actionContentStyle}>
            <ActionText historyText={data.historyContent} />
            <TimeStamp historyCreatedAt={data.historyCreatedAt} />
          </div>
        </div>
      ))}
    </div>
  );
};

const actionItemStyle = {
  display: "flex",
  justifyContent: "space-evenly",
  padding: "8px",
  gap: "8px",
  borderBottom: `1px solid ${COLOR_VARIANTS.borderDefault}`,
  ":last-child": {
    borderBottom: "none",
  },
};

const actionContentStyle = css({
  display: "flex",
  flexDirection: "column",
  alignItems: "flex-start",
  justifyContent: "flex-start",
  gap: "8px",
});

const listAreaStyle = css({
  display: "flex",
  flexDirection: "column",
  width: "330px",
  justifyContent: "center",
  gap: "8px",
});

const ProfileStyle = {
  borderRadius: "50%",
  width: "40px",
  height: "40px",
  color: COLOR_VARIANTS.surfaceBrand,
  backgroundImage: `url("assets/profile.jpg")`,
  backgroundSize: "cover",
};
