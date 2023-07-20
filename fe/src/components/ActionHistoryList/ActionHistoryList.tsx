import { RemoveModal } from "@components/base/RemoveModal";
import { Text } from "@components/base/Text";
import { COLOR_VARIANTS } from "@constants/colors";
import { useFetch } from "hooks/useFetch";
import { useEffect, useState } from "react";
import { ClearArea } from "./ClearArea";
import ListArea from "./ListArea";
import { TitleArea } from "./TitleArea";

export interface HistoryData {
  historyId: number;
  historyContent: string;
  historyCreatedAt: string;
}

export const ActionHistoryList: React.FC<{ onClose: () => void }> = ({
  onClose,
}) => {
  const [isOpenModal, setIsOpenModal] = useState(false);
  const [rightPosition, setRightPosition] = useState(RIGHT_POSITION.CLOSE);

  const { response: activeHistoryList, fetch: getHistoryFetch } = useFetch({
    url: "/api/histories",
    method: "get",
    autoFetch: true,
  });
  const { fetch: deleteHistoryFetch } = useFetch({
    url: "/api/histories",
    method: "delete",
  });

  const hasActiveHistory = activeHistoryList && activeHistoryList.length > 0;

  const onOpen = () => setRightPosition(RIGHT_POSITION.OPEN);
  const onCloseButtonClick = () => setRightPosition(RIGHT_POSITION.CLOSE);
  const openModal = () => setIsOpenModal(true);
  const closeModal = () => setIsOpenModal(false);

  const onClickRemove = async () => {
    closeModal();
    await deleteHistoryFetch();
    await getHistoryFetch();
  };
  const onTransitionEnd = () => {
    rightPosition === RIGHT_POSITION.CLOSE && onClose();
  };

  useEffect(() => {
    onOpen();
  }, [activeHistoryList]);

  // TODO: CSS 분리 및 실행 순서 정리
  return (
    <div
      css={{
        width: "350px",
        position: "absolute",
        alignItems: "center",
        right: rightPosition,
        top: "100%",
        boxSizing: "border-box",
        transition: "right 1s",
        backgroundColor: COLOR_VARIANTS.surfaceDefault,
        boxShadow: "0px 2px 8px rgba(110, 128, 145, 0.32)",
        backdropFilter: "blur(4px)",
        borderRadius: "16px",
        padding: "4px",
      }}
      onTransitionEnd={onTransitionEnd}
    >
      <TitleArea handleClose={onCloseButtonClick} />
      {hasActiveHistory && (
        <div
          css={{
            display: "flex",
            flexDirection: "column",
            alignItems: "center",
          }}
        >
          <ListArea activeHistoryList={activeHistoryList} />
          <ClearArea onClearButtonClick={openModal} />
        </div>
      )}
      {!hasActiveHistory && (
        <Text typography="displayMedium14" css={emptyTextStyle}>
          사용자 활동 기록이 없습니다.
        </Text>
      )}
      <RemoveModal
        isOpen={isOpenModal}
        removeHandler={onClickRemove}
        closeHandler={closeModal}
        text={"모든 사용자 활동 기록을 삭제할까요?"}
      />
    </div>
  );
};

const RIGHT_POSITION = {
  OPEN: 0,
  CLOSE: -(350 + window.innerWidth / 20),
};

const emptyTextStyle = {
  color: COLOR_VARIANTS.textWeak,
  padding: "16px",
  display: "flex",
  justifyContent: "center",
  alignItems: "center",
};
