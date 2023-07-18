import { css } from "@emotion/react";
import React, { useCallback, useRef, useState } from "react";
import { CardData, cardStyle } from "./Card";
import { CardViewer } from "./CardViewer";

export interface Position {
  x: number;
  y: number;
}

export const CloneCard = React.memo(
  ({
    cardData,
    initialPosition,
  }: {
    cardData: CardData;
    initialPosition: Position;
  }) => {
    const [position, setPosition] = useState<Position>(initialPosition);
    const positionRef = useRef(position);
    positionRef.current = position;

    const moveCard = useCallback(
      (e: React.MouseEvent) => {
        setPosition({ x: e.clientX, y: e.clientY });
      },
      [setPosition]
    );

    const handleMouseMove = (e: React.MouseEvent) => {
      window.requestAnimationFrame(() => moveCard(e));
    };

    const cloneCardStyle = css`
      position: fixed;
      left: ${positionRef.current.x - 150}px;
      top: ${positionRef.current.y - 44}px;
      background: white;
    `;

    return (
      <div css={[cardStyle, cloneCardStyle]} onMouseMove={handleMouseMove}>
        <CardViewer
          onClickEdit={() => {}}
          onClickRemove={() => {}}
          cardData={cardData}
        />
      </div>
    );
  }
);
