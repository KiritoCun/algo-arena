package vn.udn.dut.algoarena.port.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import vn.udn.dut.algoarena.port.domain.ExamResult;
import vn.udn.dut.algoarena.port.domain.Problem;

import java.util.Date;

/**
 * Exam result business object
 *
 * @author HoaLD
 * @date 2024-11-20
 */
@Data
@AutoMapper(target = ExamResult.class, reverseConvertGenerate = false)
public class ExamResultBo {

	private String tenantId;

	private Long id;

	private Long examId;

	private Long userId;

	private Integer totalScore;

	private Date completedAt;

	private String remark;

	private Date createTime;
}
