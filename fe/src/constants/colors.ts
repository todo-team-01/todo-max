const colors = {
  blue: "#007AFF",
  red: "#FF3B30",
  gray50: "#FEFEFE",
  gray100: "#F7F7FC",
  gray200: "#EFF0F6",
  gray300: "#D9DBE9",
  gray400: "#BEC1D5",
  gray500: "#A0A3BD",
  gray600: "#6E7191",
  gray700: "#4E4B66",
  gray800: "#2A2A44",
  gray900: "#14142B",
};

export const COLOR_VARIANTS = {
  textStrong: colors.gray900,
  textBold: colors.gray700,
  textDefault: colors.gray600,
  textWeak: colors.gray500,
  textWhiteDefault: colors.gray50,
  textWhiteWeak: colors.gray100,
  textBrand: colors.blue,
  textDanger: colors.red,
  surfaceDefault: colors.gray50,
  surfaceAlt: colors.gray100,
  surfaceBrand: colors.blue,
  surfaceDanger: colors.red,
  borderDefault: colors.gray200,
};

export type Color = keyof typeof COLOR_VARIANTS;
