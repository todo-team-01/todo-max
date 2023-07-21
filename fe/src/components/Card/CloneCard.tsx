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

    const moveCard = useCallback((e: MouseEvent) => {
      editPoint({ x: e.clientX, y: e.clientY });
    }, []);

    const editPoint = (point: Position) => {
      if (cardRef.current) {
        cardRef.current.style.left = `${point.x - 150}px`;
        cardRef.current.style.top = `${point.y - 44}px`;
      }
    };

    useEffect(() => {
      editPoint(initialPosition);
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
        <CardViewer cardData={cardData} />
      </div>
    );
  }
);
