package vn.udn.dut.algoarena.port.domain.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import io.github.linpeilie.annotations.ReverseAutoMapping;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.udn.dut.algoarena.common.excel.annotation.ExcelDictFormat;
import vn.udn.dut.algoarena.common.excel.convert.ExcelDictConvert;
import vn.udn.dut.algoarena.system.domain.vo.SysUserVo;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * Customer object export VO
 *
 * @author HoaLD
 */

@Data
@NoArgsConstructor
@AutoMapper(target = CustomerVo.class, convertGenerate = false)
public class CustomerExportVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * User ID
     */
    @ExcelProperty(value = "User ID")
    private Long userId;

    /**
     * user account
     */
    @ExcelProperty(value = "User account")
    private String userName;

    /**
     * User name
     */
    @ExcelProperty(value = "User name")
    private String nickName;

    /**
     * Email
     */
    @ExcelProperty(value = "Email")
    private String email;

    /**
     * phone number
     */
    @ExcelProperty(value = "Phone number")
    private String phonenumber;

    /**
     * user gender
     */
    @ExcelProperty(value = "User gender", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "sys_user_sex")
    private String sex;

    /**
     * Account status (0 normal 1 disabled)
     */
    @ExcelProperty(value = "Status", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "sys_normal_disable")
    private String status;
}
