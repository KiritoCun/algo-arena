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
import vn.udn.dut.algoarena.port.domain.bo.ExamResultDetailBo;
import vn.udn.dut.algoarena.port.domain.vo.ExamResultDetailVo;
import vn.udn.dut.algoarena.port.service.IExamResultDetailService;

import java.util.List;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/portCustomer/examResultDetail")
public class ExamResultDetailController extends BaseController {
	private final IExamResultDetailService examResultDetailService;
	
	@SaCheckPermission("portCustomer:examResult:list")
	@GetMapping("/list")
	public TableDataInfo<ExamResultDetailVo> list(ExamResultDetailBo bo, PageQuery pageQuery) {
		return examResultDetailService.queryPageList(bo, pageQuery);
	}
	
	@SaCheckPermission("portCustomer:examResult:query")
	@GetMapping(value = { "/", "/{id}" })
	public R<ExamResultDetailVo> getInfo(@PathVariable(value = "id", required = false) Long id) {
		return R.ok(examResultDetailService.queryById(id));
	}
	
	@SaCheckPermission("portCustomer:examResult:add")
	@Log(title = "ExamResultDetail", businessType = BusinessType.INSERT)
	@RepeatSubmit()
	@PostMapping()
	public R<Void> add(@Validated(AddGroup.class) @RequestBody ExamResultDetailBo bo) {
		return toAjax(examResultDetailService.insertByBo(bo));
	}
	
	@SaCheckPermission("portCustomer:examResult:edit")
	@Log(title = "ExamResultDetail", businessType = BusinessType.UPDATE)
	@RepeatSubmit()
	@PutMapping()
	public R<Void> edit(@Validated(EditGroup.class) @RequestBody ExamResultDetailBo bo) {
		return toAjax(examResultDetailService.updateByBo(bo));
	}
	
	@SaCheckPermission("portCustomer:examResult:remove")
	@Log(title = "ExamResultDetail", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
	public R<Void> remove(@NotEmpty(message = "Primary key cannot be empty") @PathVariable Long[] ids) {
		return toAjax(examResultDetailService.deleteWithValidByIds(List.of(ids), true));
	}
}
