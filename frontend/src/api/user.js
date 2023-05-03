import axios from 'axios';
import config from '../config/config.json';
import {
    clearApplicationData,
    loadAuthToken,
    loadUser
} from '../utils/localStorage';


export function login(username, password) {
    return axios.post(`${config.API_URL}/account/login`, {
        username,
        password
    });
}

export function register(username, email, password) {
    return axios.post(`${config.API_URL}/account/register`, {
        username,
        email,
        password
    });
}

export async function logout() {
    await axios.post(`${config.API_URL}/account/logout`, {}, {
        headers: {
            Authorization: `Bearer ${loadAuthToken()}`,
            "user-id": loadUser().userId
        }
    });
    clearApplicationData();
}

export function getUser(id) {
    return axios.get(`${config.API_URL}/user/${id}`, {
        headers: {
            Authorization: `Bearer ${loadAuthToken()}`
        }
    });
}