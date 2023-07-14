import { Button } from "@components/base/Button";
import { Text } from "@components/base/Text";
import { ClosedIcon } from "@components/icon/ClosedIcon";
import { PlusIcon } from "@components/icon/PlusIcon";
import { colors } from "@constants/colors";
import { typography } from "@constants/font";
import { Badge } from "./Badge";

interface ColumnHeaderProps {
  columnId: number;
  columnName: string;
  cardCount: number;
  handleClickAddCard: () => void;
}

export const ColumnHeader = ({
  columnId,
  columnName,
  cardCount,
  handleClickAddCard,
}: ColumnHeaderProps) => {
  const handleClickRemoveColumn = () => {};

  return (
    <div
      css={{
        display: "flex",
        justifyContent: "space-between",
        minWidth: "300px",
      }}
    >
      <div css={{ display: "flex", gap: "4px", alignItems: "center" }}>
        <Text typography={typography.display.bold[16]} color={colors.textBold}>
          {columnName}
        </Text>
        <Badge cardCount={cardCount} />
      </div>
      <div css={{ display: "flex" }}>
        <Button
          pattern="icon"
          onClick={handleClickAddCard}
          iconHoverColor={colors.surfaceBrand}
        >
          <PlusIcon size={24} rgb={colors.textWeak} />
        </Button>
        <Button
          pattern="icon"
          onClick={handleClickRemoveColumn}
          iconHoverColor={colors.red}
        >
          <ClosedIcon size={24} rgb={colors.textWeak} />
        </Button>
      </div>
    </div>
  );
};
