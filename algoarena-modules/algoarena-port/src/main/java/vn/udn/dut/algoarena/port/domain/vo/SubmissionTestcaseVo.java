package vn.udn.dut.algoarena.port.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import vn.udn.dut.algoarena.port.domain.Problem;
import vn.udn.dut.algoarena.port.domain.SubmissionTestcase;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * Submission testcase view object
 *
 * @author HoaLD
 * @date 2024-11-20
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = SubmissionTestcase.class)
public class SubmissionTestcaseVo implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

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
