package vn.udn.dut.algoarena.port.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.udn.dut.algoarena.common.tenant.core.TenantEntity;

import java.io.Serial;

/**
 * ProblemFunctionSignature object
 *
 * @author HoaLD
 * @date 2024-12-25
 */
@Data
@TableName("problem_function_signature")
@EqualsAndHashCode(callSuper = true)
public class ProblemFunctionSignature extends TenantEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	@TableId(value = "id")
	private Long id;

	private Long problemId;

	private String language;

	private String functionSignature;

	private String remark;
}
