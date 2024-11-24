package vn.udn.dut.algoarena.port.service;

import vn.udn.dut.algoarena.common.mybatis.core.page.PageQuery;
import vn.udn.dut.algoarena.common.mybatis.core.page.TableDataInfo;
import vn.udn.dut.algoarena.port.domain.bo.ExamResultBo;
import vn.udn.dut.algoarena.port.domain.vo.ExamResultVo;

import java.util.Collection;
import java.util.List;

/**
 * ExamResult service interface
 *
 * @author HoaLD
 * @date 2024-11-23
 */
public interface IExamResultService {

	/**
	 * Query ExamResult
	 */
	ExamResultVo queryById(Long id);

	/**
	 * Query ExamResult list
	 */
	TableDataInfo<ExamResultVo> queryPageList(ExamResultBo bo, PageQuery pageQuery);

	/**
	 * Query ExamResult list
	 */
	List<ExamResultVo> queryList(ExamResultBo bo);

	/**
	 * Add ExamResult
	 */
	Boolean insertByBo(ExamResultBo bo);

	/**
	 * Edit ExamResult
	 */
	Boolean updateByBo(ExamResultBo bo);

	/**
	 * Verify and delete ExamResult information in batches
	 */
	Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

}
