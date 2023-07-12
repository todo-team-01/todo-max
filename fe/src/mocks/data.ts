import { CardData } from "@components/Card";
import { faker } from "@faker-js/faker";

export function createRandomCard(): CardData {
  return {
    id: Number(faker.string.uuid()),
    name: faker.internet.userName(),
    title: faker.internet.domainName(),
    text: faker.word.words(),
    writer: faker.internet.userName(),
  };
}

export const CARDS: CardData[] = faker.helpers.multiple(createRandomCard, {
  count: 100,
});
