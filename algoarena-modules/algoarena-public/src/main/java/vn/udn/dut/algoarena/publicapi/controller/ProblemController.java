package vn.udn.dut.algoarena.publicapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.udn.dut.algoarena.common.core.domain.R;
import vn.udn.dut.algoarena.common.mybatis.core.page.PageQuery;
import vn.udn.dut.algoarena.common.mybatis.core.page.TableDataInfo;
import vn.udn.dut.algoarena.common.web.core.BaseController;
import vn.udn.dut.algoarena.port.domain.bo.*;
import vn.udn.dut.algoarena.port.domain.vo.*;
import vn.udn.dut.algoarena.port.service.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Homepage api
 *
 * @author HOALD
 * @date 2023-12-07
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/problem")
public class ProblemController extends BaseController {

	private final IProblemService problemService;

	@GetMapping
	public TableDataInfo<ProblemVo> publicList(ProblemBo bo, PageQuery pageQuery) {
		return problemService.queryPagePublicList(bo, pageQuery);
	}
}
