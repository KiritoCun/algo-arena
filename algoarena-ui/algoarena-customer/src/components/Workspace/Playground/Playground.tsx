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
	const [activeTestCaseId, setActiveTestCaseId] = useState<number>(0);
	let [userCode, setUserCode] = useState<string>(problem.starterCode);

	const [fontSize, setFontSize] = useLocalStorage("lcc-fontSize", "16px");

	const [settings, setSettings] = useState<ISettings>({
		fontSize: fontSize,
		settingsModalIsOpen: false,
		dropdownIsOpen: false,
	});

	const { language } = useContext(LanguageContext);

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

	const functionSignatures  = {
		'two-sum': {
			JavaScript: 'twoSum = function(nums, target)',
			Java: 'int[] twoSum(int[] nums, int target)',
			PHP: 'twoSum($nums, $target)',
			C: 'int* twoSum(int* nums, int numsSize, int target, int* returnSize)',
			'C++': 'vector<int> twoSum(vector<int>& nums, int target)',
			'C#': 'int[] TwoSum(int[] nums, int target)'
		},
		'jump-game': {
			JavaScript: 'canJump = function(nums)',
			Java: 'boolean canJump(int[] nums)',
			PHP: 'canJump($nums)',
			C: 'bool canJump(int* nums, int numsSize)',
			'C++': 'bool canJump(vector<int>& nums)',
			'C#': 'bool CanJump(int[] nums)'
		},
		'reverse-linked-list': {
			JavaScript: 'reverseList = function(head)',
			Java: 'ListNode reverseList(ListNode head)',
			PHP: 'reverseList($head)',
			C: 'struct ListNode* reverseList(struct ListNode* head)',
			'C++': 'ListNode* reverseList(ListNode* head)',
			'C#': 'ListNode ReverseList(ListNode head)'
		},
		'search-a-2d-matrix': {
			JavaScript: 'searchMatrix = function(matrix, target)',
			Java: 'boolean searchMatrix(int[][] matrix, int target)',
			PHP: 'searchMatrix($matrix, $target)',
			C: 'bool searchMatrix(int** matrix, int matrixSize, int* matrixColSize, int target)',
			'C++': 'bool searchMatrix(vector<vector<int>>& matrix, int target)',
			'C#': 'bool SearchMatrix(int[][] matrix, int target)'
		},
		'valid-parentheses': {
			JavaScript: 'isValid = function(s)',
			Java: 'boolean isValid(String s)',
			PHP: 'isValid($s)',
			C: 'bool isValid(char* s)',
			'C++': 'bool isValid(string s)',
			'C#': 'bool IsValid(string s)'
		}
	}

	const codeSamples = {
		JavaScript: `var ARGUMENTS {
    
};`,
		Java: `class Solution {
	public ARGUMENTS {
				
	}
}`,
		PHP: `class Solution {
	function ARGUMENTS {
		
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
	public ARGUMENTS {
		
	}
}`,
	};

	function generateCodeSample(language: string, keyPath: string) {
		// Kiểm tra nếu không tồn tại bài toán hoặc ngôn ngữ
		if (!functionSignatures[keyPath]) {
		  return `// Problem "${keyPath}" not found.`;
		}
		if (!functionSignatures[keyPath][language]) {
		  return `// Language "${language}" not supported for problem "${keyPath}".`;
		}
	  
		// Lấy chữ ký hàm từ arguments
		const functionSignature = functionSignatures[keyPath][language];
	  
		// Lấy code mẫu từ codeSamples
		const codeSampleTemplate = codeSamples[language];
	  
		if (!codeSampleTemplate) {
		  return `// Language "${language}" does not have a code sample template.`;
		}
	  
		// Thay thế ARGUMENTS bằng functionSignature
		return codeSampleTemplate.replace("ARGUMENTS", functionSignature);
	  }

	//const [user] = useAuthState(auth);
	const { user } = useContext(AuthContext);
	const {
		query: { pid },
	} = useRouter();

	const handleSubmit = async () => {
		// if (!user) {
		// 	toast.error("Please login to submit your code", {
		// 		position: "top-center",
		// 		autoClose: 3000,
		// 		theme: "dark",
		// 	});
		// 	//return;
		// }
		try {
			console.log(userCode)
			//userCode = userCode.slice(userCode.indexOf(problem.starterFunctionName));
			// const cb = new Function(`return ${userCode}`)();
			// const handler = problems[pid as string].handlerFunction;

			// if (typeof handler === "function") {
			// 	const success = handler(cb);
			// 	if (success) {
			// 		toast.success("Congrats! All tests passed!", {
			// 			position: "top-center",
			// 			autoClose: 3000,
			// 			theme: "dark",
			// 		});
			// 		setSuccess(true);
			// 		setTimeout(() => {
			// 			setSuccess(false);
			// 		}, 4000);

			// 		const userRef = doc(firestore, "users", user.uid);
			// 		await updateDoc(userRef, {
			// 			solvedProblems: arrayUnion(pid),
			// 		});
			// 		setSolved(true);
			// 	}
			// }

			const code = `class Solution {
	public int[] twoSum(int[] nums, int target) {
		Map<Integer, Integer> map = new HashMap<>();
		for (int i = 0; i < nums.length; i++) {
			int complement = target - nums[i];
			if (map.containsKey(complement)) {
				return new int[]{map.get(complement), i};
			}
			map.put(nums[i], i);
		}
		throw new IllegalArgumentException("No two sum solution");
	}
}
`;
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
						version: configCompile.version
					}),
					});
				
					const data = await response.json();
				
					console.log("data back from piston:", data);

					if (data.length === 1) {
						toast.error(`Error: ${data[0]}`, {
							position: "top-center",
							autoClose: 3000,
							theme: "dark",
						});
					} else if (data.every(result => result === "true")) {
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
				toast.error("Oops! One or more test cases failed", {
					position: "top-center",
					autoClose: 3000,
					theme: "dark",
				});
			} 
			// else {
			// 	toast.error(error.message, {
			// 		position: "top-center",
			// 		autoClose: 3000,
			// 		theme: "dark",
			// 	});
			// }
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
	setUserCode(generateCodeSample(language, pid as string));
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
						//value={generateCodeSample(language, pid)}
						theme={vscodeDark}
						onChange={onChange}
						extensions={[javascript()]}
						style={{ fontSize: settings.fontSize }}
					/>
				</div>
				<div className='w-full px-5 overflow-auto'>
					{/* testcase heading */}
					<div className='flex h-10 items-center space-x-6'>
						<div className='relative flex h-full flex-col justify-center cursor-pointer'>
							<div className='text-sm font-medium leading-5 text-white'>Testcases</div>
							<hr className='absolute bottom-0 h-0.5 w-full rounded-full border-none bg-white' />
						</div>
					</div>

					<div className='flex'>
						{problem.examples.map((example, index) => (
							<div
								className='mr-2 items-start mt-2 '
								key={example.id}
								onClick={() => setActiveTestCaseId(index)}
							>
								<div className='flex flex-wrap items-center gap-y-4'>
									<div
										className={`font-medium items-center transition-all focus:outline-none inline-flex bg-dark-fill-3 hover:bg-dark-fill-2 relative rounded-lg px-4 py-1 cursor-pointer whitespace-nowrap
										${activeTestCaseId === index ? "text-white" : "text-gray-500"}
									`}
									>
										Case {index + 1}
									</div>
								</div>
							</div>
						))}
					</div>

					<div className='font-semibold my-4'>
						<p className='text-sm font-medium mt-4 text-white'>Input:</p>
						<div className='w-full cursor-text rounded-lg border px-3 py-[10px] bg-dark-fill-3 border-transparent text-white mt-2'>
							{problem.examples[activeTestCaseId].inputText}
						</div>
						<p className='text-sm font-medium mt-4 text-white'>Output:</p>
						<div className='w-full cursor-text rounded-lg border px-3 py-[10px] bg-dark-fill-3 border-transparent text-white mt-2'>
							{problem.examples[activeTestCaseId].outputText}
						</div>
					</div>
				</div>
			</Split>
			<EditorFooter handleSubmit={handleSubmit} />
		</div>
	);
};
export default Playground;
