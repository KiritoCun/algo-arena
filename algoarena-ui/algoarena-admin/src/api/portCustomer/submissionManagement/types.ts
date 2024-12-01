export interface SubmissionVO {
  /**
   * Submission id
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
  submissionName: string;

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

export interface SubmissionForm extends BaseEntity {
  /**
   * Submission id
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

export interface SubmissionQuery extends PageQuery {
  
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

export interface SubmissionTestcaseVO {
  /**
   * Submission id
   */
  submissionId: string | number;

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

export interface SubmissionTestcaseForm extends BaseEntity {
  /**
   * Submission id
   */
  id?: string | number;

  /**
   * Cinema id
   */
  submissionId?: string | number;

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

export interface SubmissionTestcaseQuery {
  
  /**
   *
   */
  submissionId?: string | number;
}