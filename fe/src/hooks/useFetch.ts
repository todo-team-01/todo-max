import { useEffect, useState } from "react";
import { http } from "utils/http";

interface UseFetchProps {
  url: string;
  method: "get" | "post" | "delete";
  body?: Request;
}

export const useFetch = ({ url, method, body }: UseFetchProps) => {
  const [response, setResponse] = useState([]);
  const [errorMsg, setErrorMsg] = useState("");
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const getData = async () => {
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
    };

    getData();
  }, [url, method, body]);

  return {
    response,
    errorMsg,
    loading,
  };
};
