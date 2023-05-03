import details from 'd:/movie-database/details.json';

export default (id) => details.find((movie) => movie.id === id);