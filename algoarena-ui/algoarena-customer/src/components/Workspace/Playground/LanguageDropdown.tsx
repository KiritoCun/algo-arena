import React, { useState, useContext } from "react";
import { LanguageContext } from "../LanguageContext";

const LanguageDropdown = () => {
  const { language, setLanguage } = useContext(LanguageContext);
  const [dropdownOpen, setDropdownOpen] = useState(false);
  const [selectedLanguage, setSelectedLanguage] = useState(language ? language : "JavaScript");
  const languages = ["JavaScript", "Java", "PHP", "C#", "Python" , "Go"];

  const handleSelectLanguage = (language: string) => {
    setSelectedLanguage(language);
    setLanguage(language)
    setDropdownOpen(false);
  };

  return (
    <div className="flex items-center text-white">
      <div className="relative">
        {/* Button to toggle dropdown */}
        <button
          className="flex cursor-pointer items-center rounded focus:outline-none bg-dark-fill-3 text-dark-label-2 hover:bg-dark-fill-2 px-2 py-1.5 font-medium w-32"
          onClick={() => setDropdownOpen(!dropdownOpen)}
        >
          <div className="flex items-center px-1 justify-between w-full">
            <div className="text-xs text-label-2 dark:text-dark-label-2">
              {selectedLanguage}
            </div>
            <svg
              className={`w-4 h-4 transform transition-transform ${
                dropdownOpen ? "rotate-180" : "rotate-0"
              }`}
              fill="currentColor"
              viewBox="0 0 20 20"
            >
              <path
                fillRule="evenodd"
                d="M5.293 7.707a1 1 0 011.414 0L10 11.586l3.293-3.879a1 1 0 111.414 1.414l-4 4a1 1 0 01-1.414 0l-4-4a1 1 0 010-1.414z"
                clipRule="evenodd"
              />
            </svg>
          </div>
        </button>

        {/* Dropdown list */}
        {dropdownOpen && (
          <ul className="absolute left-0 mt-1 w-32 bg-white text-black rounded shadow-md z-10">
            {languages.map((lang, index) => (
              <li
                key={index}
                className="px-4 py-2 hover:bg-gray-200 cursor-pointer text-sm"
                onClick={() => handleSelectLanguage(lang)}
              >
                {lang}
              </li>
            ))}
          </ul>
        )}
      </div>
    </div>
  );
};

export default LanguageDropdown;
