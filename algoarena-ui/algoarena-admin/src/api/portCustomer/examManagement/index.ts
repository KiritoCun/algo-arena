import request from '@/utils/request';
import { AxiosPromise } from 'axios';
import { ExamVO, ProblemVO, ExamForm, ProblemForm, ExamQuery, ProblemQuery } from '@/api/portCustomer/examManagement/types';
import { parseStrEmpty } from '@/utils/starcinema';

/**
 * Query Exam list
 * @param query
 * @returns {*}
 */
export const listExam = (query?: ExamQuery): AxiosPromise<ExamVO[]> => {
  return request({
    url: '/portCustomer/exam/list',
    method: 'get',
    params: query
  });
};

/**
 * Query detail of Exam
 * @param id
 */
export const getExam = (id?: string | number): AxiosPromise<ExamVO> => {
  return request({
    url: '/portCustomer/exam/' + parseStrEmpty(id),
    method: 'get'
  });
};

/**
 * Add Exam
 * @param data
 */
export const addExam = (data: ExamForm) => {
  return request({
    url: '/portCustomer/exam',
    method: 'post',
    data: data
  });
};

/**
 * Edit Exam
 * @param data
 */
export const updateExam = (data: ExamForm) => {
  return request({
    url: '/portCustomer/exam',
    method: 'put',
    data: data
  });
};

/**
 * Delete Exam
 * @param id
 */
export const delExam = (id: string | number | Array<string | number>) => {
  return request({
    url: '/portCustomer/exam/' + id,
    method: 'delete'
  });
};

/**
 * Query Exam list
 * @param query
 * @returns {*}
 */
export const listProblem = (query?: ProblemQuery): AxiosPromise<ProblemVO[]> => {
  return request({
    url: '/portCustomer/problem/list',
    method: 'get',
    params: query
  });
};

/**
 * Edit Exam
 * @param data
 */
export const updateProblems = (examSeats: ProblemVO[], examId: string | number) => {
  return request({
    url: '/portCustomer/exam/' + examId,
    method: 'put',
    data: examSeats
  });
};

export const getExamMap = (): AxiosPromise<any> => {
  return request({
    url: '/portCustomer/exam/map',
    method: 'get'
  });
};
