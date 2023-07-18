import { CardData } from "@components/Card/Card";
import { faker } from "@faker-js/faker";

export function createRandomCard(cardId: number): CardData {
  return {
    cardId,
    title: faker.internet.domainName(),
    content: faker.word.words(),
    writer: "web",
  };
}

export const CARDS: CardData[] = Array.from({ length: 100 }, (_, index) =>
  createRandomCard(index + 100)
);
