package vn.udn.dut.algoarena.port.domain;

import java.io.Serial;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.udn.dut.algoarena.common.tenant.core.TenantEntity;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("slide")
public class Slide extends TenantEntity {
	
	@Serial
	private static final long serialVersionUID = 1L;
	
	@TableId(value = "id")
	private Long id;
	
	private String slideUrl;
	
	private String remark;
}
