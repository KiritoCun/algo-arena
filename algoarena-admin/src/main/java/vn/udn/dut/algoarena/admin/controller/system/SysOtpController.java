package vn.udn.dut.algoarena.admin.controller.system;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.dev33.satoken.annotation.SaCheckPermission;
import lombok.RequiredArgsConstructor;
import vn.udn.dut.algoarena.common.mybatis.core.page.PageQuery;
import vn.udn.dut.algoarena.common.mybatis.core.page.TableDataInfo;
import vn.udn.dut.algoarena.common.web.core.BaseController;
import vn.udn.dut.algoarena.system.domain.bo.SysOtpBo;
import vn.udn.dut.algoarena.system.domain.vo.SysOtpVo;
import vn.udn.dut.algoarena.system.service.ISysOtpService;

/**
 * System otp
 *
 * @author HoaLD
 * @date 2023-10-23
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/portSearch/smsOtp")
public class SysOtpController extends BaseController {

    private final ISysOtpService sysOtpService;

    /**
     * Query System otp list
     */
    @SaCheckPermission("portSearch:smsOtp:list")
    @GetMapping("/list")
    public TableDataInfo<SysOtpVo> list(SysOtpBo bo, PageQuery pageQuery) {
        return sysOtpService.queryPageList(bo, pageQuery);
    }
}
