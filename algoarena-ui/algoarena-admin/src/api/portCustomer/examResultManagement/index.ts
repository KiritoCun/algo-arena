import request from '@/utils/request';
import { AxiosPromise } from 'axios';
import { ExamResultVO, ExamResultDetailVO, ExamResultForm, ExamResultDetailForm, ExamResultQuery, ExamResultDetailQuery } from '@/api/portCustomer/examResultManagement/types';
import { parseStrEmpty } from '@/utils/algoarena';

/**
 * Query ExamResult list
 * @param query
 * @returns {*}
 */
export const listExamResult = (query?: ExamResultQuery): AxiosPromise<ExamResultVO[]> => {
  return request({
    url: '/portCustomer/examResult/list',
    method: 'get',
    params: query
  });
};

/**
 * Query detail of ExamResult
 * @param id
 */
export const getExamResult = (id?: string | number): AxiosPromise<ExamResultVO> => {
  return request({
    url: '/portCustomer/examResult/' + parseStrEmpty(id),
    method: 'get'
  });
};

/**
 * Add ExamResult
 * @param data
 */
export const addExamResult = (data: ExamResultForm) => {
  return request({
    url: '/portCustomer/examResult',
    method: 'post',
    data: data
  });
};

/**
 * Edit ExamResult
 * @param data
 */
export const updateExamResult = (data: ExamResultForm) => {
  return request({
    url: '/portCustomer/examResult',
    method: 'put',
    data: data
  });
};

/**
 * Delete ExamResult
 * @param id
 */
export const delExamResult = (id: string | number | Array<string | number>) => {
  return request({
    url: '/portCustomer/examResult/' + id,
    method: 'delete'
  });
};

/**
 * Query ExamResult list
 * @param query
 * @returns {*}
 */
export const listExamResultDetail = (query?: ExamResultDetailQuery): AxiosPromise<ExamResultDetailVO[]> => {
  return request({
    url: '/portCustomer/examResultDetail/list',
    method: 'get',
    params: query
  });
};

/**
 * Edit ExamResult
 * @param data
 */
export const updateExamResultDetails = (examResultSeats: ExamResultDetailVO[], examResultId: string | number) => {
  return request({
    url: '/portCustomer/examResult/' + examResultId,
    method: 'put',
    data: examResultSeats
  });
};

export const getExamResultMap = (): AxiosPromise<any> => {
  return request({
    url: '/portCustomer/examResult/map',
    method: 'get'
  });
};
