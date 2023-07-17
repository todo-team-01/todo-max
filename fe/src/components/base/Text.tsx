import { COLOR_VARIANTS, Color } from "@constants/colors";
import { Bold, Medium } from "@constants/font";
import { HTMLAttributes } from "react";

export function Text({
  typography = "displayMedium12",
  color = "textDefault",
  ...props
}: Props) {
  return (
    <span
      css={{
        color: COLOR_VARIANTS[color],
        ...TYPOGRAPHY_VARIANTS[typography],
      }}
      {...props}
    />
  );
}

interface Props extends HTMLAttributes<HTMLSpanElement> {
  typography?: TypographyVariant;
  color?: Color;
}

const TYPOGRAPHY_VARIANTS = {
  displayBold24: Bold.L,
  displayBold16: Bold.M,
  displayBold14: Bold.R,
  displayBold12: Bold.S,
  displayMedium16: Medium.M,
  displayMedium14: Medium.R,
  displayMedium12: Medium.S,
  selectedBold16: Bold.M,
  selectedBold14: Bold.R,
  availableMedium16: Medium.M,
  availableMedium14: Medium.R,
};

type TypographyVariant = keyof typeof TYPOGRAPHY_VARIANTS;
