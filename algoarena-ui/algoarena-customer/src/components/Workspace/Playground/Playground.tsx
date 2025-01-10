import { useState, useEffect, useContext } from "react";
import PreferenceNav from "./PreferenceNav/PreferenceNav";
import Split from "react-split";
import CodeMirror from "@uiw/react-codemirror";
import { vscodeDark } from "@uiw/codemirror-theme-vscode";
import { javascript } from "@codemirror/lang-javascript";
import EditorFooter from "./EditorFooter";
import { Problem } from "@/utils/types/problem";
import { useAuthState } from "react-firebase-hooks/auth";
import { auth, firestore } from "@/firebase/firebase";
import { toast } from "react-toastify";
import { problems } from "@/utils/problems";
import { useRouter } from "next/router";
import { arrayUnion, doc, updateDoc } from "firebase/firestore";
import useLocalStorage from "@/hooks/useLocalStorage";
import { AuthContext } from '../../Modals/AuthContext';
import { LanguageContext } from "../LanguageContext";
import { LANGUAGE_CONFIG } from "@/constants";
import { fetchTestcases, fetchProblemFunctionSignatures, getNewestPassedSubmission } from "@/pages/api/api";

type PlaygroundProps = {
	problem: Problem;
	setSuccess: React.Dispatch<React.SetStateAction<boolean>>;
	setSolved: React.Dispatch<React.SetStateAction<boolean>>;
};

export interface ISettings {
	fontSize: string;
	settingsModalIsOpen: boolean;
	dropdownIsOpen: boolean;
}

