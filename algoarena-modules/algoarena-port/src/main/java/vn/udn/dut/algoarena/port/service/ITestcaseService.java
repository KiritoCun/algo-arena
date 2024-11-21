package vn.udn.dut.algoarena.port.service;

import vn.udn.dut.algoarena.common.mybatis.core.page.PageQuery;
import vn.udn.dut.algoarena.common.mybatis.core.page.TableDataInfo;
import vn.udn.dut.algoarena.port.domain.bo.TestcaseBo;
import vn.udn.dut.algoarena.port.domain.vo.TestcaseVo;

import java.util.Collection;
import java.util.List;

/**
 * Testcase service interface
 *
 * @author HoaLD
 * @date 2024-11-20
 */
public interface ITestcaseService {

	/**
	 * Query Testcase
	 */
	TestcaseVo queryById(Long id);

	/**
	 * Query Testcase list
	 */
	TableDataInfo<TestcaseVo> queryPageList(TestcaseBo bo, PageQuery pageQuery);

	/**
	 * Query Testcase list
	 */
	List<TestcaseVo> queryList(TestcaseBo bo);

	/**
	 * Add Testcase
	 */
	Boolean insertByBo(TestcaseBo bo);

	/**
	 * Edit Testcase
	 */
	Boolean updateByBo(TestcaseBo bo);

	/**
	 * Verify and delete Testcase information in batches
	 */
	Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

}
