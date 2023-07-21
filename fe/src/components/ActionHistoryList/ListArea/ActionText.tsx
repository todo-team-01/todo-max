import { Text } from "@components/base/Text";
import { COLOR_VARIANTS } from "@constants/colors";

export const ActionText: React.FC<{ historyText: string }> = ({
  historyText,
}) => {
  const highlightTextParts = (historyText: string) => {
    const parts = historyText.split("'");

    return parts.map((part, index) => {
      const isHighlighted = index % 2 !== 0;
      const typography = isHighlighted ? "displayBold14" : "displayMedium14";
      const color = isHighlighted
        ? COLOR_VARIANTS.textBold
        : COLOR_VARIANTS.textDefault;

      return (
        <Text key={index} typography={typography} css={{ color }}>
          {part}
        </Text>
      );
    });
  };

  return (
    <div css={actionHistoryItemStyle}>{highlightTextParts(historyText)}</div>
  );
};

const actionHistoryItemStyle = {
  width: "250px",
};
