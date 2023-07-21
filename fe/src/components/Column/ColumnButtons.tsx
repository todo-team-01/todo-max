import { Button } from "@components/base/Button";
import { ClosedIcon } from "@components/icon/ClosedIcon";
import { PlusIcon } from "@components/icon/PlusIcon";
import { COLOR_VARIANTS } from "@constants/colors";

export const ColumnButtons = ({
  onAddButtonClick,
  onDeleteButtonClick,
}: {
  onAddButtonClick: () => void;
  onDeleteButtonClick: () => void;
}) => {
  return (
    <div css={{ display: "flex" }}>
      <Button
        pattern="icon"
        onClick={onAddButtonClick}
        iconHoverColor="surfaceBrand"
      >
        <PlusIcon size={24} rgb={COLOR_VARIANTS.textWeak} />
      </Button>
      <Button
        pattern="icon"
        onClick={onDeleteButtonClick}
        iconHoverColor="surfaceDanger"
      >
        <ClosedIcon size={24} rgb={COLOR_VARIANTS.textWeak} />
      </Button>
    </div>
  );
};
