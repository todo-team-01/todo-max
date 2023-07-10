import { Button } from "@components/base/Button";
import { colors } from "@constants/colors";
import { ClosedSVG } from "assets/closed";
import "./App.css";

export function App() {
  const testFun = () => {
    alert(1);
  };

  return (
    <div>
      <Button variant={"blue"} text={"Button"} onClick={testFun}>
        <ClosedSVG size={24} rgb={colors.textWhiteDefault} />
      </Button>
    </div>
  );
}
