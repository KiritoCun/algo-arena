package vn.udn.dut.algoarena.port.listener;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import lombok.extern.slf4j.Slf4j;
import vn.udn.dut.algoarena.common.core.exception.ServiceException;
import vn.udn.dut.algoarena.common.core.utils.SpringUtils;
import vn.udn.dut.algoarena.common.core.utils.ValidatorUtils;
import vn.udn.dut.algoarena.common.excel.core.ExcelListener;
import vn.udn.dut.algoarena.common.excel.core.ExcelResult;
import vn.udn.dut.algoarena.common.satoken.utils.LoginHelper;
import vn.udn.dut.algoarena.port.domain.bo.CustomerBo;
import vn.udn.dut.algoarena.port.domain.vo.CustomerImportVo;
import vn.udn.dut.algoarena.port.domain.vo.CustomerVo;
import vn.udn.dut.algoarena.port.service.ICustomerService;
import vn.udn.dut.algoarena.system.domain.bo.SysUserBo;
import vn.udn.dut.algoarena.system.domain.vo.SysUserImportVo;
import vn.udn.dut.algoarena.system.domain.vo.SysUserVo;
import vn.udn.dut.algoarena.system.service.ISysConfigService;
import vn.udn.dut.algoarena.system.service.ISysUserService;

import java.util.List;

/**
 * Customer-defined import
 *
 * @author HoaLD
 */
@Slf4j
public class CustomerImportListener extends AnalysisEventListener<CustomerImportVo> implements ExcelListener<CustomerImportVo> {

    private final ICustomerService userService;

    private final String password;

    private final Boolean isUpdateSupport;

    private final Long operUserId;

    private int successNum = 0;
    private int failureNum = 0;
    private final StringBuilder successMsg = new StringBuilder();
    private final StringBuilder failureMsg = new StringBuilder();

    public CustomerImportListener(Boolean isUpdateSupport) {
        String initPassword = SpringUtils.getBean(ISysConfigService.class).selectConfigByKey("sys.user.initPassword");
        this.userService = SpringUtils.getBean(ICustomerService.class);
        this.password = BCrypt.hashpw(initPassword);
        this.isUpdateSupport = isUpdateSupport;
        this.operUserId = LoginHelper.getUserId();
    }

    @Override
    public void invoke(CustomerImportVo userVo, AnalysisContext context) {
        CustomerBo customerBo = new CustomerBo();
        customerBo.setUserName(userVo.getUserName());
        List<CustomerVo> sysUser = this.userService.queryList(customerBo);
        try {
            // Verify that the user exists
            if (ObjectUtil.isNull(sysUser) || sysUser.isEmpty()) {
                CustomerBo user = BeanUtil.toBean(userVo, CustomerBo.class);
                ValidatorUtils.validate(user);
                user.setPassword(password);
                user.setCreateBy(operUserId);
                userService.insertByBo(user);
                successNum++;
                successMsg.append("<br/>").append(successNum).append(", account ").append(user.getUserName()).append(" imported successfully");
            }
        } catch (Exception e) {
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

    }

    @Override
    public ExcelResult<CustomerImportVo> getExcelResult() {
        return new ExcelResult<>() {

            @Override
            public String getAnalysis() {
                if (failureNum > 0) {
                    failureMsg.insert(0, "Sorry, the import failed! A total of " + failureNum + " data format is incorrect, the error is as follows: ");
                    throw new ServiceException(failureMsg.toString());
                } else {
                    successMsg.insert(0, "Congratulations, all the data has been imported successfully! There are " + successNum + " items in total, and the data is as follows: ");
                }
                return successMsg.toString();
            }

            @Override
            public List<CustomerImportVo> getList() {
                return null;
            }

            @Override
            public List<String> getErrorList() {
                return null;
            }
        };
    }
}
