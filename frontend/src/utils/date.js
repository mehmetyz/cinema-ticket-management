const getHourAndMinute = (runtime) => {
  const hours = Math.floor(runtime / 60);
  const minutes = runtime % 60;

  return `${hours}h ${minutes}m`;
};

const getYear = (date) => {
  const year = date.split("-")[0];
  return year;
};


const convertDate = (date) => {
  return new Date(date);
}

export { getHourAndMinute, getYear , convertDate};
