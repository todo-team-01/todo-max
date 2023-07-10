import { colors } from "@constants/colors";
import { ButtonHTMLAttributes } from "react";

interface ButtonProps extends ButtonHTMLAttributes<HTMLButtonElement> {
  variant: "blue" | "red" | "gray";
  text?: string;
  onClick: () => void;
}

export const Button = ({ variant, text, onClick, children }: ButtonProps) => {
  return (
    <button
      css={{
        outline: "none",
        padding: "4px",
        display: "flex",
        justifyContent: "space-between",
        alignItems: "center",
        ...VARIANTS[variant],
      }}
      onClick={onClick}
    >
      {children}
      {text && (
        <div
          css={{
            padding: "4px",
            width: "45px",
            height: "17px",
          }}
        >
          {text}
        </div>
      )}
    </button>
  );
};

const VARIANTS = {
  blue: {
    color: colors.textWhiteDefault,
    background: colors.surfaceBrand,
  },
  red: {
    color: colors.textWhiteDefault,
    background: colors.surfaceDanger,
  },
  gray: {
    color: colors.textDefault,
    background: colors.surfaceAlt,
  },
};
