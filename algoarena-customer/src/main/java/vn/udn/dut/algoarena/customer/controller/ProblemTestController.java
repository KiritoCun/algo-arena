package vn.udn.dut.algoarena.customer.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.udn.dut.algoarena.common.mybatis.core.page.PageQuery;
import vn.udn.dut.algoarena.common.mybatis.core.page.TableDataInfo;
import vn.udn.dut.algoarena.port.domain.bo.*;
import vn.udn.dut.algoarena.port.domain.vo.*;
import vn.udn.dut.algoarena.port.service.*;

/**
 * Booking controller
 *
 * @author HoaLD
 */
@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/problem/test")
public class ProblemTestController {

	private final IProblemService problemService;

	@GetMapping
	public TableDataInfo<ProblemVo> publicList(ProblemBo bo, PageQuery pageQuery) {
		return problemService.queryPagePublicList(bo, pageQuery);
	}
}
