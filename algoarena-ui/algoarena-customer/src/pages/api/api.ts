import axios from "axios";

const baseURL = "http://localhost:8082/customer"

const api = axios.create({
  baseURL: baseURL,
});

export const customerLogin = async (username: string, password: string) => {
    try {
        const response = await api.post('/auth/login',{
            username,
            password,
        });
        
        return response;
    } catch (error) {
    }
};

const getToken = () => {
    const token = typeof window !== 'undefined' ? localStorage.getItem('authToken') : null;
    return token;
}

// Tạo một instance axios với header mặc định
const apiClient = axios.create({
    baseURL: baseURL,
    headers: {
        Authorization: `Customer-Bearer ${getToken()}`,
    },
});

export const getUserInfo = async (token: string) => {
    const apiClient2 = axios.create({
        baseURL: baseURL,
        headers: {
            Authorization: `Customer-Bearer ${token}`,
        },
    });
    try {
        const response = await apiClient2.get('/system/user/getInfo');
        
        if (response.data.code === 200) {
          return response.data.data.user
        } else {
        }
    } catch (error) {
    }
};

export const fetchProblems = async (userId: number) => {
    try {
        const response = await apiClient.get('/homepage/search/problem?'+'userId='+userId);
        
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

export const fetchProblemFunctionSignatures = async (keyPath: string, userId: number) => {
    try {
        const response = await apiClient.get('/homepage/search/problem-function-signature/' + keyPath + "/" + userId);
        if (response.status === 200) {
        return response.data
        } else {
        }
    } catch (error) {
    }
};

// Hàm fetch GPT response (mới)
export const fetchGptResponse = async (userInput: string) => {
    try {
        const response = await apiClient.post("/homepage/search/get-gpt-response", {
            userInput,
        });
        if (response.status === 200) {
            return response.data; // Trả về dữ liệu từ response
        } else {
            console.error("Failed to fetch GPT response:", response.status);
        }
    } catch (error) {
        console.error("Error fetching GPT response:", error);
    }
};

// Hàm gọi API run-solution
export const runSolution = async (submittedCode: string, problemId: string, language: string, version: string) => {
    try {
        const response = await apiClient.post("/homepage/search/run-solution", {
            submittedCode,
            problemId,
            language,
            version,
        });

        if (response.status === 200) {
            return response.data; // Trả về dữ liệu từ API
        } else {
            console.error("Failed to run solution:", response.status);
        }
    } catch (error) {
        console.error("Error running solution:", error);
    }
};
  
// Hàm gọi API submit-solution
export const submitSolution = async (
    submittedCode: string,
    problemId: string,
    language: string,
    version: string,
    userId: string
) => {
    try {
        const response = await apiClient.post("/homepage/search/submit-solution", {
            submittedCode,
            problemId,
            language,
            version,
            userId,
        });
  
        if (response.status === 200) {
            return response.data; // Trả về dữ liệu từ API
        } else {
            console.error("Failed to submit solution:", response.status);
        }
    } catch (error) {
        console.error("Error submitting solution:", error);
    }
  };