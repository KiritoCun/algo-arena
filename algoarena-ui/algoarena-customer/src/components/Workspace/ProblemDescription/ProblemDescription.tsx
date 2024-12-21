import { DBProblem, Problem } from "@/utils/types/problem";
import { useEffect, useState } from "react";
import { fetchProblems } from "@/pages/api/api";

type ProblemDescriptionProps = {
	problem: Problem;
	_solved: boolean;
};

const ProblemDescription: React.FC<ProblemDescriptionProps> = ({ problem, _solved }) => {
	// Sử dụng useState để lưu trữ vấn đề
	const [currentProblem, setCurrentProblem] = useState<Problem | null>(null);

	// Hàm gọi API để cập nhật problem
	useEffect(() => {
		const setProblem = async () => {
			const problems = await fetchProblems();
			const foundProblem = problems.find(p => p.keyPath === problem.id); // Tìm vấn đề theo keyPath
			if (foundProblem) {
				setCurrentProblem(foundProblem); // Cập nhật trạng thái problem
			}
		};
		setProblem();
	}, [problem.id]); // Chạy lại khi problem.id thay đổi

	// Nếu chưa có problem, hiển thị loading
	if (!currentProblem) {
		return <div>Loading...</div>;
	}

	return (
		<div className='bg-dark-layer-1'>
			{/* TAB */}
			<div className='flex h-11 w-full items-center pt-2 bg-dark-layer-2 text-white overflow-x-hidden'>
				<div className={"bg-dark-layer-1 rounded-t-[5px] px-5 py-[10px] text-xs cursor-pointer"}>
					Description
				</div>
			</div>

			<div className='flex px-0 py-4 h-[calc(100vh-94px)] overflow-y-auto'>
				<div className='px-5'>
					{/* Problem heading */}
					<div className='w-full'>
						<div dangerouslySetInnerHTML={{ __html: currentProblem.description }} />
					</div>
				</div>
			</div>
		</div>
	);
};
export default ProblemDescription;
	