package vn.udn.dut.algoarena.port.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import vn.udn.dut.algoarena.port.domain.ExamResultDetail;
import vn.udn.dut.algoarena.port.domain.Problem;

import java.util.Date;

/**
 * Exam result detail business object
 *
 * @author HoaLD
 * @date 2024-11-20
 */
@Data
@AutoMapper(target = ExamResultDetail.class, reverseConvertGenerate = false)
public class ExamResultDetailBo {

	private String tenantId;

	private Long id;

	private Long examId;

	private Long examResultId;

	private Long problemId;

	private Integer score;

	private Date completedAt;

	private String remark;

	private Date createTime;
}
