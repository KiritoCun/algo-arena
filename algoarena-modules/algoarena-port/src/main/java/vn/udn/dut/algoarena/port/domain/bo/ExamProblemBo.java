package vn.udn.dut.algoarena.port.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import vn.udn.dut.algoarena.port.domain.Exam;
import vn.udn.dut.algoarena.port.domain.ExamProblem;

import java.util.Date;

/**
 * Exam problem business object
 *
 * @author HoaLD
 * @date 2024-11-20
 */
@Data
@AutoMapper(target = ExamProblem.class, reverseConvertGenerate = false)
public class ExamProblemBo {

	private String tenantId;

	private Long id;

	private Long examId;

	private Long problemId;

	private Integer maxScore;

	private Date createTime;
}
