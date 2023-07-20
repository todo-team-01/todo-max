import { Button } from "@components/base/Button";
import { Text } from "@components/base/Text";
import { ClosedIcon } from "@components/icon/ClosedIcon";
import { EditIcon } from "@components/icon/EditIcon";
import { COLOR_VARIANTS } from "@constants/colors";
import { CardData } from "./Card";

interface CardViewerProps {
  cardData: CardData;
  onClickEdit?: () => void;
  onClickRemove?: () => void;
}

export const CardViewer = ({
  cardData,
  onClickEdit,
  onClickRemove,
}: CardViewerProps) => {
  const { cardTitle, cardContent, writer } = cardData;

  return (
    <div
      css={{ display: "flex", justifyContent: "space-between", height: "100%" }}
    >
      <div
        css={{
          display: "flex",
          flexDirection: "column",
          justifyContent: "space-between",
        }}
      >
        <div css={{ display: "flex", flexDirection: "column", gap: "8px" }}>
          <Text
            css={{ height: "17px", color: COLOR_VARIANTS.textStrong }}
            typography="displayBold14"
          >
            {cardTitle}
          </Text>

          <Text
            css={{ height: "17px", color: COLOR_VARIANTS.textDefault }}
            typography="displayBold14"
          >
            {cardContent}
          </Text>
        </div>
        <Text
          css={{ color: COLOR_VARIANTS.textDefault }}
          typography="displayBold12"
        >
          author by {writer}
        </Text>
      </div>
      <div>
        <Button
          pattern="icon"
          onClick={onClickRemove}
          iconHoverColor="surfaceDanger"
        >
          <ClosedIcon size={24} rgb={COLOR_VARIANTS.textDefault} />
        </Button>
        <Button pattern="icon" onClick={onClickEdit}>
          <EditIcon size={24} rgb={COLOR_VARIANTS.textDefault} />
        </Button>
      </div>
    </div>
  );
};
