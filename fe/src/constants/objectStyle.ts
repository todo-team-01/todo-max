import { css } from "@emotion/react";

export const radius = {
  radius8: css`
    border-radius: 8px;
  `,
  radius16: css`
    border-radius: 16px;
  `,
  radius50: (height: number) => css`
    border-radius: ${height / 50}px;
  `,
};

export const dropShadow = {
  normal: css`
    box-shadow: 0px 1px 4px rgba(110, 128, 145, 0.24);
  `,
  up: css`
    box-shadow: 0px 2px 8px rgba(110, 128, 145, 0.32);
    backdrop-filter: blur(4px);
  `,
  floating: css`
    box-shadow: 0px 0px 4px rgba(110, 128, 145, 0.08),
      0px 16px 16px rgba(110, 128, 145, 0.24);
  `,
};
