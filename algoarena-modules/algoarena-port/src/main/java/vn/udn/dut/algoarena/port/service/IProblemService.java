package vn.udn.dut.algoarena.port.service;

import vn.udn.dut.algoarena.common.mybatis.core.page.PageQuery;
import vn.udn.dut.algoarena.common.mybatis.core.page.TableDataInfo;
import vn.udn.dut.algoarena.port.domain.bo.ProblemBo;
import vn.udn.dut.algoarena.port.domain.vo.ProblemVo;

import java.util.Collection;
import java.util.List;

/**
 * Problem service interface
 *
 * @author HoaLD
 * @date 2024-11-20
 */
public interface IProblemService {

	/**
	 * Query Problem
	 */
	ProblemVo queryById(Long id);

	/**
	 * Query Problem list
	 */
	TableDataInfo<ProblemVo> queryPagePublicList(ProblemBo bo, PageQuery pageQuery);

	/**
	 * Query Problem list with userId
	 */
	TableDataInfo<ProblemVo> queryPagePublicListWithUserId(ProblemBo bo, PageQuery pageQuery, Long userId);

	/**
	 * Query Problem list
	 */
	TableDataInfo<ProblemVo> queryPageList(ProblemBo bo, PageQuery pageQuery);

	/**
	 * Query Problem list
	 */
	List<ProblemVo> queryList(ProblemBo bo);

	/**
	 * Query Problem list
	 */
	List<ProblemVo> queryPublicList(ProblemBo bo);

	/**
	 * Query Problem list with userId
	 */
	List<ProblemVo> queryPublicListWithUserId(Long userId);

	/**
	 * Add Problem
	 */
	Boolean insertByBo(ProblemBo bo);

	/**
	 * Edit Problem
	 */
	Boolean updateByBo(ProblemBo bo);

	/**
	 * Verify and delete Problem information in batches
	 */
	Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

}
