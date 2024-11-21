package vn.udn.dut.algoarena.port.domain.vo;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import vn.udn.dut.algoarena.port.domain.AlgoArena;

/**
 * AlgoArena view object
 *
 * @author HoaLD
 * @date 2023-11-07
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = AlgoArena.class)
public class AlgoArenaVo implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * AlgoArena id
	 */
	private Long id;

	private String algoArenaName;

	private String algoArenaAddress;

	private String remark;
	
	private Date createTime;
	
	
}
