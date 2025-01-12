import React, { createContext, useState, useEffect } from 'react';
import { getUserInfo } from "@/pages/api/api";

// Tạo Context
export const AuthContext = createContext({
  user: null,
  setUser: (user) => {},
  logout: () => {},
});

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);

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
