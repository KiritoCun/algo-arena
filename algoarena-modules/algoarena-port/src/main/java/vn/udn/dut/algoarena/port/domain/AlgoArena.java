package vn.udn.dut.algoarena.port.domain;

import java.io.Serial;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.udn.dut.algoarena.common.tenant.core.TenantEntity;

/**
 * AlgoArena object
 *
 * @author HoaLD
 * @date 2023-11-07
 */
@Data
@TableName("algoarena")
@EqualsAndHashCode(callSuper = true)
public class AlgoArena extends TenantEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	@TableId(value = "id")
	private Long id;

	private String algoArenaName;

	private String algoArenaAddress;

	private String remark;
}