const Playground: React.FC<PlaygroundProps> = ({ problem, setSuccess, setSolved }) => {
	const { language, setLanguage  } = useContext(LanguageContext);
	// Biến state để lưu trữ giá trị lọc
    const [testcases, setStateTestcases] = useState<Object[] | []>([]);
	const [resultTestcases, setStateResultTestcases] = useState<Object[] | []>([]);
	const [functionSignatures, setFunctionSignatures] = useState([]);
	const [newestPassedSubmission, setNewestPassedSubmission] = useState<Object>();
	const [errorMessage, setErrorMessage] = useState('You must run your code first');

	// Hàm gọi API để cập nhật problem
	useEffect(() => {
		setActiveTestCaseId(0)
		const setTestcases = async () => {
			const res = await fetchTestcases(problem.id);
			// Xử lý danh sách
			const tcs = res
			.filter(testcase => testcase.isHidden === 0) // Lọc các phần tử có isHidden = 0
			.map((testcase, index) => {
				const testcaseJson = testcase.testcaseJson; // Lấy chuỗi JSON gốc
	
				// Tìm giá trị của `expect` bằng regex
				const expectMatch = testcaseJson.match(/"expect":\s*(\[.*?\]|true|false|".*?"|\d+)/);
				const outputText = expectMatch ? expectMatch[1] : ""; // Giá trị outputText là chuỗi `expect`
	
				// Loại bỏ `expect` ra khỏi chuỗi JSON
				const inputPart = testcaseJson.replace(/"expect":\s*(\[.*?\]|true|false|".*?"|\d+),?\s*/, "").trim();
				const inputTextMatch = inputPart.match(/{(.*)}/); // Lấy phần input bên trong {}
	
				// Chuyển phần input thành chuỗi inputText
				const inputText = inputTextMatch
					? inputTextMatch[1]
						  .trim()
						  .replace(/"/g, "") // Loại bỏ dấu ngoặc kép
						  .replace(/:/g, " = ") // Đổi dấu ':' thành ' = '
						  .replace(/,\s*$/, "") // Xóa dấu phẩy thừa ở cuối
						  .replace(/,/g, ", ") // Thêm khoảng trắng sau dấu ','
					: "";
	
				return {
					id: index + 1, // Thêm trường id (bắt đầu từ 1)
					inputText,
					outputText // Chuỗi `expect`
				};
			});

			setStateTestcases(tcs)
		};
		setTestcases();
	}, [problem.id]); // Chạy lại khi problem.id thay đổi

	// Hàm khởi tạo functionSignatures từ API
	const initializeFunctionSignatures = async (problemId: string) => {
		const userId = (user && user.userId) ? user.userId : 0;
		const fetchedSignatures = await fetchProblemFunctionSignatures(problemId);
		setFunctionSignatures(fetchedSignatures);
	  };

	  const passedSubmission = async (problemId: string, userId: string) => {
		const newestPassedSubmission = await getNewestPassedSubmission(problemId, userId);
		setNewestPassedSubmission(newestPassedSubmission);
	  };
	
	  // Hàm generateCodeSample đã sửa lại cho phù hợp với async
	  const generateCodeSample = (language: string, keyPath: string) => {
		// Kiểm tra nếu chưa có functionSignatures, tránh lỗi
		if (functionSignatures.length === 0) {
		  return '// Function signatures not loaded yet';
		}
	
		const signatureEntry = functionSignatures.find(
		  (entry) => entry.keyPath === keyPath && entry.language === language
		);
	
		if (!signatureEntry) {
		  if (!functionSignatures.some((entry) => entry.keyPath === keyPath)) {
			return `// Problem "${keyPath}" not found.`;
		  }
		  return `// Language "${language}" not supported for problem "${keyPath}".`;
		}
	
		const functionSignature = signatureEntry.functionSignature;
		const codeSampleTemplate = codeSamples[language];
	
		if (!codeSampleTemplate) {
		  return `// Language "${language}" does not have a code sample template.`;
		}
	
		return codeSampleTemplate.replace('ARGUMENTS', functionSignature);
	  };
	
	  // useEffect để tạo userCode khi functionSignatures đã được tải
	  useEffect(() => {
		initializeFunctionSignatures(problem.id);
		// if (user != null && user != undefined && user.userId) {
		// 	console.log(user)
		// 	passedSubmission(problem.id, user.userId);
		// }
		// console.log(newestPassedSubmission)
		// if (newestPassedSubmission) {
		// 	setUserCode(newestPassedSubmission.code);

		// 	const newestSubmissionLanguage = newestPassedSubmission.programingLanguage;

		// 	if (newestSubmissionLanguage == "java") {
		// 		setLanguage("Java");
		// 	} else if (newestSubmissionLanguage == "javascript") {
		// 		setLanguage("JavaScript");
		// 	} else if (newestSubmissionLanguage == "python") {
		// 		setLanguage("Python");
		// 	} else if (newestSubmissionLanguage == "c#") {
		// 		setLanguage("C#");
		// 	} else if (newestSubmissionLanguage == "go") {
		// 		setLanguage("Go");
		// 	} else if (newestSubmissionLanguage == "php") {
		// 		setLanguage("PHP");
		// 	}
			
		// 	return;
		// }
		if (functionSignatures.length > 0) {
		  setUserCode(generateCodeSample(language, problem.id, problem.id));
		}
	  }, [problem.id]); // Chạy khi problemId thay đổi

	  useEffect(() => {
		if (functionSignatures.length > 0) {
		  setUserCode(generateCodeSample(language, problem.id, problem.id));
		}
	  }, [functionSignatures, language]); // Chạy khi functionSignatures hoặc language thay đổi

	const [activeTestCaseId, setActiveTestCaseId] = useState<number>(0);
	let [userCode, setUserCode] = useState<string>(problem.starterCode);

	const [fontSize, setFontSize] = useLocalStorage("lcc-fontSize", "16px");

	const [settings, setSettings] = useState<ISettings>({
		fontSize: fontSize,
		settingsModalIsOpen: false,
		dropdownIsOpen: false,
	});

	const [activeTab, setActiveTab] = useState<"Testcases" | "Test Result">(
		"Testcases"
	  );

	const languageVersions: object[] = [
		{ language: 'Java', version: '15.0.2' },           
		{ language: 'Python', version: '3.10.0' },     
		{ language: 'JavaScript', version: '18.15.0' }, 
		{ language: 'C#', version: '5.0.201' },           
		{ language: 'C++', version: '10.2.0' },            
		{ language: 'Ruby', version: '3.0.1' },        
		{ language: 'Go', version: '1.16.2' },         
		{ language: 'Rust', version: '1.68.2' },       
		{ language: 'C', version: '10.2.0' },              
		{ language: 'PHP', version: '8.2.3' },         
	  ];

	const codeSamples = {
		JavaScript: `ARGUMENTS {
    
};`,
		Java: `class Solution {
	ARGUMENTS {
		
	}
}`,
		PHP: `class Solution {
	ARGUMENTS {
		
	}
}`,
		C: `ARGUMENTS {
	
}`,
		'C++': `class Solution {
public:
	ARGUMENTS {
		
	}
};`,
		'C#': `public class Solution {
	ARGUMENTS {
		
	}
}`,
		'Python': `class Solution:
	ARGUMENTS
	`,
		'Go': `ARGUMENTS {
	
}`,
	};

	const { user } = useContext(AuthContext);
	const {
		query: { pid },
	} = useRouter();

	const handleRun = async () => {
		if (!user) {
			toast.error("Please login to run your code", {
				position: "top-center",
				autoClose: 3000,
				theme: "dark",
			});
			return;
		}
		try {
			console.log(userCode)
			toast.loading("Running solution", { position: "top-center", toastId: "loadingToast" });
			const configCompile = languageVersions.find(
				(item) => item.language.toLowerCase() === language.toLowerCase()
			);
			const response = await fetch("http://localhost:8082/customer/homepage/search/run-solution", {
			method: "POST",
			headers: {
				"Content-Type": "application/json",
			},
			body: JSON.stringify({
				submittedCode: userCode,
				problemId: pid,
				language: language.toLowerCase(),
				version: configCompile.version
			}),
			});
		
			const data = await response.json();
		
			console.log("data back from piston:", data);

			if (data.length === 1) {
				toast.dismiss("loadingToast");
				setStateResultTestcases([])
				setErrorMessage(`${data[0]}`)
				setActiveTab("Test Result")
			} else if (Array.isArray(data) && data.every(result => result === "true")) {
				toast.dismiss("loadingToast");
				const resultTestcases = testcases.map((testcase, index) => ({
					...testcase, // Sao chép toàn bộ thuộc tính từ testcase
					success: data[index], // Thêm thuộc tính success từ mảng res
				}));
				setStateResultTestcases(resultTestcases)
				setActiveTab("Test Result")
			} else {
				toast.dismiss("loadingToast");
				const resultTestcases = testcases.map((testcase, index) => ({
					...testcase, // Sao chép toàn bộ thuộc tính từ testcase
					success: data[index], // Thêm thuộc tính success từ mảng res
				}));
				setStateResultTestcases(resultTestcases)
				setActiveTab("Test Result")
			}

		} catch (error: any) {
			console.log(error);
			console.log(error.message);
			if (
				error.message.startsWith("AssertionError [ERR_ASSERTION]: Expected values to be strictly deep-equal:")
			) {
				toast.dismiss("loadingToast");
				toast.error("Oops! One or more test cases failed", {
					position: "top-center",
					autoClose: 3000,
					theme: "dark",
				});
			}
		}
	};

	const handleSubmit = async () => {
		if (!user) {
			toast.error("Please login to submit your code", {
				position: "top-center",
				autoClose: 3000,
				theme: "dark",
			});
			return;
		}
		try {
			toast.loading("Checking solution", { position: "top-center", toastId: "loadingToast" });
			const configCompile = languageVersions.find(
				(item) => item.language.toLowerCase() === language.toLowerCase()
			);
			const response = await fetch("http://localhost:8082/customer/homepage/search/submit-solution", {
			method: "POST",
			headers: {
				"Content-Type": "application/json",
			},
			body: JSON.stringify({
				submittedCode: userCode,
				problemId: pid,
				language: language.toLowerCase(),
				version: configCompile.version,
				userId: user.userId
			}),
			});
		
			const data = await response.json();
		
			console.log("data back from piston:", data);

			if (data.length === 1) {
				toast.dismiss("loadingToast");
				toast.error(`Error: ${data[0]}`, {
					position: "top-center",
					autoClose: 3000,
					theme: "dark",
				});
			} else if (Array.isArray(data) && data.every(result => result === "true")) {
				toast.dismiss("loadingToast");
				toast.success("Congrats! All tests passed!", {
					position: "top-center",
					autoClose: 3000,
					theme: "dark",
				});
				setSuccess(true);
				setTimeout(() => {
					setSuccess(false);
				}, 4000);

				const userRef = doc(firestore, "users", user.uid);
				await updateDoc(userRef, {
					solvedProblems: arrayUnion(pid),
				});
				setSolved(true);
			} else {
				toast.dismiss("loadingToast");
				toast.error("Oops! One or more test cases failed", {
					position: "top-center",
					autoClose: 3000,
					theme: "dark",
				});
			}

		} catch (error: any) {
			console.log(error);
			console.log(error.message);
			if (
				error.message.startsWith("AssertionError [ERR_ASSERTION]: Expected values to be strictly deep-equal:")
			) {
				toast.dismiss("loadingToast");
				toast.error("Oops! One or more test cases failed", {
					position: "top-center",
					autoClose: 3000,
					theme: "dark",
				});
			}
		}
	};

	useEffect(() => {
		const code = localStorage.getItem(`code-${pid}`);
		if (user) {
			setUserCode(code ? JSON.parse(code) : problem.starterCode);
		} else {
			setUserCode(problem.starterCode);
		}
	}, [pid, user, problem.starterCode]);

	useEffect(() => {
	// Update userCode when language changes
	setUserCode(generateCodeSample(language, pid as string, pid as string));
	}, [language, pid]);

	const onChange = (value: string) => {
		setUserCode(value);
		localStorage.setItem(`code-${pid}`, JSON.stringify(value));
	};

	return (
		<div className='flex flex-col bg-dark-layer-1 relative overflow-x-hidden'>
			<PreferenceNav settings={settings} setSettings={setSettings} />

			<Split className='h-[calc(100vh-94px)]' direction='vertical' sizes={[60, 40]} minSize={60}>
				<div className='w-full overflow-auto'>
					<CodeMirror
						value={userCode}
						theme={vscodeDark}
						onChange={onChange}
						extensions={[javascript()]}
						style={{ fontSize: settings.fontSize }}
					/>
				</div>
				<div className="w-full px-5 overflow-auto">
					{/* Tabs Heading */}
					<div className="flex h-10 items-center space-x-6">
						<div
						className={`relative flex h-full flex-col justify-center cursor-pointer ${
							activeTab === "Testcases" ? "" : "text-gray-500"
						}`}
						onClick={() => setActiveTab("Testcases")}
						>
						<div className="text-sm font-medium leading-5 text-white">
							Testcases
						</div>
						{activeTab === "Testcases" && (
							<hr className="absolute bottom-0 h-0.5 w-full rounded-full border-none bg-white" />
						)}
						</div>
						<div
						className={`relative flex h-full flex-col justify-center cursor-pointer ${
							activeTab === "Test Result" ? "" : "text-gray-500"
						}`}
						onClick={() => setActiveTab("Test Result")}
						>
						<div className="text-sm font-medium leading-5 text-white">
							Test Results
						</div>
						{activeTab === "Test Result" && (
							<hr className="absolute bottom-0 h-0.5 w-full rounded-full border-none bg-white" />
						)}
						</div>
					</div>

					{/* Nội dung của tab */}
					{activeTab === "Testcases" && (
						<div>
						<div className="flex">
							{testcases.map((example, index) => (
							<div
								className="mr-2 items-start mt-2"
								key={example.id}
								onClick={() => setActiveTestCaseId(index)}
							>
								<div className="flex flex-wrap items-center gap-y-4">
								<div
									className={`font-medium items-center transition-all focus:outline-none inline-flex bg-dark-fill-3 hover:bg-dark-fill-2 relative rounded-lg px-4 py-1 cursor-pointer whitespace-nowrap ${
									activeTestCaseId === index
										? "text-white"
										: "text-gray-500"
									}`}
								>
									Case {index + 1}
								</div>
								</div>
							</div>
							))}
						</div>

						<div className="font-semibold my-4">
							<p className="text-sm font-medium mt-4 text-white">Input:</p>
							<div className="w-full cursor-text rounded-lg border px-3 py-[10px] bg-dark-fill-3 border-transparent text-white mt-2">
							{testcases[activeTestCaseId]?.inputText}
							</div>
							<p className="text-sm font-medium mt-4 text-white">Output:</p>
							<div className="w-full cursor-text rounded-lg border px-3 py-[10px] bg-dark-fill-3 border-transparent text-white mt-2">
							{testcases[activeTestCaseId]?.outputText}
							</div>
						</div>
						</div>
					)}

					{activeTab === "Test Result" && (
						<div>
						<div className="flex">
							{resultTestcases.map((example, index) => (
							<div
								className="mr-2 items-start mt-2"
								key={example.id}
								onClick={() => setActiveTestCaseId(index)}
							>
								<div className="flex items-center gap-x-2">
								{activeTab === "Test Result" && (
									<span
									className={`h-3 w-3 rounded-full ${
										example.success === "true" || example.success === "True"
										? "bg-green-500"
										: "bg-red-500"
									}`}
									></span>
								)}
								<div
									className={`font-medium items-center transition-all focus:outline-none inline-flex bg-dark-fill-3 hover:bg-dark-fill-2 relative rounded-lg px-4 py-1 cursor-pointer whitespace-nowrap ${
									activeTestCaseId === index
										? "text-white"
										: "text-gray-500"
									}`}
								>
									Case {index + 1}
								</div>
								</div>
							</div>
							))}
						</div>
						{resultTestcases.length != 0 && (
							<div className="font-semibold my-4">
								<p className="text-sm font-medium mt-4 text-white">Input:</p>
								<div className="w-full cursor-text rounded-lg border px-3 py-[10px] bg-dark-fill-3 border-transparent text-white mt-2">
								{resultTestcases[activeTestCaseId]?.inputText}
								</div>
								<p className="text-sm font-medium mt-4 text-white">Output:</p>
								<div className="w-full cursor-text rounded-lg border px-3 py-[10px] bg-dark-fill-3 border-transparent text-white mt-2">
								{resultTestcases[activeTestCaseId]?.outputText}
								</div>
							</div>
						)}
						{(resultTestcases.length == 0) && (
							<p
							className={`text-sm font-medium mt-4 ${
							  errorMessage === 'You must run your code first' ? 'text-white' : 'text-red-500'
							}`}
						  >
							{errorMessage}
						  </p>
						)}
						</div>
					)}
					</div>
			</Split>
			<EditorFooter handleRun={handleRun} handleSubmit={handleSubmit} />
		</div>
	);
};
export default Playground;
