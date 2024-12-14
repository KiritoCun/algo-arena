package vn.udn.dut.algoarena.port.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.udn.dut.algoarena.common.tenant.core.TenantEntity;

import java.io.Serial;

/**
 * Testcase object
 *
 * @author HoaLD
 * @date 2024-11-20
 */
@Data
@TableName("testcase")
@EqualsAndHashCode(callSuper = true)
public class Testcase extends TenantEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	@TableId(value = "id")
	private Long id;

	private Long problemId;

	private String testcaseJson;

	private String illustration;

	private Long ossId;

	private String explanation;

	private Integer isHidden;

	private String remark;
}
