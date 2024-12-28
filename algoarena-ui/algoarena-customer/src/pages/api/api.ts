import axios from "axios";

const api = axios.create({
  baseURL: "https://your-api-url.com", // Thay bằng URL của API bạn
});

const getToken = () => {
    const token = typeof window !== 'undefined' ? localStorage.getItem('authToken') : null;
    return token;
}

// Tạo một instance axios với header mặc định
const apiClient = axios.create({
    baseURL: 'http://localhost:8082/customer',
    headers: {
        Authorization: `Customer-Bearer ${getToken()}`,
    },
});

export const fetchProblems = async () => {
    try {
        const response = await apiClient.get('/homepage/search/problem');
        
        if (response.data.code === 200) {
        return response.data.rows
        } else {
        }
    } catch (error) {
    }
};

export const fetchTestcases = async (keyPath: string) => {
    try {
        const response = await apiClient.get('/homepage/search/testcase/' + keyPath);
        if (response.status === 200) {
        return response.data
        } else {
        }
    } catch (error) {
    }
};

export const fetchProblemFunctionSignatures = async (keyPath: string) => {
    try {
        const response = await apiClient.get('/homepage/search/problem-function-signature/' + keyPath);
        if (response.status === 200) {
        return response.data
        } else {
        }
    } catch (error) {
    }
};

// Hàm lấy danh sách bài toán đã giải
export const fetchSolvedProblems = async (userId: string) => {
  const response = await api.get(`/users/${userId}/solved-problems`);
  return response.data;
};