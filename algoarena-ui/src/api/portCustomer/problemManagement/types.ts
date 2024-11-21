export interface ProblemVO {
  /**
   * Problem id
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
  problemName: string;

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

export interface ProblemForm extends BaseEntity {
  /**
   * Problem id
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
   * Problem id
   */
  problemId: string | number;

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

export interface TestcaseForm extends BaseEntity {
  /**
   * Problem id
   */
  id?: string | number;

  /**
   * Cinema id
   */
  problemId?: string | number;

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

export interface TestcaseQuery {
  
  /**
   *
   */
  problemId?: string | number;
}