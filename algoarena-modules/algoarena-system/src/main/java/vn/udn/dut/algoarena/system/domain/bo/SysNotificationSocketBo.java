package vn.udn.dut.algoarena.system.domain.bo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.udn.dut.algoarena.common.mybatis.core.domain.BaseEntity;

@EqualsAndHashCode(callSuper = true)
@Data
public class SysNotificationSocketBo extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String title;
	
	private String message;
	
	private String messageType;
	
	private String routePath;
}
