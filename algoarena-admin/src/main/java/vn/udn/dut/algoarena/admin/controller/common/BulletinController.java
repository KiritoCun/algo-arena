package vn.udn.dut.algoarena.admin.controller.common;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.dev33.satoken.annotation.SaIgnore;
import lombok.RequiredArgsConstructor;
import vn.udn.dut.algoarena.common.mybatis.core.page.PageQuery;
import vn.udn.dut.algoarena.common.mybatis.core.page.TableDataInfo;
import vn.udn.dut.algoarena.system.constant.SystemConstants;
import vn.udn.dut.algoarena.system.domain.bo.SysNoticeBo;
import vn.udn.dut.algoarena.system.domain.vo.SysNoticeVo;
import vn.udn.dut.algoarena.system.service.ISysNoticeService;

/**
 * Front page
 *
 * @author HoaLD
 */
@SaIgnore
@RequiredArgsConstructor
@RestController
@RequestMapping("/bulletin")
public class BulletinController {

    private final ISysNoticeService noticeService;

    
    @GetMapping("/list")
    public TableDataInfo<SysNoticeVo> list(SysNoticeBo bo, PageQuery pageQuery) {
    	noticeService.addFilterNoticeType(bo, SystemConstants.NOTICE_TYPE_VICT);
    	bo.setStatus(SystemConstants.NOTICE_STATUS_ENABLE);
        return noticeService.selectPageNoticeList(bo, pageQuery);
    }
}
