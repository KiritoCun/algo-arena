package vn.udn.dut.algoarena.port.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.udn.dut.algoarena.common.core.validate.AddGroup;
import vn.udn.dut.algoarena.common.core.validate.EditGroup;
import vn.udn.dut.algoarena.common.tenant.core.TenantEntity;
import vn.udn.dut.algoarena.port.domain.AlgoArena;

/**
 * AlgoArena business object
 *
 * @author HoaLD
 * @date 2023-11-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = AlgoArena.class, reverseConvertGenerate = false)
public class AlgoArenaBo extends TenantEntity {

	private static final long serialVersionUID = -2323039814896662171L;

	/**
	 * AlgoArena id
	 */
	@NotNull(message = "Id cannot be empty", groups = { EditGroup.class })
	private Long id;

	@NotBlank(message = "AlgoArena name cannot be empty", groups = { AddGroup.class, EditGroup.class })
	private String algoArenaName;

	@NotBlank(message = "AlgoArena address cannot be empty", groups = { AddGroup.class, EditGroup.class })
	private String algoArenaAddress;

	private String remark;
}
