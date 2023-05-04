import axios from 'axios';
import {
  API_URL
} from '../config/config.json';

export async function getMovies() {
  const response = await axios.get(`${API_URL}/movie/`);
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
