package vn.udn.dut.algoarena.port.domain.vo;

import java.io.Serial;
import java.io.Serializable;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import vn.udn.dut.algoarena.common.translation.annotation.Translation;
import vn.udn.dut.algoarena.common.translation.constant.TransConstant;
import vn.udn.dut.algoarena.port.domain.Customer;

/**
 * Customer view object
 *
 * @author HoaLD
 * @date 2023-11-07
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = Customer.class)
public class CustomerVo implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * User ID
	 */
	private Long userId;

	private String userName;

	private String nickName;

	private String customerType;

	private String email;

	private String phonenumber;

	private String sex;

    /**
     * Avatar address
     */
    @Translation(type = TransConstant.OSS_ID_TO_URL)
	private Long avatar;
    
	private String password;

	private String status;

	private String remark;
}
