import { useCallback, useEffect, useState } from "react";
import { http } from "utils/http";

interface UseFetchProps {
  url: string;
  method: "get" | "post" | "delete" | "put" | "patch";
  autoFetch?: boolean;
}

export const useFetch = ({
  url,
  method,
  autoFetch = false,
}: UseFetchProps): {
  response: any;
  errorMsg: string;
  loading: boolean;
  fetch: (body?: object) => Promise<void>;
} => {
  const [response, setResponse] = useState(null);
  const [errorMsg, setErrorMsg] = useState("");
  const [loading, setLoading] = useState(false);

  const fetch = useCallback(
    async (body?: object) => {
      setLoading(true);
      try {
        const res = await http[method](url, body);
        setResponse(res);
      } catch (error) {
        if (error instanceof Error) {
          console.error("HTTP Error: ", error.message);
          setErrorMsg(error.message);
        }
      } finally {
        setLoading(false);
      }
    },
    [url, method]
  );

  useEffect(() => {
    if (autoFetch) {
      fetch();
    }
  }, [url, method, autoFetch]);

  return { response, errorMsg, loading, fetch };
};
