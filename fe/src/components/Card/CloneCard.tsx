import { css } from "@emotion/react";
import { memo, useCallback, useEffect, useRef } from "react";
import { CardData, cardStyle } from "./Card";
import { CardViewer } from "./CardViewer";

export interface Position {
  x: number;
  y: number;
}
export const CloneCard = memo(
  ({
    cardData,
    initialPosition,
  }: {
    cardData: CardData;
    initialPosition: Position;
  }) => {
    const cardRef = useRef<HTMLDivElement>(null);

    useEffect(() => {
      if (cardRef.current) {
        cardRef.current.style.left = `${initialPosition.x - 150}px`;
        cardRef.current.style.top = `${initialPosition.y - 44}px`;
      }
    }, [initialPosition]);

    const moveCard = useCallback((e: MouseEvent) => {
      if (cardRef.current) {
        cardRef.current.style.left = `${e.clientX - 150}px`;
        cardRef.current.style.top = `${e.clientY - 44}px`;
      }
    }, []);

    useEffect(() => {
      window.addEventListener("mousemove", moveCard);

      return () => {
        window.removeEventListener("mousemove", moveCard);
      };
    }, [moveCard]);

    const cloneCardStyle = css`
      position: fixed;
      background: white;
    `;

    return (
      <div ref={cardRef} css={[cardStyle, cloneCardStyle]}>
        <CardViewer
          onClickEdit={() => {}}
          onClickRemove={() => {}}
          cardData={cardData}
        />
      </div>
    );
  }
);
