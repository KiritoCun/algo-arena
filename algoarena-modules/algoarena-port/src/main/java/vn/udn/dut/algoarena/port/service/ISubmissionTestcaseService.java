package vn.udn.dut.algoarena.port.service;

import vn.udn.dut.algoarena.common.mybatis.core.page.PageQuery;
import vn.udn.dut.algoarena.common.mybatis.core.page.TableDataInfo;
import vn.udn.dut.algoarena.port.domain.bo.SubmissionTestcaseBo;
import vn.udn.dut.algoarena.port.domain.vo.SubmissionTestcaseVo;

import java.util.Collection;
import java.util.List;

/**
 * SubmissionTestcase service interface
 *
 * @author HoaLD
 * @date 2024-11-23
 */
public interface ISubmissionTestcaseService {

	/**
	 * Query SubmissionTestcase
	 */
	SubmissionTestcaseVo queryById(Long id);

	/**
	 * Query SubmissionTestcase list
	 */
	TableDataInfo<SubmissionTestcaseVo> queryPageList(SubmissionTestcaseBo bo, PageQuery pageQuery);

	/**
	 * Query SubmissionTestcase list
	 */
	List<SubmissionTestcaseVo> queryList(SubmissionTestcaseBo bo);

	/**
	 * Add SubmissionTestcase
	 */
	Boolean insertByBo(SubmissionTestcaseBo bo);

	/**
	 * Edit SubmissionTestcase
	 */
	Boolean updateByBo(SubmissionTestcaseBo bo);

	/**
	 * Verify and delete SubmissionTestcase information in batches
	 */
	Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

}
