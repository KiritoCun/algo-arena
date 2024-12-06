import { authModalState } from "@/atoms/authModalAtom";
import React, { useEffect } from "react";
import { IoClose } from "react-icons/io5";
import Login from "./Login";
import ResetPassword from "./ResetPassword";
import Signup from "./Signup";
import { useRecoilValue, useSetRecoilState } from "recoil";

type AuthModalProps = {};

const AuthModal: React.FC<AuthModalProps> = () => {
	const authModal = useRecoilValue(authModalState);
	const closeModal = useCloseModal();
	return (
		<>
			<div
				className='absolute top-0 left-0 w-full h-full flex items-center justify-center bg-opacity-60'
				onClick={closeModal}
			></div>
			<div className='w-full sm:w-[450px]  absolute top-[35%] left-[50%] translate-x-[-50%] translate-y-[-50%]  flex justify-center items-center'>
				<div className='relative w-full h-full mx-auto flex items-center justify-center'>
					<div className='bg-white rounded-lg shadow relative w-full bg-gradient-to-b from-brand-orange to-slate-900 mx-6'>
						<div className='flex justify-end p-2'>
							<button
								type='button'
								className='bg-transparent  rounded-lg text-sm p-1.5 ml-auto inline-flex items-center hover:bg-gray-800 hover:text-white text-white'
								onClick={closeModal}
							>
								<IoClose className='h-5 w-5' />
							</button>
						</div>
						{authModal.type === "login" ? <Login /> : authModal.type === "register" ? <Signup /> : <ResetPassword />}
					</div>
				</div>
			</div>
			<div className="contact-container" id="contact">
				<div className="contact-address">
					<img className="contact-logo" src="@/assets/logo/logo.png" alt="Logo" />
					<div className="contact-address-detail">
					<div className="main-info">
						<div className="title-text">Địa chỉ liên hệ</div>
						<div className="sub-title-text">ALGORITHM ARENA</div>
					</div>
					<div className="sub-info">
						<div className="sub-info-item">
						<svg-icon icon-class="location" />
						<div className="content">
							<div className="label-text">Địa chỉ</div>
							<div className="normal-text">
							Số 99 Đường Điện Biên Phủ, phường Hòa Khê, quận Thanh Khê, TP. Đà Nẵng, Việt Nam
							</div>
						</div>
						</div>
						<div className="sub-info-item">
						<svg-icon icon-class="phone" />
						<div className="content">
							<div className="label-text">Điện thoại</div>
							<div className="normal-text">(84-28) 9999 9999</div>
						</div>
						</div>
						<div className="sub-info-item">
						<svg-icon icon-class="mail" />
						<div className="content">
							<div className="label-text">Email</div>
							<div className="normal-text">info@algo-arena.com</div>
						</div>
						</div>
					</div>
					</div>
				</div>

				<div className="contact-others">
					<div className="policy">
					<div className="title-text">Chính sách khách hàng</div>
					<div className="term">
						<div className="term-column1">
						<div className="term-item">
							<div className="icon-background"><svg-icon icon-class="play" /></div>
							<div className="normal-text">Điều khoản dịch vụ</div>
						</div>
						<div className="term-item">
							<div className="icon-background"><svg-icon icon-class="play" /></div>
							<div className="normal-text">Chính sách đổi trả</div>
						</div>
						<div className="term-item">
							<div className="icon-background"><svg-icon icon-class="play" /></div>
							<div className="normal-text">Chính sách khách hàng</div>
						</div>
						</div>
						<div className="term-column2">
						<div className="term-item">
							<div className="icon-background"><svg-icon icon-class="play" /></div>
							<div className="normal-text">Chính sách bảo mật</div>
						</div>
						<div className="term-item">
							<div className="icon-background"><svg-icon icon-class="play" /></div>
							<div className="normal-text">Điều khoản thanh toán</div>
						</div>
						</div>
					</div>
					</div>

					<div className="support">
					<div className="title-text">Hỗ trợ</div>
					<div className="normal-text">
						Đăng ký sử dụng Algorithm Arena, trợ giúp hướng dẫn sử dụng, giải đáp thắc mắc vui lòng liên hệ hotline
					</div>
					<div className="support-info">
						<div className="support-info-item">
						<div className="normal-text">Hotline</div>
						<div className="hotline">
							<div className="hotline-background-icon">
							<svg-icon icon-class="phone" />
							</div>
							<div className="hotline-text">(84-28) 8342 9999</div>
						</div>
						</div>
					</div>
					</div>
				</div>
				</div>
		</>
	);
};
export default AuthModal;

function useCloseModal() {
	const setAuthModal = useSetRecoilState(authModalState);

	const closeModal = () => {
		setAuthModal((prev) => ({ ...prev, isOpen: false, type: "login" }));
	};

	useEffect(() => {
		const handleEsc = (e: KeyboardEvent) => {
			if (e.key === "Escape") closeModal();
		};
		window.addEventListener("keydown", handleEsc);
		return () => window.removeEventListener("keydown", handleEsc);
	}, []);

	return closeModal;
}

