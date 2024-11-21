package vn.udn.dut.algoarena.port.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.udn.dut.algoarena.common.tenant.core.TenantEntity;

import java.io.Serial;
import java.util.Date;

/**
 * Exam problem object
 *
 * @author HoaLD
 * @date 2024-11-20
 */
@Data
@TableName("exam_problem")
public class ExamProblem {

	private String tenantId;

	@TableId(value = "id")
	private Long id;

	private Long examId;

	private Long problemId;

	private Integer maxScore;
}
