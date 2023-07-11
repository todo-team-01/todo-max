import { colors } from "@constants/colors";
import { typography } from "@constants/font";

export const Badge = ({ cardCount }: { cardCount: number }) => {
  const isMoreTwoDigits = cardCount > 99;

  return (
    <div
      css={{
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        width: "1.5rem",
        height: "1.5rem",
        border: `1px solid ${colors.borderDefault}`,
        padding: "0px, 4px",
        borderRadius: "8px",
        color: colors.textWeak,
      }}
    >
      <span
        css={{
          ...typography.display.medium[12],
        }}
      >
        {isMoreTwoDigits ? "99+" : cardCount}
      </span>
    </div>
  );
};
