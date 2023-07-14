import { colors } from "@constants/colors";
import { radius } from "@constants/objectStyle";
import { css } from "@emotion/react";
import { ButtonHTMLAttributes } from "react";

type ButtonVariant = "blue" | "red" | "gray" | "transparent";
type ButtonPattern = "text" | "icon";

interface ButtonProps extends ButtonHTMLAttributes<HTMLButtonElement> {
  variant?: ButtonVariant;
  pattern: ButtonPattern;
  iconHoverColor?: string;
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
  iconHoverColor?: string;
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
        fill: ${iconHoverColor};
      }
    }
    &:disabled {
      cursor: default;
      opacity: 0.3;
    }
    color: ${VARIANTS[variant].color};
    background: ${VARIANTS[variant].background};
    ${pattern === "text" && radius.radius8}
  `;
};

const VARIANTS = {
  blue: {
    color: colors.textWhiteDefault,
    background: colors.surfaceBrand,
  },
  red: {
    color: colors.textWhiteDefault,
    background: colors.red,
  },
  gray: {
    color: colors.textDefault,
    background: colors.surfaceAlt,
  },
  transparent: {
    color: colors.textDefault,
    background: "none",
  },
};
