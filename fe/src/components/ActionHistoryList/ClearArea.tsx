import { Button } from "@components/base/Button";
import { Text } from "@components/base/Text";
import { COLOR_VARIANTS } from "@constants/colors";

export const ClearArea: React.FC<{ onClearButtonClick: () => void }> = ({
  onClearButtonClick,
}) => {
  return (
    <div
      css={{
        display: "flex",
        width: "100%",
        justifyContent: "flex-end",
        height: "40px",
      }}
    >
      <Button pattern="text" onClick={onClearButtonClick}>
        <Text
          typography="displayBold14"
          css={{ color: COLOR_VARIANTS.textDanger }}
        >
          기록 전체 삭제
        </Text>
      </Button>
    </div>
  );
};
