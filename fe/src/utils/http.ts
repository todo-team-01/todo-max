export type CardAddRequestBody = {
  columnId: number;
  cardTitle: string;
  cardContent: string;
  nextCardId: number;
};

export type RequestMap = {
  "/api/cards": CardAddRequestBody;
  "/api": undefined;
};

export type RequestBody = RequestMap[keyof RequestMap];

export const http = {
  get: async (url: string) => {
    const response = await fetch(url);

    if (!response.ok) {
      throw new Error(response.statusText);
    }

    return response.json();
  },

  post: async (url: string, body?: object) => {
    const option = {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(body),
    };

    const response = await fetch(url, option);

    if (!response.ok) {
      throw new Error(response.statusText);
    }

    return response.json();
  },

  put: async (url: string, body?: object) => {
    const options = {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(body),
    };

    const response = await fetch(url, options);

    if (!response.ok) {
      throw new Error(response.statusText);
    }

    return response.json();
  },

  delete: async (url: string) => {
    const option = {
      method: "DELETE",
    };

    const response = await fetch(url, option);

    if (!response.ok) {
      throw new Error(response.statusText);
    }
  },
};
