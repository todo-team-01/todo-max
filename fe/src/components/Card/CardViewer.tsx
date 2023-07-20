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
      css={{
        display: "flex",
        justifyContent: "space-between",
        minHeight: "88px",
        height: "auto",
      }}
    >
      <div
        css={{
          display: "flex",
          flexDirection: "column",
          justifyContent: "space-between",
        }}
      >
        <div
          css={{
            display: "flex",
            flexDirection: "column",
            gap: "8px",
            overflow: "hidden",
          }}
        >
          <Text
            css={{
              height: "auto",
              wordBreak: "break-word",
              whiteSpace: "pre-wrap",
            }}
            typography="displayBold14"
          >
            {cardTitle}
          </Text>

          <Text
            css={{
              height: "auto",
              wordBreak: "break-word",
              whiteSpace: "pre-wrap",
            }}
            typography="displayBold14"
          >
            {cardContent}
          </Text>
        </div>
        <Text
          css={{ color: COLOR_VARIANTS.textDefault, marginTop: "8px" }}
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
