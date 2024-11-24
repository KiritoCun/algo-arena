package vn.udn.dut.algoarena.port.service;

import vn.udn.dut.algoarena.common.mybatis.core.page.PageQuery;
import vn.udn.dut.algoarena.common.mybatis.core.page.TableDataInfo;
import vn.udn.dut.algoarena.port.domain.bo.ExamBo;
import vn.udn.dut.algoarena.port.domain.vo.ExamVo;

import java.util.Collection;
import java.util.List;

/**
 * Exam service interface
 *
 * @author HoaLD
 * @date 2024-11-23
 */
public interface IExamService {

	/**
	 * Query Exam
	 */
	ExamVo queryById(Long id);

	/**
	 * Query Exam list
	 */
	TableDataInfo<ExamVo> queryPageList(ExamBo bo, PageQuery pageQuery);

	/**
	 * Query Exam list
	 */
	List<ExamVo> queryList(ExamBo bo);

	/**
	 * Add Exam
	 */
	Boolean insertByBo(ExamBo bo);

	/**
	 * Edit Exam
	 */
	Boolean updateByBo(ExamBo bo);

	/**
	 * Verify and delete Exam information in batches
	 */
	Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

}
