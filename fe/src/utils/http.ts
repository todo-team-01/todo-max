export const http = {
  get: async (url: string) => {
    const response = await fetch(url);

    if (!response.ok) {
      throw new Error(response.statusText);
    }

    return response.json();
  },

  post: async (url: string, body?: Request) => {
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
