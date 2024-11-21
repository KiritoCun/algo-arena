package vn.udn.dut.algoarena.admin.controller.portCustomer;

import cn.dev33.satoken.annotation.SaCheckPermission;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vn.udn.dut.algoarena.common.core.domain.R;
import vn.udn.dut.algoarena.common.core.validate.AddGroup;
import vn.udn.dut.algoarena.common.core.validate.EditGroup;
import vn.udn.dut.algoarena.common.idempotent.annotation.RepeatSubmit;
import vn.udn.dut.algoarena.common.log.annotation.Log;
import vn.udn.dut.algoarena.common.log.enums.BusinessType;
import vn.udn.dut.algoarena.common.mybatis.core.page.PageQuery;
import vn.udn.dut.algoarena.common.mybatis.core.page.TableDataInfo;
import vn.udn.dut.algoarena.common.web.core.BaseController;
import vn.udn.dut.algoarena.port.domain.bo.TestcaseBo;
import vn.udn.dut.algoarena.port.domain.vo.TestcaseVo;
import vn.udn.dut.algoarena.port.service.ITestcaseService;
import vn.udn.dut.algoarena.port.service.IBookingService;

import java.util.List;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/portCustomer/testcase")
public class TestcaseController extends BaseController{
	private final ITestcaseService testcaseService;
	
	private final IBookingService bookingService;
	
	@SaCheckPermission("portCustomer:testcase:list")
	@GetMapping("/list")
	public TableDataInfo<TestcaseVo> list(TestcaseBo bo, PageQuery pageQuery) {
		return testcaseService.queryPageList(bo, pageQuery);
	}

	@SaCheckPermission("portCustomer:testcase:query")
	@GetMapping(value = { "/", "/{id}" })
	public R<TestcaseVo> getInfo(@PathVariable(value = "id", required = false) Long id) {
		return R.ok(testcaseService.queryById(id));
	}
	
	@SaCheckPermission("portCustomer:testcase:add")
	@Log(title = "Testcase", businessType = BusinessType.INSERT)
	@RepeatSubmit()
	@PostMapping()
	public R<Void> add(@Validated(AddGroup.class) @RequestBody TestcaseBo bo) {
		return toAjax(testcaseService.insertByBo(bo));
	}

	@SaCheckPermission("portCustomer:testcase:edit")
	@Log(title = "Testcase", businessType = BusinessType.UPDATE)
	@RepeatSubmit()
	@PutMapping()
	public R<Void> edit(@Validated(EditGroup.class) @RequestBody TestcaseBo bo) {
		return toAjax(testcaseService.updateByBo(bo));
	}

	@SaCheckPermission("portCustomer:testcase:remove")
	@Log(title = "Testcase", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
	public R<Void> remove(@NotEmpty(message = "Primary key cannot be empty") @PathVariable Long[] ids) {
		return toAjax(testcaseService.deleteWithValidByIds(List.of(ids), true));
	}
}
