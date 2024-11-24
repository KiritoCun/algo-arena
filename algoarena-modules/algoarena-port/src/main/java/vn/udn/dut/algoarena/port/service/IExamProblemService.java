package vn.udn.dut.algoarena.port.service;

import vn.udn.dut.algoarena.common.mybatis.core.page.PageQuery;
import vn.udn.dut.algoarena.common.mybatis.core.page.TableDataInfo;
import vn.udn.dut.algoarena.port.domain.bo.ExamProblemBo;
import vn.udn.dut.algoarena.port.domain.vo.ExamProblemVo;

import java.util.Collection;
import java.util.List;

/**
 * ExamProblem service interface
 *
 * @author HoaLD
 * @date 2024-11-23
 */
public interface IExamProblemService {

	/**
	 * Query ExamProblem
	 */
	ExamProblemVo queryById(Long id);

	/**
	 * Query ExamProblem list
	 */
	TableDataInfo<ExamProblemVo> queryPageList(ExamProblemBo bo, PageQuery pageQuery);

	/**
	 * Query ExamProblem list
	 */
	List<ExamProblemVo> queryList(ExamProblemBo bo);

	/**
	 * Add ExamProblem
	 */
	Boolean insertByBo(ExamProblemBo bo);

	/**
	 * Edit ExamProblem
	 */
	Boolean updateByBo(ExamProblemBo bo);

	/**
	 * Verify and delete ExamProblem information in batches
	 */
	Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

}
