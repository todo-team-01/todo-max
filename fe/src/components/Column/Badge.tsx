import { colors } from "@constants/colors";
import { typography } from "@constants/font";
import { radius } from "@constants/objectStyle";
import { css } from "@emotion/react";

export const Badge = ({ cardCount }: { cardCount: number }) => {
  const isMoreTwoDigits = cardCount > 99;

  return (
    <div css={badgeStyle(isMoreTwoDigits)}>
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

const badgeStyle = (isMoreTwoDigits: boolean) => css`
  display: flex;
  justify-content: center;
  align-items: center;
  height: 1.5rem;
  width: ${isMoreTwoDigits ? "40px" : "1.5rem"};
  border: 1px solid ${colors.borderDefault};
  color: ${colors.textWeak};
  ${radius.radius8};
`;
