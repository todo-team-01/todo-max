import { CardData } from "@components/Card/Card";
import { faker } from "@faker-js/faker";
import { rest } from "msw";
import { CARDS } from "./data";

type CardAddRequestBody = {
  columnId: number;
  cardTitle: string;
  cardContent: string;
};

interface CardEditRequestBody {
  changedCardTitle: string;
  changedCardContent: string;
}

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
        content: "add, commit, push, rebase, merge",
        writer: "web",
      },
      {
        cardId: 20,
        title: "블로그에 포스팅할 것",
        content: "모던 자바스크립트 1장",
        writer: "web",
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
        content: "add, commit, push, rebase, merge",
        writer: "web",
      },
      {
        cardId: 30,
        title: "블로그에 포스팅할 것",
        content: "모던 자바스크립트 1장",
        writer: "web",
      },
    ],
  },
];

let historyData = [
  {
    historyId: 1,
    historyContent: `'블로그에 포스팅할 것'을(를) '하고있는 일'에서 '해야할 일'으로 '이동'하였습니다.`,
    timeStamp: "2023-07-17 10:00:00",
  },
  {
    historyId: 2,
    historyContent: `'블로그에 포스팅할 것'을(를) '하고있는 일'에서 '해야할 일'으로 '이동'하였습니다.`,
    timeStamp: "2023-07-16 10:00:00",
  },
  {
    historyId: 3,
    historyContent: `'블로그에 포스팅할 것'을(를) '하고있는 일'에서 '해야할 일'으로 '이동'하였습니다.`,
    timeStamp: "2023-07-15 10:00:00",
  },
];

export const handlers = [
  rest.get("/api", (_, res, ctx) => {
    return res(ctx.status(200), ctx.json(columnData));
  }),

  rest.get("/api/history", (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(historyData));
  }),

  rest.delete("/api/cards/:id", (req, res, ctx) => {
    const { id } = req.params;

    columnData = columnData.map((column) => ({
      ...column,
      cards: column.cards.filter((card) => card.cardId !== Number(id)),
    }));

    return res(ctx.status(200), ctx.json(columnData));
  }),

  rest.delete("/api/history", (req, res, ctx) => {
    historyData = [];

    return res(ctx.status(200), ctx.json(historyData));
  }),

  rest.delete("/api/columns/:id", (req, res, ctx) => {
    const { id } = req.params;

    columnData = columnData.filter((column) => column.columnId !== Number(id));

    return res(ctx.status(200), ctx.json(columnData));
  }),

  rest.put("/api/cards/:id", (req, res, ctx) => {
    const { id } = req.params;
    const { changedCardTitle, changedCardContent } =
      await req.json<CardEditRequestBody>();

    columnData = columnData.map((column) => ({
      ...column,
      cards: column.cards.map((card) => {
        if (card.cardId === Number(id)) {
          return {
            ...card,
            title: changedCardTitle,
            content: changedCardContent,
          };
        }
        return card;
      }),
    }));

    return res(ctx.status(200), ctx.json(columnData));
  }),

  rest.post("/api/cards", async (req, res, ctx) => {
    const { columnId, cardTitle, cardContent } =
      await req.json<CardAddRequestBody>();
    const newCard = {
      cardId: Number(faker.number.octal()),
      title: cardTitle,
      content: cardContent,
      writer: "web",
    };

    columnData = columnData.map((column) => {
      if (column.columnId === columnId) {
        return {
          ...column,
          cards: [newCard, ...column.cards],
        };
      }
      return column;
    });

    return res(ctx.status(200), ctx.json(newCard));
  }),

  rest.post("/api/columns", async (req, res, ctx) => {
    const { columnName } = await req.json<{ columnName: string }>();
    const newColumn = {
      columnId: columnData.length + 1,
      columnName,
      cards: [],
    };

    columnData.push(newColumn);
    return res(ctx.status(200), ctx.json(newColumn));
  }),
];
