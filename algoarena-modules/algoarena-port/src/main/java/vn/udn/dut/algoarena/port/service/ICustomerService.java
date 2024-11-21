package vn.udn.dut.algoarena.port.service;

import java.util.Collection;
import java.util.List;

import vn.udn.dut.algoarena.common.mybatis.core.page.PageQuery;
import vn.udn.dut.algoarena.common.mybatis.core.page.TableDataInfo;
import vn.udn.dut.algoarena.port.domain.bo.CustomerBo;
import vn.udn.dut.algoarena.port.domain.vo.CustomerVo;

public interface ICustomerService {
	CustomerVo queryById(Long id);

	TableDataInfo<CustomerVo> queryPageList(CustomerBo bo, PageQuery pageQuery);

	List<CustomerVo> queryList(CustomerBo bo);

	Boolean insertByBo(CustomerBo bo);

	Boolean updateByBo(CustomerBo bo);

	Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

	boolean checkUserNameUnique(CustomerBo customerUser);

	boolean registerUser(CustomerBo customerUser);

	CustomerVo selectByUserId(Long userId);

	boolean updateUserAvatar(Long userId, Long ossId);
}
