import { rest } from "msw";
import { CARDS } from "./data";
import { CardData } from "@components/Card";

interface Column {
  columnId: number;
  columnName: string;
  cards: CardData[];
}

let columnData: Column[] = [
  {
    columnId: 1,
    columnName: "해야 할 일",
    cards: CARDS,
  },
  {
    columnId: 2,
    columnName: "하고 있는 일",
    cards: [
      {
        cardId: 3,
        title: "HTML/CSS 공부하기",
        text: "add, commit, push, rebase, merge",
        writer: "jay",
      },
      {
        cardId: 20,
        title: "블로그에 포스팅할 것",
        text: "모던 자바스크립트 1장",
        writer: "jay",
      },
    ],
  },
  {
    columnId: 3,
    columnName: "완료한 일",
    cards: [
      {
        cardId: 40,
        title: "HTML/CSS 공부하기",
        text: "add, commit, push, rebase, merge",
        writer: "jay",
      },
      {
        cardId: 30,
        title: "블로그에 포스팅할 것",
        text: "모던 자바스크립트 1장",
        writer: "jay",
      },
    ],
  },
];

export const handlers = [
  rest.get("/", (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(columnData));
  }),

  rest.delete("/cards/:id", (req, res, ctx) => {
    const { id } = req.params;

    columnData = columnData.map((column) => ({
      ...column,
      cards: column.cards.filter((card) => card.cardId !== Number(id)),
    }));

    return res(ctx.status(200), ctx.json(columnData));
  }),
];
