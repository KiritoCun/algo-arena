import { authModalState } from "@/atoms/authModalAtom";
import { auth } from "@/firebase/firebase";
import { useRouter } from "next/router";
import React, { useEffect, useState, createContext, useContext } from "react";
import { useSignInWithEmailAndPassword } from "react-firebase-hooks/auth";
import { useSetRecoilState } from "recoil";
import { toast } from "react-toastify";
import axios from 'axios';
import { AuthContext } from './AuthContext';
type LoginProps = {};

const Login: React.FC<LoginProps> = () => {
	const { setUser } = useContext(AuthContext);

	const setAuthModalState = useSetRecoilState(authModalState);
	const handleClick = (type: "login" | "register" | "forgotPassword") => {
		setAuthModalState((prev) => ({ ...prev, type }));
	};
	const [inputs, setInputs] = useState({ username: "", password: "" });
	const [signInWithEmailAndPassword, user, loading, error] = useSignInWithEmailAndPassword(auth);
	const router = useRouter();
	const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
		setInputs((prev) => ({ ...prev, [e.target.name]: e.target.value }));
	};

	const login = async (username: string, password: string) => {
		try {
			const response = await axios.post('http://localhost/dev-api/customer/auth/login', {
			username,
			password,
			});
			const token = response.data.data.token;
		
			// Lưu token vào localStorage hoặc sessionStorage
			localStorage.setItem('authToken', token);

			// Tạo một instance axios với header mặc định
			const apiClient = axios.create({
				baseURL: 'http://localhost/dev-api/customer/system',
				headers: {
					Authorization: `Customer-Bearer ${token}`,
				},
			});

			const getUserInfo = async () => {
				try {
				  const response = await apiClient.get('/user/getInfo');
				  
				  if (response.data.code === 200) {
					return response.data.data.user
				  } else {
				  }
				} catch (error) {
				}
			  };
		
			// Trả về dữ liệu người dùng (nếu API trả về)
			const user = await getUserInfo()
			setUser(user)
			return user;
		} catch (error) {
			console.error('Login failed', error);
			throw error;
		}
	};

	const handleLogin = async (e: React.FormEvent<HTMLFormElement>) => {
		e.preventDefault();
		if (!inputs.username || !inputs.password) return alert("Please fill all fields");
		try {
			//const newUser = await signInWithEmailAndPassword(inputs.email, inputs.password);
			const user = await login(inputs.username, inputs.password)
			if (!user) return;
			router.push("/");
		} catch (error: any) {
			toast.error(error.message, { position: "top-center", autoClose: 3000, theme: "dark" });
		}
	};

	useEffect(() => {
		if (error) toast.error(error.message, { position: "top-center", autoClose: 3000, theme: "dark" });
	}, [error]);
	return (
		<form className='space-y-6 px-6 pb-4' onSubmit={handleLogin}>
			<h3 className='text-xl font-medium text-white'>Đăng nhập hệ thống Algorithm Arena</h3>
			<div>
				<label htmlFor='username' className='text-sm font-medium block mb-2 text-gray-300'>
					Tên đăng nhập
				</label>
				<input
					onChange={handleInputChange}
					type='text'
					name='username'
					id='username'
					className='
            border-2 outline-none sm:text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5
            bg-gray-600 border-gray-500 placeholder-gray-400 text-white
        '
					placeholder='Input your username'
				/>
			</div>
			<div>
				<label htmlFor='password' className='text-sm font-medium block mb-2 text-gray-300'>
					Mật khẩu
				</label>
				<input
					onChange={handleInputChange}
					type='password'
					name='password'
					id='password'
					className='
            border-2 outline-none sm:text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5
            bg-gray-600 border-gray-500 placeholder-gray-400 text-white
        '
					placeholder='*******'
				/>
			</div>

			<button
				type='submit'
				className='w-full text-white focus:ring-blue-300 font-medium rounded-lg
                text-sm px-5 py-2.5 text-center bg-brand-orange hover:bg-brand-orange-s
            '
			>
				{loading ? "Loading..." : "Đăng nhập"}
			</button>
			<button className='flex w-full justify-end' onClick={() => handleClick("forgotPassword")}>
				<a href='#' className='text-sm block text-brand-orange hover:underline w-full text-right'>
					Quên mật khẩu?
				</a>
			</button>
			<div className='text-sm font-medium text-gray-300'>
				Chưa có tài khoản?{" "}
				<a href='#' className='text-blue-700 hover:underline' onClick={() => handleClick("register")}>
					Đăng ký
				</a>
			</div>
		</form>
	);
};
export default Login;
