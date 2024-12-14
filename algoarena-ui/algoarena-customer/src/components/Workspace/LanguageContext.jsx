import React, { createContext, useContext, useState } from "react";

// Tạo context
export const LanguageContext = createContext({
    language: null,
    setLanguage: (language) => {},
  });

// Provider để cung cấp ngôn ngữ cho toàn ứng dụng
export const LanguageProvider = ({ children }) => {
    const [language, setLanguage] = useState("JavaScript");

    return (
        <LanguageContext.Provider value={{ language, setLanguage }}>
            {children}
        </LanguageContext.Provider>
    );
};

// Hook để dễ dàng sử dụng context
export const useLanguage = () => {
    return useContext(LanguageContext);
};
