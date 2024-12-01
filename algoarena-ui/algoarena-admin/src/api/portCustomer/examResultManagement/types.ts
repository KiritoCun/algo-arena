export interface ExamResultVO {
  /**
   * ExamResult id
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
  examResultName: string;

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

export interface ExamResultForm extends BaseEntity {
  /**
   * ExamResult id
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

export interface ExamResultQuery extends PageQuery {
  
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

export interface ExamResultDetailVO {
  /**
   * ExamResult id
   */
  examResultId: string | number;

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

export interface ExamResultDetailForm extends BaseEntity {
  /**
   * ExamResult id
   */
  id?: string | number;

  /**
   * Cinema id
   */
  examResultId?: string | number;

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

export interface ExamResultDetailQuery {
  
  /**
   *
   */
  examResultId?: string | number;
}