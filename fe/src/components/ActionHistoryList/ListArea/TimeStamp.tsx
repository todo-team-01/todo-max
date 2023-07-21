import { Text } from "@components/base/Text";
import { COLOR_VARIANTS } from "@constants/colors";

export const TimeStamp: React.FC<{ historyCreatedAt: string }> = ({
  historyCreatedAt,
}) => {
  const calculateTimeDiff = (historyCreatedAt: string) => {
    const now = new Date();
    const generatedAtDate = new Date(historyCreatedAt);

    const diff = now.getTime() - generatedAtDate.getTime();
    const diffInMinutes = Math.floor(diff / 1000 / 60);
    const diffInHours = Math.floor(diffInMinutes / 60);
    const diffInDays = Math.floor(diffInHours / 24);

    if (diffInDays > 0) {
      return `${diffInDays}일 전`;
    }
    if (diffInHours > 0) {
      return `${diffInHours}시간 전`;
    }
    return diffInMinutes === 0 ? "방금" : `${diffInMinutes}분 전`;
  };

  return (
    <Text typography="displayMedium12" css={{ color: COLOR_VARIANTS.textWeak }}>
      {calculateTimeDiff(historyCreatedAt)}
    </Text>
  );
};
