package vn.udn.dut.algoarena.port.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import vn.udn.dut.algoarena.common.mybatis.core.page.PageQuery;
import vn.udn.dut.algoarena.common.mybatis.core.page.TableDataInfo;
import vn.udn.dut.algoarena.port.domain.bo.HallBo;
import vn.udn.dut.algoarena.port.domain.vo.HallVo;

/**
 * Hall service interface
 *
 * @author HoaLD
 * @date 2023-12-13
 */
public interface IHallService {

	/**
	 * Query Hall
	 */
	HallVo queryById(Long id);

	/**
	 * Query Hall list
	 */
	TableDataInfo<HallVo> queryPageList(HallBo bo, PageQuery pageQuery);

	/**
	 * Query Hall list
	 */
	List<HallVo> queryList(HallBo bo);

	/**
	 * Add Hall
	 */
	Boolean insertByBo(HallBo bo);

	/**
	 * Edit Hall
	 */
	Boolean updateByBo(HallBo bo);

	/**
	 * Verify and delete Hall information in batches
	 */
	Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

	Map<Long, List<HallVo>> getHallMap();

}
