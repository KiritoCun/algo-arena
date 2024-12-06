export interface ExamVO {
  /**
   * Exam id
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
  startTime: string;

  /**
   *
   */
  endTime: string;

  /**
   * Remark
   */
  remark: string;
}

export interface ExamForm extends BaseEntity {
  /**
   * Exam id
   */
  id?: string | number;

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
  startTime?: string | number;

  /**
   *
   */
  endTime?: string | number;

  /**
   * Remark
   */
  remark?: string;
}

export interface ExamQuery extends PageQuery {
  
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
  startTime: string;

  /**
   *
   */
  endTime: string;

  /**
   *
   */
  remark: string;
}

export interface ProblemVO {
  /**
   * Exam id
   */
  examId: string | number;

  /**
   * Seat type id
   */
  seatTypeId: string | number;

  /**
   * Id
   */
  id: string | number;

  /**
   *
   */
  rowCode: string;

  /**
   *
   */
  columCode: string | number;

  /**
   *
   */
  createTime: string;
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
   * Seat type id
   */
  seatTypeId?: string | number;

  /**
   *
   */
  rowCode?: string | number;

  /**
   *
   */
  rowSeatNumber?: string;
}

export interface ProblemQuery {
  
  /**
   *
   */
  examId?: string | number;
}