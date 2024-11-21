package vn.udn.dut.algoarena.port.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import vn.udn.dut.algoarena.port.domain.Problem;
import vn.udn.dut.algoarena.port.domain.SubmissionTestcase;

import java.util.Date;

/**
 * Submission testcase business object
 *
 * @author HoaLD
 * @date 2024-11-20
 */
@Data
@AutoMapper(target = SubmissionTestcase.class, reverseConvertGenerate = false)
public class SubmissionTestcaseBo {

	private String tenantId;

	private Long id;

	private Long submissionId;

	private Long testcaseId;

	private String actualOutput;

	private boolean isCorrect;

	private Date executedAt;

	private String remark;

	private Date createTime;
}
