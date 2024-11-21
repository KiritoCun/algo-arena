package vn.udn.dut.algoarena.port.domain.vo;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;

import lombok.Data;

/**
 * Showtime view object
 *
 * @author HoaLD
 * @date 2023-11-07
 */
@Data
@ExcelIgnoreUnannotated
public class ShowtimeInfoVo implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * AlgoArena ID
	 */
	private Long id;

	private AlgoArenaVo algoArena;

	private List<ShowtimeVo> showtimeList;
}
