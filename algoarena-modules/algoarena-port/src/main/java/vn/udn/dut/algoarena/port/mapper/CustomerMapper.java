package vn.udn.dut.algoarena.port.mapper;

import vn.udn.dut.algoarena.common.mybatis.core.mapper.BaseMapperPlus;
import vn.udn.dut.algoarena.port.domain.Customer;
import vn.udn.dut.algoarena.port.domain.vo.CustomerVo;

public interface CustomerMapper extends BaseMapperPlus<Customer, CustomerVo> {

	CustomerVo selectUserCustomerByUserName(String username);

	CustomerVo selectUserById(Long userId);

}
