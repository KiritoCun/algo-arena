package vn.udn.dut.algoarena.port.service;

import vn.udn.dut.algoarena.common.mybatis.core.page.PageQuery;
import vn.udn.dut.algoarena.common.mybatis.core.page.TableDataInfo;
import vn.udn.dut.algoarena.port.domain.bo.ExamResultDetailBo;
import vn.udn.dut.algoarena.port.domain.vo.ExamResultDetailVo;

import java.util.Collection;
import java.util.List;

/**
 * ExamResultDetail service interface
 *
 * @author HoaLD
 * @date 2024-11-23
 */
public interface IExamResultDetailService {

	/**
	 * Query ExamResultDetail
	 */
	ExamResultDetailVo queryById(Long id);

	/**
	 * Query ExamResultDetail list
	 */
	TableDataInfo<ExamResultDetailVo> queryPageList(ExamResultDetailBo bo, PageQuery pageQuery);

	/**
	 * Query ExamResultDetail list
	 */
	List<ExamResultDetailVo> queryList(ExamResultDetailBo bo);

	/**
	 * Add ExamResultDetail
	 */
	Boolean insertByBo(ExamResultDetailBo bo);

	/**
	 * Edit ExamResultDetail
	 */
	Boolean updateByBo(ExamResultDetailBo bo);

	/**
	 * Verify and delete ExamResultDetail information in batches
	 */
	Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

}
