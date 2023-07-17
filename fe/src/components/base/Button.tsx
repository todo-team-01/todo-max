import { COLOR_VARIANTS, Color } from "@constants/colors";
import { radius } from "@constants/objectStyle";
import { css } from "@emotion/react";
import { ButtonHTMLAttributes } from "react";

type ButtonVariant = "blue" | "red" | "gray" | "transparent";
type ButtonPattern = "text" | "icon";

interface ButtonProps extends ButtonHTMLAttributes<HTMLButtonElement> {
  variant?: ButtonVariant;
  pattern: ButtonPattern;
  iconHoverColor?: Color;
}

export const Button = ({
  variant = "transparent",
  pattern,
  iconHoverColor,
  children,
  ...props
}: ButtonProps) => {
  return (
    <button css={buttonStyle({ variant, pattern, iconHoverColor })} {...props}>
      {children}
    </button>
  );
};

const buttonStyle = ({
  pattern,
  variant = "transparent",
  iconHoverColor,
}: {
  variant?: ButtonVariant;
  pattern: ButtonPattern;
  iconHoverColor?: Color;
}) => {
  return css`
    width: ${pattern === "text" ? "132px" : "auto"};
    height: ${pattern === "text" ? "32px" : "auto"};
    outline: none;
    padding: 4px;
    display: flex;
    justify-content: center;
    align-items: center;
    &:hover {
      opacity: 0.8;
      & svg,
      path {
        fill: ${iconHoverColor && COLOR_VARIANTS[iconHoverColor]};
      }
    }
    &:disabled {
      cursor: default;
      opacity: 0.3;
    }
    color: ${BUTTON_VARIANTS[variant].color};
    background: ${BUTTON_VARIANTS[variant].background};
    ${pattern === "text" && radius.radius8}
  `;
};

const BUTTON_VARIANTS = {
  blue: {
    color: COLOR_VARIANTS.textWhiteDefault,
    background: COLOR_VARIANTS.surfaceBrand,
  },
  red: {
    color: COLOR_VARIANTS.textWhiteDefault,
    background: COLOR_VARIANTS.surfaceDanger,
  },
  gray: {
    color: COLOR_VARIANTS.textDefault,
    background: COLOR_VARIANTS.surfaceAlt,
  },
  transparent: {
    color: COLOR_VARIANTS.textDefault,
    background: "none",
  },
};
