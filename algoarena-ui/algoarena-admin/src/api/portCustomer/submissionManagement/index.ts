import request from '@/utils/request';
import { AxiosPromise } from 'axios';
import { SubmissionVO, SubmissionTestcaseVO, SubmissionForm, SubmissionTestcaseForm, SubmissionQuery, SubmissionTestcaseQuery } from '@/api/portCustomer/submissionManagement/types';
import { parseStrEmpty } from '@/utils/algoarena';

/**
 * Query Submission list
 * @param query
 * @returns {*}
 */
export const listSubmission = (query?: SubmissionQuery): AxiosPromise<SubmissionVO[]> => {
  return request({
    url: '/portCustomer/submission/list',
    method: 'get',
    params: query
  });
};

/**
 * Query detail of Submission
 * @param id
 */
export const getSubmission = (id?: string | number): AxiosPromise<SubmissionVO> => {
  return request({
    url: '/portCustomer/submission/' + parseStrEmpty(id),
    method: 'get'
  });
};

/**
 * Add Submission
 * @param data
 */
export const addSubmission = (data: SubmissionForm) => {
  return request({
    url: '/portCustomer/submission',
    method: 'post',
    data: data
  });
};

/**
 * Edit Submission
 * @param data
 */
export const updateSubmission = (data: SubmissionForm) => {
  return request({
    url: '/portCustomer/submission',
    method: 'put',
    data: data
  });
};

/**
 * Delete Submission
 * @param id
 */
export const delSubmission = (id: string | number | Array<string | number>) => {
  return request({
    url: '/portCustomer/submission/' + id,
    method: 'delete'
  });
};

/**
 * Query Submission list
 * @param query
 * @returns {*}
 */
export const listSubmissionTestcase = (query?: SubmissionTestcaseQuery): AxiosPromise<SubmissionTestcaseVO[]> => {
  return request({
    url: '/portCustomer/submissionTestcase/list',
    method: 'get',
    params: query
  });
};

/**
 * Edit Submission
 * @param data
 */
export const updateSubmissionTestcases = (submissionSeats: SubmissionTestcaseVO[], submissionId: string | number) => {
  return request({
    url: '/portCustomer/submission/' + submissionId,
    method: 'put',
    data: submissionSeats
  });
};

export const getSubmissionMap = (): AxiosPromise<any> => {
  return request({
    url: '/portCustomer/submission/map',
    method: 'get'
  });
};
