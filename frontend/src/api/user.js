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
    console.log("logout", loadUser())
    await axios.post(`${config.API_URL}/account/logout`, {}, {
        headers: {
            Authorization: `Bearer ${loadAuthToken()}`,
            "user-id": loadUser().userId
        }
    });
    clearApplicationData();
}

export function getUser() {
    return axios.get(`${config.API_URL}/user/me`, {
        headers: {
            Authorization: `Bearer ${loadAuthToken()}`
        }
    });
}

export function getUsers() {
    return axios.get(`${config.API_URL}/user/all`, {
        headers: {
            Authorization: `Bearer ${loadAuthToken()}`
        }
    });
}

export function updateUser(userId, body) {
    return axios.put(`${config.API_URL}/user/${userId}/update`, body, {
        headers: {
            Authorization: `Bearer ${loadAuthToken()}`
        }
    });
}

export function deleteUser(userId) {
    return axios.delete(`${config.API_URL}/user/${userId}/delete`, {
        headers: {
            Authorization: `Bearer ${loadAuthToken()}`
        }
    });
}
