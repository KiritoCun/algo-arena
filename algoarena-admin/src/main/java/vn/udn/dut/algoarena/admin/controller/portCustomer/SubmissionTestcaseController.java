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
import vn.udn.dut.algoarena.port.domain.bo.SubmissionTestcaseBo;
import vn.udn.dut.algoarena.port.domain.vo.SubmissionTestcaseVo;
import vn.udn.dut.algoarena.port.service.ISubmissionTestcaseService;

import java.util.List;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/portCustomer/submissionTestcase")
public class SubmissionTestcaseController extends BaseController {
	private final ISubmissionTestcaseService submissionTestcaseService;
	
	@SaCheckPermission("portCustomer:submission:list")
	@GetMapping("/list")
	public TableDataInfo<SubmissionTestcaseVo> list(SubmissionTestcaseBo bo, PageQuery pageQuery) {
		return submissionTestcaseService.queryPageList(bo, pageQuery);
	}
	
	@SaCheckPermission("portCustomer:submission:query")
	@GetMapping(value = { "/", "/{id}" })
	public R<SubmissionTestcaseVo> getInfo(@PathVariable(value = "id", required = false) Long id) {
		return R.ok(submissionTestcaseService.queryById(id));
	}
	
	@SaCheckPermission("portCustomer:submission:add")
	@Log(title = "SubmissionTestcase", businessType = BusinessType.INSERT)
	@RepeatSubmit()
	@PostMapping()
	public R<Void> add(@Validated(AddGroup.class) @RequestBody SubmissionTestcaseBo bo) {
		return toAjax(submissionTestcaseService.insertByBo(bo));
	}
	
	@SaCheckPermission("portCustomer:submission:edit")
	@Log(title = "SubmissionTestcase", businessType = BusinessType.UPDATE)
	@RepeatSubmit()
	@PutMapping()
	public R<Void> edit(@Validated(EditGroup.class) @RequestBody SubmissionTestcaseBo bo) {
		return toAjax(submissionTestcaseService.updateByBo(bo));
	}
	
	@SaCheckPermission("portCustomer:submission:remove")
	@Log(title = "SubmissionTestcase", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
	public R<Void> remove(@NotEmpty(message = "Primary key cannot be empty") @PathVariable Long[] ids) {
		return toAjax(submissionTestcaseService.deleteWithValidByIds(List.of(ids), true));
	}
}
