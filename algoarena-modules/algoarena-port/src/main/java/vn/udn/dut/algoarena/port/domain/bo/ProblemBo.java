package vn.udn.dut.algoarena.port.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import vn.udn.dut.algoarena.port.domain.Booking;
import vn.udn.dut.algoarena.port.domain.Problem;

import java.util.Date;

/**
 * Problem business object
 *
 * @author HoaLD
 * @date 2024-11-20
 */
@Data
@AutoMapper(target = Problem.class, reverseConvertGenerate = false)
public class ProblemBo {

	private String tenantId;

	private Long id;

	private Long examId;

	private String title;

	private String description;

	private Integer difficulty;

	private Integer maxScore;

	private String remark;

	private Date createTime;
}
