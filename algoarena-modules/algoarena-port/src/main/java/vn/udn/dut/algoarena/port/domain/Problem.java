package vn.udn.dut.algoarena.port.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.udn.dut.algoarena.common.tenant.core.TenantEntity;

import java.io.Serial;
import java.util.Date;

/**
 * Problem object
 *
 * @author HoaLD
 * @date 2024-11-20
 */
@Data
@TableName("problem")
@EqualsAndHashCode(callSuper = true)
public class Problem extends TenantEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	@TableId(value = "id")
	private Long id;

	private Long examId;

	private String title;

	private String description;

	private Integer difficulty;

	private Integer maxScore;

	private String remark;
}
