import Topbar from "@/components/Topbar/Topbar";
import Workspace from "@/components/Workspace/Workspace";
import useHasMounted from "@/hooks/useHasMounted";
import React from "react";
import { fetchProblems } from "@/pages/api/api";

type Problem = {
  id: string;
  pid: string;
  title: string;
  description: string;
  handlerFunction: string;
};

type ProblemPageProps = {
  problem: Problem | null;
};

const ProblemPage: React.FC<ProblemPageProps> = ({ problem }) => {
  const hasMounted = useHasMounted();

  if (!hasMounted || !problem) return <div>Problem not found</div>;

  return (
    <div>
      <Topbar problemPage />
      <Workspace problem={problem} />
    </div>
  );
};

export default ProblemPage;

// getStaticPaths: Tạo các route động
export async function getStaticPaths() {
  let problems: Problem[] = [];
  try {
    // Gọi API để lấy danh sách bài toán
    const rawProblems = await fetchProblems(0);

    // Chuyển đổi dữ liệu trả về
    problems = rawProblems.map((item: any) => ({
      id: item.keyPath,
      pid: item.keyPath,
      title: item.title,
      description: item.description,
      handlerFunction: "", // Bạn có thể thêm logic để lấy function nếu cần
    }));
  } catch (error) {
    console.error("Failed to fetch problems:", error);
  }

  const paths = problems.map((problem) => ({
    params: { pid: problem.pid },
  }));

  return {
    paths,
    fallback: true, // Cho phép fallback khi route chưa tồn tại
  };
}

// getStaticProps: Fetch dữ liệu động
export async function getStaticProps({ params }: { params: { pid: string } }) {
  const { pid } = params;

  let problems: Problem[] = [];
  try {
    // Gọi API để lấy danh sách bài toán
    const rawProblems = await fetchProblems(0);

    // Chuyển đổi dữ liệu trả về
    problems = rawProblems.map((item: any) => ({
      id: item.keyPath,
      pid: item.keyPath,
      title: item.title,
      description: item.description,
      handlerFunction: "", // Bạn có thể thêm logic để lấy function nếu cần
    }));
  } catch (error) {
    console.error("Failed to fetch problems:", error);
  }

  // Tìm bài toán dựa trên pid
  const problem = problems.find((item) => item.pid === pid) || null;

  return {
    props: {
      problem,
    },
  };
}
