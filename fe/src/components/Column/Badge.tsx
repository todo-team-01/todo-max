import { Text } from "@components/base/Text";
import { COLOR_VARIANTS } from "@constants/colors";
import { radius } from "@constants/objectStyle";
import { css } from "@emotion/react";

export const Badge = ({ cardCount }: { cardCount: number }) => {
  const isMoreTwoDigits = cardCount > 99;

  return (
    <div css={badgeStyle(isMoreTwoDigits)}>
      <Text>{isMoreTwoDigits ? "99+" : cardCount}</Text>
    </div>
  );
};

const badgeStyle = (isMoreTwoDigits: boolean) => css`
  display: flex;
  justify-content: center;
  align-items: center;
  height: 1.5rem;
  width: ${isMoreTwoDigits ? "40px" : "1.5rem"};
  border: 1px solid ${COLOR_VARIANTS.borderDefault};
  color: ${COLOR_VARIANTS.textWeak};
  ${radius.radius8};
`;
