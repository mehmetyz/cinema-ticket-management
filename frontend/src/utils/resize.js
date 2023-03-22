const getMovieCount = () => {
  const width = window.innerWidth;
  return Math.floor(width / 208) * 3;
};

export default getMovieCount;
