package vn.udn.dut.algoarena.port.service;

import java.util.Collection;
import java.util.List;

import vn.udn.dut.algoarena.common.mybatis.core.page.PageQuery;
import vn.udn.dut.algoarena.common.mybatis.core.page.TableDataInfo;
import vn.udn.dut.algoarena.port.domain.bo.ShowtimeBo;
import vn.udn.dut.algoarena.port.domain.vo.ShowtimeInfoVo;
import vn.udn.dut.algoarena.port.domain.vo.ShowtimeVo;

/**
 * Showtime service interface
 *
 * @author HoaLD
 * @date 2023-12-13
 */
public interface IShowtimeService {

	/**
	 * Query Showtime
	 */
	ShowtimeVo queryById(Long id);

	/**
	 * Query Showtime list
	 */
	TableDataInfo<ShowtimeVo> queryPageList(ShowtimeBo bo, PageQuery pageQuery);

	/**
	 * Query Showtime list
	 */
	List<ShowtimeVo> queryList(ShowtimeBo bo);

	/**
	 * Add Showtime
	 */
	Boolean insertByBo(ShowtimeBo bo);

	/**
	 * Edit Showtime
	 */
	Boolean updateByBo(ShowtimeBo bo);

	/**
	 * Verify and delete Showtime information in batches
	 */
	Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

	void releaseShowtimes(List<ShowtimeBo> showtimes);

	List<ShowtimeInfoVo> fetchShowtimeInfoList(Long movieId);

}
