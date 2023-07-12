import { CardData } from "@components/Card";
import { faker } from "@faker-js/faker";

export function createRandomCard(cardId: number): CardData {
  return {
    cardId,
    title: faker.internet.domainName(),
    text: faker.word.words(),
    writer: faker.internet.userName(),
  };
}

export const CARDS: CardData[] = Array.from({ length: 100 }, (_, index) =>
  createRandomCard(index + 100)
);
