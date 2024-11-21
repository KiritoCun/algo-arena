package vn.udn.dut.algoarena.port.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import vn.udn.dut.algoarena.port.domain.Booking;
import vn.udn.dut.algoarena.port.domain.Problem;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * Problem view object
 *
 * @author HoaLD
 * @date 2024-11-20
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = Problem.class)
public class ProblemVo implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	private String tenantId;

	private Long id;

	private String title;

	private String description;

	private Integer difficulty;

	private String remark;

	private Date createTime;
}
