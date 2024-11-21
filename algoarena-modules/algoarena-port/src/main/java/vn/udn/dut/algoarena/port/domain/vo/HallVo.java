package vn.udn.dut.algoarena.port.domain.vo;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import vn.udn.dut.algoarena.port.domain.Hall;

/**
 * Hall view object
 *
 * @author HoaLD
 * @date 2023-11-07
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = Hall.class)
public class HallVo implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * Seat ID
	 */
	private Long id;

	private Long algoArenaId;
	
	private String algoArenaName;

	private String hallName;

	private Boolean capacity;

	private Long rowNumber;
	
	private Date createTime;

	private String remark;
}
