import { rest } from "msw";

export const handlers = [
  rest.get("/", (req, res, ctx) => {
    const columnData = [
      {
        columnId: 1,
        columnName: "해야 할 일",
        cards: [
          {
            cardId: 1,
            title: "GitHub 공부하기",
            text: "add, commit, push, rebase, merge",
            writer: "jay",
          },
          {
            cardId: 2,
            title: "블로그에 포스팅할 것",
            text: "모던 자바스크립트 1장",
            writer: "jay",
          },
        ],
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
    return res(ctx.status(200), ctx.json(columnData));
  }),
];
