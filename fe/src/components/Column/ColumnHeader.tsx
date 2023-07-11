import { Button } from "@components/base/Button";
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
        <span css={{ ...typography.display.bold[16] }}>{columnName}</span>
        <Badge cardCount={cardCount} />
      </div>
      <div css={{ display: "flex" }}>
        <Button onClick={handleClickAddCard}>
          <PlusIcon size={24} rgb={colors.textWeak} />
        </Button>
        <Button onClick={handleClickRemoveColumn}>
          <ClosedIcon size={24} rgb={colors.textWeak} />
        </Button>
      </div>
    </div>
  );
};
