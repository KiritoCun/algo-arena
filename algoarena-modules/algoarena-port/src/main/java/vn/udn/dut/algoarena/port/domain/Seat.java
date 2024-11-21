package vn.udn.dut.algoarena.port.domain;

import java.io.Serial;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.udn.dut.algoarena.common.tenant.core.TenantEntity;

/**
 * Seat object
 *
 * @author HoaLD
 * @date 2023-11-07
 */
@Data
@TableName("seat")
@EqualsAndHashCode(callSuper = true)
public class Seat extends TenantEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	@TableId(value = "id")
	private Long id;

	private Long seatTypeId;

	private Long hallId;
	
	private Long showtimeId;

    private String rowCode;
    
    private Integer columnCode;
	
	private String status;

	private String remark;
}
