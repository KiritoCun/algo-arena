package vn.udn.dut.algoarena.port.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.udn.dut.algoarena.common.tenant.core.TenantEntity;

import java.io.Serial;
import java.util.Date;

/**
 * Submission testcase object
 *
 * @author HoaLD
 * @date 2024-11-20
 */
@Data
@TableName("submission_testcase")
@EqualsAndHashCode(callSuper = true)
public class SubmissionTestcase extends TenantEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	@TableId(value = "id")
	private Long id;

	private Long submissionId;

	private Long testcaseId;

	private String actualOutput;

	private boolean isCorrect;

	private Date executedAt;

	private String remark;
}
