import { Card } from "@components/types";
import { faker } from "@faker-js/faker";

export function createRandomCard(): Card {
  return {
    id: Number(faker.string.uuid()),
    name: faker.internet.userName(),
    title: faker.internet.domainName(),
    text: faker.word.words(),
    writer: faker.internet.userName(),
  };
}

export const CARDS: Card[] = faker.helpers.multiple(createRandomCard, {
  count: 100,
});
