import { Header } from "@components/Header";
import { Main } from "@components/Main";

export function App() {
  return (
    <div
      css={{
        width: "100vw",
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
      }}
    >
      <Header />
      <Main />
    </div>
  );
}
