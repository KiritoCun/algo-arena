package vn.udn.dut.algoarena.port.service;

import java.util.Collection;
import java.util.List;

import vn.udn.dut.algoarena.common.mybatis.core.page.PageQuery;
import vn.udn.dut.algoarena.common.mybatis.core.page.TableDataInfo;
import vn.udn.dut.algoarena.port.domain.bo.SeatTypeBo;
import vn.udn.dut.algoarena.port.domain.vo.SeatTypeVo;

/**
 * Seat type service interface
 *
 * @author HoaLD
 * @date 2023-11-07
 */
public interface ISeatTypeService {

	/**
	 * Query SeatType
	 */
	SeatTypeVo queryById(Long id);

	/**
	 * Query Seat type list
	 */
	TableDataInfo<SeatTypeVo> queryPageList(SeatTypeBo bo, PageQuery pageQuery);

	/**
	 * Query Seat type list
	 */
	List<SeatTypeVo> queryList(SeatTypeBo bo);

	/**
	 * Add SeatType
	 */
	Boolean insertByBo(SeatTypeBo bo);

	/**
	 * Edit SeatType
	 */
	Boolean updateByBo(SeatTypeBo bo);

	/**
	 * Verify and delete Seat type information in batches
	 */
	Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

}
