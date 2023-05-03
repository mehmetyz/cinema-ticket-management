import { useState, useEffect } from "react";
import { useLocation } from "react-router-dom";

const useQueryParams = () => {
  const { search } = useLocation();

  const [queryParams, setQueryParams] = useState({});
  useEffect(() => {
    const params = new URLSearchParams(search);
    const queryParams = {};
    for (let param of params) {
      queryParams[param[0]] = param[1];
    }
    setQueryParams(queryParams);
  }, [search]);

  return queryParams;
};

export { useQueryParams };
