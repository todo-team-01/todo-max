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

interface MoveCardBodyType {
  changedColumnId: number;
  TopCardId: number | null;
  BottomCardId: number | null;
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
        cardTitle: "HTML/CSS 공부하기",
        cardContent: "add, commit, push, rebase, merge",
        writer: "web",
      },
      {
        cardId: 20,
        cardTitle: "블로그에 포스팅할 것",
        cardContent: "모던 자바스크립트 1장",
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
        cardTitle: "HTML/CSS 공부하기",
        cardContent: "add, commit, push, rebase, merge",
        writer: "web",
      },
      {
        cardId: 30,
        cardTitle: "블로그에 포스팅할 것",
        cardContent: "모던 자바스크립트 1장",
        writer: "web",
      },
    ],
  },
  {
    columnId: 4,
    columnName: "하기 싫은 일",
    cards: [],
  },
];

let historyData = [
  {
    historyId: 1,
    historyContent: `'블로그에 포스팅할 것'을(를) '하고있는 일'에서 '해야할 일'으로 '이동'하였습니다.`,
    historyCreatedAt: "2023-07-18T20:45:23.019434",
  },
  {
    historyId: 2,
    historyContent: `'블로그에 포스팅할 것'을(를) '하고있는 일'에서 '해야할 일'으로 '이동'하였습니다.`,
    historyCreatedAt: "2023-07-17T20:45:23.019434",
  },
  {
    historyId: 3,
    historyContent: `'블로그에 포스팅할 것'을(를) '하고있는 일'에서 '해야할 일'으로 '이동'하였습니다.`,
    historyCreatedAt: "2023-07-16T20:45:23.019434",
  },
];

export const handlers = [
  rest.get("/api/columns", (_, res, ctx) => {
    return res(ctx.status(200), ctx.json(columnData));
  }),

  rest.get("/api/histories", (_, res, ctx) => {
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

  rest.delete("/api/histories", (_, res, ctx) => {
    historyData = [];

    return res(ctx.status(200), ctx.json(historyData));
  }),

  rest.delete("/api/columns/:id", (req, res, ctx) => {
    const { id } = req.params;

    columnData = columnData.filter((column) => column.columnId !== Number(id));

    return res(ctx.status(200), ctx.json(columnData));
  }),

  rest.put("/api/cards/:id", async (req, res, ctx) => {
    const { id } = req.params;
    const { changedCardTitle, changedCardContent } =
      await req.json<CardEditRequestBody>();

    columnData = columnData.map((column) => ({
      ...column,
      cards: column.cards.map((card) => {
        if (card.cardId === Number(id)) {
          return {
            ...card,
            cardTitle: changedCardTitle,
            cardContent: changedCardContent,
          };
        }
        return card;
      }),
    }));

    return res(ctx.status(200), ctx.json(columnData));
  }),

  rest.put("/api/columns/:id", (req, res, ctx) => {
    const { id } = req.params;
    const { changedColumnName } = req.body as { changedColumnName: string };

    columnData = columnData.map((column) => {
      if (column.columnId === Number(id)) {
        return {
          ...column,
          columnName: changedColumnName,
        };
      }
      return column;
    });

    return res(ctx.status(200), ctx.json(columnData));
  }),

  rest.post("/api/cards", async (req, res, ctx) => {
    const { columnId, cardTitle, cardContent } =
      await req.json<CardAddRequestBody>();
    const newCard = {
      cardId: Number(faker.number.octal()),
      cardTitle: cardTitle,
      cardContent: cardContent,
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

  rest.patch("/api/cards/:id", (req, res, ctx) => {
    const { id } = req.params;
    const { changedColumnId, TopCardId, BottomCardId } =
      req.body as MoveCardBodyType;
    console.log(req.body);

    let movingCard = null;
    let targetColumn = null;

    columnData.forEach((column) => {
      const cardIndex = column.cards.findIndex(
        (card) => card.cardId === Number(id)
      );
      if (cardIndex > -1) {
        movingCard = column.cards[cardIndex];
        column.cards.splice(cardIndex, 1);
      }
      if (column.columnId === changedColumnId) {
        targetColumn = column;
      }
    });

    if (targetColumn && movingCard) {
      targetColumn = targetColumn as Column;
      if (TopCardId === null) {
        targetColumn.cards.unshift(movingCard);
      } else if (BottomCardId === null) {
        targetColumn.cards.push(movingCard);
      } else {
        const topCardIndex = targetColumn.cards.findIndex(
          (card) => card.cardId === TopCardId
        );
        const bottomCardIndex = targetColumn.cards.findIndex(
          (card) => card.cardId === BottomCardId
        );
        if (topCardIndex < bottomCardIndex) {
          targetColumn.cards.splice(bottomCardIndex, 0, movingCard);
        } else {
          targetColumn.cards.splice(topCardIndex, 0, movingCard);
        }
      }
    }

    return res(ctx.status(200), ctx.json(columnData));
  }),
];
