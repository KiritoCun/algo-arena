export interface ExamVO {
  /**
   * Exam id
   */
  id: string | number;

  /**
   * Cinema id
   */
  cinemaId: string | number;

  /**
   * Cinema name
   */
  cinemaName: string;

  /**
   *
   */
  examName: string;

  /**
   *
   */
  capacity: Boolean;

  /**
   *
   */
  rowNumber: string | number;

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
  difficulty?: string | number;

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
  difficulty: string;

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
   * Exam id
   */
  id?: string | number;

  /**
   * Cinema id
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