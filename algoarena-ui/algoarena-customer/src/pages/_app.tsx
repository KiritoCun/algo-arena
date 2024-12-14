import "@/styles/globals.css";
import type { AppProps } from "next/app";
import Head from "next/head";
import { RecoilRoot } from "recoil";
import { ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { AuthProvider } from '../components/Modals/AuthContext';
import { LanguageProvider } from '../components/Workspace/LanguageContext';
export default function App({ Component, pageProps }: AppProps) {
	return (
		<AuthProvider>
			<LanguageProvider>
				<RecoilRoot>
					<Head>
						<title>Algorithm Arena</title>
						<meta name='viewport' content='width=device-width, initial-scale=1' />
						<link rel='icon' href='/favicon.png' />
						<meta
							name='description'
							content='Web application that contains algorithm arena problems and video solutions'
						/>
					</Head>
					<ToastContainer />
					<Component {...pageProps} />
				</RecoilRoot>
			</LanguageProvider>
		</AuthProvider>
	);
}
