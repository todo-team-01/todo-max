import { useFetch } from "hooks/useFetch";
import { useOutsideClick } from "hooks/useOutsideClick";
import { useRef, useState } from "react";

interface NameEditorProps {
  columnId: number;
  columnName: string;
  onNameOutsideClick: () => void;
  onCardChanged: () => void;
}

export const NameEditor = ({
  columnId,
  columnName,
  onNameOutsideClick,
  onCardChanged,
}: NameEditorProps) => {
  const [updatedName, setUpdatedName] = useState(columnName);
  const inputRef = useRef<HTMLInputElement>(null);

  const { fetch: updateColumnFetch } = useFetch({
    url: `/api/columns/${columnId}`,
    method: "put",
  });

  const handleNameChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setUpdatedName(e.target.value);
  };

  const onUpdateName = async () => {
    onNameOutsideClick();

    if (columnName !== updatedName) {
      await updateColumnFetch({ changedColumnName: updatedName });
      onCardChanged();
    }
  };

  useOutsideClick({
    ref: inputRef,
    callback: onUpdateName,
  });

  return (
    <input
      ref={inputRef}
      value={updatedName}
      maxLength={50}
      onChange={handleNameChange}
    ></input>
  );
};
