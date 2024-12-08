import React, { createContext, useState, useEffect } from 'react';
import axios from 'axios';

// Tạo Context
export const AuthContext = createContext({
  user: null,
  setUser: (user) => {},
  logout: () => {},
});

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);

  // Tạo instance Axios với header mặc định
  const apiClient = axios.create({
    baseURL: 'http://localhost:8082/customer/system',
    headers: {
      Authorization: typeof window !== 'undefined' && localStorage.getItem('authToken')
        ? `Customer-Bearer ${localStorage.getItem('authToken')}`
        : '', // Thêm kiểm tra nếu không có authToken
    },
  });

  const getUserInfo = async () => {
    try {
      const response = await apiClient.get('/user/getInfo');
      if (response.data.code === 200) {
        return response.data.data.user;
      }
    } catch (error) {
      console.error('API call failed:', error);
    }
    return null;
  };

  useEffect(() => {
    const fetchUser = async () => {
      const token = typeof window !== 'undefined' ? localStorage.getItem('authToken') : null;
      if (token) {
        const userInfo = await getUserInfo();
        if (userInfo) setUser(userInfo);
      }
    };
    fetchUser();
  }, []);

  const logout = () => {
    if (typeof window !== 'undefined') {
      localStorage.removeItem('authToken');
    }
    setUser(null);
  };

  return (
    <AuthContext.Provider value={{ user, setUser, logout }}>
      {children}
    </AuthContext.Provider>
  );
};
