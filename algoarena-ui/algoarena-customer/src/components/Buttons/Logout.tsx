import { auth } from "@/firebase/firebase";
import React, { useState, useContext } from "react";
import { useSignOut } from "react-firebase-hooks/auth";
import { FiLogOut } from "react-icons/fi";
import { AuthContext } from '../Modals/AuthContext';

const Logout: React.FC = () => {
	const { user, logout } = useContext(AuthContext);
  const [signOut, loading, error] = useSignOut(auth);

  const handleLogout = () => {
    signOut(); // Sử dụng hook signOut để đăng xuất
    localStorage.removeItem('authToken');
    logout(); // Reset user state
  };

  return (
    <button 
      className='bg-dark-fill-3 py-1.5 px-3 cursor-pointer rounded text-brand-orange' 
      onClick={handleLogout}
    >
      <FiLogOut />
    </button>
  );
};

export default Logout;
