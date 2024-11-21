package vn.udn.dut.algoarena.common.core.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * User registration object
 *
 * @author HoaLD
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RegisterBody extends LoginBody {

    private String userType;
    private String nickName;
    private String phonenumber;
    private String email;
    private String sex;

}
