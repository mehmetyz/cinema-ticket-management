import axios from 'axios';
import {
  API_URL
} from '../config/config.json';
import {
  loadAuthToken
} from '../utils/localStorage';

export async function getMovies(filter) {
  const response = await axios.get(`${API_URL}/movie/all?${filter ? Object.keys(filter).filter(key => !!filter[key]).map(key => `${key}=${filter[key]}`).join('&') : ''}`);


  for (let i = 0; i < response.data.length; i++) {
    const keywords = await axios.get(`${API_URL}/movie/${response.data[i].movieId}/keywords`);
    response.data[i].keywords = keywords.data;
  }

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

export async function getMovieCast(id) {
  const response = await axios.get(`${API_URL}/movie/${id}/cast`);
  return response.data;
}

export async function getMovieComments(id, filter) {
  const response = await axios.get(`${API_URL}/movie/${id}/comments?${filter ? Object.keys(filter).map(key => `${key}=${filter[key]}`).join('&') : ''}`);
  return response.data;
}

export async function createMovie(movie) {
  movie = {
    ...movie,
    keywords: movie.keywords.split(',').map(keyword => keyword.trim())
  }

  const response = await axios.post(`${API_URL}/movie/`, movie, {
    headers: {
      'Authorization': `Bearer ${loadAuthToken()}`
    }
  });
  return response.data;
}


export async function updateMovie(movie) {
  movie = {
    ...movie,
    keywords: movie.keywords.split(',').map(keyword => keyword.trim())
  }

  const response = await axios.put(`${API_URL}/movie/${movie.movieId}`, movie, {
    headers: {
      'Authorization': `Bearer ${loadAuthToken()}`
    }
  });
  return response.data;
}

export async function deleteMovie(movieId) {
  const response = await axios.delete(`${API_URL}/movie/${movieId}`, {
    headers: {
      'Authorization': `Bearer ${loadAuthToken()}`
    }
  });
  return response.data;
}