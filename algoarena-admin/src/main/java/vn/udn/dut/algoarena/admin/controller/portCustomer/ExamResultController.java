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
import vn.udn.dut.algoarena.port.domain.bo.ExamResultBo;
import vn.udn.dut.algoarena.port.domain.vo.ExamResultVo;
import vn.udn.dut.algoarena.port.service.IExamResultService;

import java.util.List;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/portCustomer/examResult")
public class ExamResultController extends BaseController {
	private final IExamResultService examResultService;
	
	@SaCheckPermission("portCustomer:examResult:list")
	@GetMapping("/list")
	public TableDataInfo<ExamResultVo> list(ExamResultBo bo, PageQuery pageQuery) {
		return examResultService.queryPageList(bo, pageQuery);
	}
	
	@SaCheckPermission("portCustomer:examResult:query")
	@GetMapping(value = { "/", "/{id}" })
	public R<ExamResultVo> getInfo(@PathVariable(value = "id", required = false) Long id) {
		return R.ok(examResultService.queryById(id));
	}
	
	@SaCheckPermission("portCustomer:examResult:add")
	@Log(title = "ExamResult", businessType = BusinessType.INSERT)
	@RepeatSubmit()
	@PostMapping()
	public R<Void> add(@Validated(AddGroup.class) @RequestBody ExamResultBo bo) {
		return toAjax(examResultService.insertByBo(bo));
	}
	
	@SaCheckPermission("portCustomer:examResult:edit")
	@Log(title = "ExamResult", businessType = BusinessType.UPDATE)
	@RepeatSubmit()
	@PutMapping()
	public R<Void> edit(@Validated(EditGroup.class) @RequestBody ExamResultBo bo) {
		return toAjax(examResultService.updateByBo(bo));
	}
	
	@SaCheckPermission("portCustomer:examResult:remove")
	@Log(title = "ExamResult", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
	public R<Void> remove(@NotEmpty(message = "Primary key cannot be empty") @PathVariable Long[] ids) {
		return toAjax(examResultService.deleteWithValidByIds(List.of(ids), true));
	}
}
