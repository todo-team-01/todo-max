import { useEffect } from "react";

interface UseOutsideClickProps {
  ref: React.RefObject<HTMLElement>;
  callback: () => void;
}

export const useOutsideClick = ({ ref, callback }: UseOutsideClickProps) => {
  useEffect(() => {
    const listener = (e: MouseEvent) => {
      const targetNode = e.target as Node;

      if (!ref.current || ref.current.contains(targetNode)) {
        return;
      }

      callback();
    };

    document.addEventListener("mousedown", listener);

    return () => {
      document.removeEventListener("mousedown", listener);
    };
  }, [ref, callback]);
};
