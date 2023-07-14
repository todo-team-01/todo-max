import { css } from "@emotion/react";

const Bold = {
  L: css`
    font-size: 24px;
    line-height: auto;
    font-weight: 700;
  `,
  M: css`
    font-size: 16px;
    line-height: auto;
    font-weight: 700;
  `,
  R: css`
    font-size: 14px;
    line-height: auto;
    font-weight: 700;
  `,
  S: css`
    font-size: 12px;
    line-height: auto;
    font-weight: 700;
  `,
};

const Medium = {
  M: css`
    font-size: 16px;
    line-height: 22px;
    font-weight: 500;
  `,
  R: css`
    font-size: 14px;
    line-height: auto;
    font-weight: 500;
  `,
  S: css`
    font-size: 12px;
    line-height: auto;
    font-weight: 500;
  `,
};

export const typography = {
  display: {
    bold: {
      24: Bold.L,
      16: Bold.M,
      14: Bold.R,
      12: Bold.S,
    },
    medium: {
      16: Medium.M,
      14: Medium.R,
      12: Medium.S,
    },
  },
  selected: {
    bold: {
      16: Bold.M,
      14: Bold.R,
    },
  },
  available: {
    medium: {
      16: Medium.M,
      14: Medium.R,
    },
  },
};
