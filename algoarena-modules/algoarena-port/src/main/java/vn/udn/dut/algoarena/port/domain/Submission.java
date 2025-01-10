package vn.udn.dut.algoarena.port.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.udn.dut.algoarena.common.tenant.core.TenantEntity;

import java.io.Serial;

/**
 * Submission object
 *
 * @author HoaLD
 * @date 2024-11-20
 */
@Data
@TableName("submission")
@EqualsAndHashCode(callSuper = true)
public class Submission extends TenantEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	@TableId(value = "id")
	private Long id;

	private Long examId;

	private Long problemId;

	private Long userId;

	private String programingLanguage;

	private String code;

	private Integer status;

	private Integer score;

	private String remark;
}
