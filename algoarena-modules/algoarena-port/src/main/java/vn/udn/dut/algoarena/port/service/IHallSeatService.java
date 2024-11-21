package vn.udn.dut.algoarena.port.service;

import java.util.Collection;
import java.util.List;

import vn.udn.dut.algoarena.common.mybatis.core.page.PageQuery;
import vn.udn.dut.algoarena.common.mybatis.core.page.TableDataInfo;
import vn.udn.dut.algoarena.port.domain.bo.HallSeatBo;
import vn.udn.dut.algoarena.port.domain.vo.HallSeatVo;
import vn.udn.dut.algoarena.port.domain.vo.HallVo;

/**
 * HallSeat service interface
 *
 * @author HoaLD
 * @date 2023-12-10
 */
public interface IHallSeatService {

	/**
	 * Query HallSeat
	 */
	HallSeatVo queryById(Long id);

	/**
	 * Query HallSeat list
	 */
	TableDataInfo<HallSeatVo> queryPageList(HallSeatBo bo, PageQuery pageQuery);

	/**
	 * Query HallSeat list
	 */
	List<HallSeatVo> queryList(HallSeatBo bo);

	/**
	 * Add HallSeat
	 */
	Boolean insertByBo(HallSeatBo bo);

	/**
	 * Edit HallSeat
	 */
	Boolean updateByBo(HallSeatBo bo);

	/**
	 * Verify and delete HallSeat information in batches
	 */
	Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

	Boolean updateHallSeatListBo(List<HallSeatBo> hallSeats, HallVo hall);

}
