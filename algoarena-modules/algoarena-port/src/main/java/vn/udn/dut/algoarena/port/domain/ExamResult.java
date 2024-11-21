package vn.udn.dut.algoarena.port.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.udn.dut.algoarena.common.tenant.core.TenantEntity;

import java.io.Serial;
import java.util.Date;

/**
 * Exam result object
 *
 * @author HoaLD
 * @date 2024-11-20
 */
@Data
@TableName("exam_result")
@EqualsAndHashCode(callSuper = true)
public class ExamResult extends TenantEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	@TableId(value = "id")
	private Long id;

	private Long examId;

	private Long userId;

	private Integer totalScore;

	private Date completedAt;

	private String remark;
}
