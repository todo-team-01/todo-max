import { Button } from "@components/base/Button";
import { Text } from "@components/base/Text";
import { ClosedIcon } from "@components/icon/ClosedIcon";
import { PlusIcon } from "@components/icon/PlusIcon";
import { COLOR_VARIANTS } from "@constants/colors";
import { useFetch } from "hooks/useFetch";
import { Badge } from "./Badge";

interface ColumnHeaderProps {
  columnId: number;
  columnName: string;
  cardCount: number;
  handleClickAddCard: () => void;
  onCardChanged: () => void;
}

export const ColumnHeader = ({
  columnId,
  columnName,
  cardCount,
  handleClickAddCard,
  onCardChanged,
}: ColumnHeaderProps) => {
  const { fetch: deleteColumnFetch } = useFetch({
    url: `/api/columns/${columnId}`,
    method: "delete",
  });
  const handleClickRemoveColumn = () => {
    deleteColumnFetch();
    onCardChanged();
  };

  return (
    <div
      css={{
        display: "flex",
        justifyContent: "space-between",
        minWidth: "300px",
      }}
    >
      <div css={{ display: "flex", gap: "4px", alignItems: "center" }}>
        <Text typography="displayBold16" color="textBold">
          {columnName}
        </Text>
        <Badge cardCount={cardCount} />
      </div>
      <div css={{ display: "flex" }}>
        <Button
          pattern="icon"
          onClick={handleClickAddCard}
          iconHoverColor="surfaceBrand"
        >
          <PlusIcon size={24} rgb={COLOR_VARIANTS.textWeak} />
        </Button>
        <Button
          pattern="icon"
          onClick={handleClickRemoveColumn}
          iconHoverColor="surfaceDanger"
        >
          <ClosedIcon size={24} rgb={COLOR_VARIANTS.textWeak} />
        </Button>
      </div>
    </div>
  );
};
