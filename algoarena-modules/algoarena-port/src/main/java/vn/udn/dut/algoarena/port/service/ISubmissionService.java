package vn.udn.dut.algoarena.port.service;

import vn.udn.dut.algoarena.common.mybatis.core.page.PageQuery;
import vn.udn.dut.algoarena.common.mybatis.core.page.TableDataInfo;
import vn.udn.dut.algoarena.port.domain.bo.SubmissionBo;
import vn.udn.dut.algoarena.port.domain.vo.SubmissionVo;

import java.util.Collection;
import java.util.List;

/**
 * Submission service interface
 *
 * @author HoaLD
 * @date 2024-11-23
 */
public interface ISubmissionService {

	/**
	 * Query Submission
	 */
	SubmissionVo queryById(Long id);

	/**
	 * Query Submission list
	 */
	TableDataInfo<SubmissionVo> queryPageList(SubmissionBo bo, PageQuery pageQuery);

	/**
	 * Query Submission list
	 */
	List<SubmissionVo> queryList(SubmissionBo bo);

	/**
	 * Add Submission
	 */
	Boolean insertByBo(SubmissionBo bo);

	/**
	 * Edit Submission
	 */
	Boolean updateByBo(SubmissionBo bo);

	/**
	 * Verify and delete Submission information in batches
	 */
	Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

}
