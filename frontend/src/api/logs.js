import axios from 'axios';
import { API_URL } from '../config/config.json';

export async function getLogs() {
    const response = await axios.get(`${API_URL}/dev/log/all`);
    return response.data;
}

export async function clearLogs() {
    const response = await axios.delete(`${API_URL}/dev/log/clear`);
    return response.data;
}

