const textSearch = (str, search, isCaseSensitive = false) => {
  if (!str || !search) {
    return false;
  }

  if (!isCaseSensitive) {
    str = str.toLowerCase();
    search = search.toLowerCase();
  }

  return str.includes(search);
};


export { textSearch };
