import axios from 'axios';
import {
  API_URL
} from '../config/config.json';

export async function getMovies(filter) {
  const response = await axios.get(`${API_URL}/movie/all?${filter ? Object.keys(filter).filter(key => !!filter[key]).map(key => `${key}=${filter[key]}`).join('&') : ''}`);
  return response.data;
}

export async function getMovie(id) {
  const response = await axios.get(`${API_URL}/movie/${id}`);
  return response.data;
}

export async function getGenres() {
  const response = await axios.get(`${API_URL}/movie/genres`);
  return response.data;
}

export async function getRandomMovie() {
  const response = await axios.get(`${API_URL}/movie/random`);
  return response.data;
}

export async function getPopularMovies(filter) {
  const response = await axios.get(`${API_URL}/movie/popular?${filter ? Object.keys(filter).map(key => `${key}=${filter[key]}`).join('&') : ''}`);
  return response.data;
}

export async function search(filter) {
  const response = await axios.get(`${API_URL}/movie/search?${filter ? Object.keys(filter).map(key => `${key}=${filter[key]}`).join('&') : ''}`);

  return response.data;
}

export async function getMovieCount(genreId) {
  const response = await axios.get(`${API_URL}/movie/count?genre_id=${genreId}`);
  return response.data;
}

export async function getMovieCountByQuery(query) {
  const response = await axios.get(`${API_URL}/movie/count/search?query=${query}`);
  return response.data;
}