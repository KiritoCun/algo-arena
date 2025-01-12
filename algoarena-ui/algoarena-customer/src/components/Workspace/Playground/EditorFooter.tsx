import React from "react";
import { BsChevronUp } from "react-icons/bs";

type EditorFooterProps = {
	handleRun: () => void;
	handleSubmit: () => void;
	toggleShowHideChatGptTab: () => void;
	isChatGptVisible: boolean; // Thêm trạng thái hiển thị Chat GPT
};

const EditorFooter: React.FC<EditorFooterProps> = ({
	handleRun,
	handleSubmit,
	toggleShowHideChatGptTab,
	isChatGptVisible, // Nhận trạng thái hiển thị Chat GPT
}) => {
	return (
		<div className="flex bg-dark-layer-1 relative w-full">
		<div className="mx-5 my-[10px] flex justify-between w-full">
			<div className="mr-2 flex flex-1 flex-nowrap items-center space-x-4">
			<button
				className={`px-3 py-1.5 font-medium items-center transition-all inline-flex text-sm rounded-lg pl-3 pr-2 ${
				isChatGptVisible
					? "bg-blue-500 text-white hover:bg-blue-600" // Highlight nếu tab Chat GPT hiển thị
					: "bg-dark-fill-3 text-dark-label-2 hover:bg-dark-fill-2" // Bình thường
				}`}
				onClick={toggleShowHideChatGptTab}
			>
				GPT-4
			</button>
			</div>
			<div className="ml-auto flex items-center space-x-4">
			<button
			className="px-3 py-1.5 text-sm font-medium items-center whitespace-nowrap transition-all focus:outline-none inline-flex text-white bg-dark-green-s hover:bg-green-3 rounded-lg"
			onClick={handleRun}
			>
			Run
			</button>
			<button
			className="px-3 py-1.5 font-medium items-center transition-all focus:outline-none inline-flex text-sm text-white bg-red-500 hover:bg-red-600 rounded-lg"
			onClick={handleSubmit}
			>
			Submit
			</button>
			</div>
		</div>
		</div>
	);
};

export default EditorFooter;
