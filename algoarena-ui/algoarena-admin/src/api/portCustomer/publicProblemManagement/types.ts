export interface ProblemVO {
  /**
   * Problem id
   */
  id: string | number;

  /**
   * Title
   */
  title: string;

  /**
   * Description
   */
  description: string;

  /**
   *
   */
  maxScore: number;

  /**
   *
   */
  numberTestcase: number;

  /**
   *
   */
  level: string | number;

  /**
   * Remark
   */
  remark: string;
}

export interface ProblemForm extends BaseEntity {
  /**
   * Problem id
   */
  id?: string | number;

  /**
   * Exam id
   */
  examId?: string | number;

  /**
   * Title
   */
  title?: string;

  /**
   *
   */
  description?: string;

  /**
   *
   */
  difficulty?: string | number;

  /**
   *
   */
  maxScore?: number;

  /**
   *
   */
  numberTestcase?: number;

  /**
   * Remark
   */
  remark?: string;
}

export interface ProblemForm extends BaseEntity {
  /**
   * Problem id
   */
  id?: string | number;

  /**
   * Problem tag
   */
  tag?: string | number;

  /**
   * Title
   */
  title?: string;

  /**
   *
   */
  description?: string;

  /**
   *
   */
  difficulty?: string | number;

  keyPath?: string;

  category?: string;

  videoId?: string;

  /**
   *
   */
  functionDefinitionJava?: string;

  /**
   * Remark
   */
  remark?: string;
}

export interface ProblemQuery extends PageQuery {
  
  /**
   *
   */
  title: string;

  /**
   *
   */
  description: string;

  /**
   *
   */
  difficulty: string;

  /**
   *
   */
  remark: string;
}

export interface TestcaseVO {
  /**
   * Testcase id
   */
  id?: string | number;

  /**
   * Problem id
   */
  problemId: string | number;

  /**
   * Input data
   */
  inputData: string | number;

  /**
   * Expected output
   */
  expectedOutput: string | number;

  /**
   *
   */
  illustration: string;

  /**
   *
   */
  explanation: string | number;

  /**
   *
   */
  isHidden: string | number | boolean;

  /**
   *
   */
  remark: string;
}

export interface FunctionDefinitionVO {
  /**
   * FunctionDefinition id
   */
  id?: string | number;

  /**
   * Problem id
   */
  problemId: string | number;

  /**
   * Programing language
   */
  language: string;

  /**
   * Function definition inferer
   */
  functionDefinition: string;

  /**
   *
   */
  remark: string;
}

export interface TestcaseForm extends BaseEntity {
  /**
   * Testcase id
   */
  id?: string | number;
  
  /**
   * Problem id
   */
  problemId?: string | number;

  /**
   * Input data
   */
  inputData?: string | number;

  /**
   * Expected output
   */
  expectedOutput?: string | number;

  /**
   *
   */
  ossId?: number;

  /**
   *
   */
  illustration?: string;

  /**
   *
   */
  explanation?: string | number;

  /**
   *
   */
  isHidden?: string | number | boolean;

  /**
   *
   */
  remark?: string;
}

export interface FunctionDefinitionForm extends BaseEntity {
  /**
   * Function definition id
   */
  id?: string | number;
  
  /**
   * Problem id
   */
  problemId?: string | number;

  /**
   * Programing language
   */
  language?: string;

  /**
   * Function definition
   */
  functionDefinition?: string;

  /**
   *
   */
  remark?: string;
}

export interface TestcaseQuery {
  
  /**
   *
   */
  problemId?: string | number;
}

export interface FunctionDefinitionQuery {
  
  /**
   *
   */
  problemId?: string | number;
}