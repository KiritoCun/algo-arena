import request from '@/utils/request';
import { AxiosPromise } from 'axios';
import { ProblemVO, TestcaseVO, ProblemForm, TestcaseForm, ProblemQuery, TestcaseQuery } from '@/api/portCustomer/problemManagement/types';
import { parseStrEmpty } from '@/utils/starcinema';

/**
 * Query Problem list
 * @param query
 * @returns {*}
 */
export const listProblem = (query?: ProblemQuery): AxiosPromise<ProblemVO[]> => {
  return request({
    url: '/portCustomer/problem/public-list',
    method: 'get',
    params: query
  });
};

/**
 * Query detail of Problem
 * @param id
 */
export const getProblem = (id?: string | number): AxiosPromise<ProblemVO> => {
  return request({
    url: '/portCustomer/problem/' + parseStrEmpty(id),
    method: 'get'
  });
};

/**
 * Add Problem
 * @param data
 */
export const addProblem = (data: ProblemForm) => {
  return request({
    url: '/portCustomer/problem',
    method: 'post',
    data: data
  });
};

/**
 * Edit Problem
 * @param data
 */
export const updateProblem = (data: ProblemForm) => {
  return request({
    url: '/portCustomer/problem',
    method: 'put',
    data: data
  });
};

/**
 * Delete Problem
 * @param id
 */
export const delProblem = (id: string | number | Array<string | number>) => {
  return request({
    url: '/portCustomer/problem/' + id,
    method: 'delete'
  });
};

/**
 * Query Problem list
 * @param query
 * @returns {*}
 */
export const listTestcase = (query?: TestcaseQuery): AxiosPromise<TestcaseVO[]> => {
  return request({
    url: '/portCustomer/testcase/list',
    method: 'get',
    params: query
  });
};

/**
 * Edit Problem
 * @param data
 */
export const updateTestcases = (problemSeats: TestcaseVO[], problemId: string | number) => {
  return request({
    url: '/portCustomer/problem/' + problemId,
    method: 'put',
    data: problemSeats
  });
};

export const getProblemMap = (): AxiosPromise<any> => {
  return request({
    url: '/portCustomer/problem/map',
    method: 'get'
  });
};
