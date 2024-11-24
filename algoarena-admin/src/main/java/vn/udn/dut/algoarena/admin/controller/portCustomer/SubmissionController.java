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
import vn.udn.dut.algoarena.port.domain.bo.SubmissionBo;
import vn.udn.dut.algoarena.port.domain.vo.SubmissionVo;
import vn.udn.dut.algoarena.port.service.ISubmissionService;

import java.util.List;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/portCustomer/submission")
public class SubmissionController extends BaseController {
	private final ISubmissionService submissionService;
	
	@SaCheckPermission("portCustomer:submission:list")
	@GetMapping("/list")
	public TableDataInfo<SubmissionVo> list(SubmissionBo bo, PageQuery pageQuery) {
		return submissionService.queryPageList(bo, pageQuery);
	}
	
	@SaCheckPermission("portCustomer:submission:query")
	@GetMapping(value = { "/", "/{id}" })
	public R<SubmissionVo> getInfo(@PathVariable(value = "id", required = false) Long id) {
		return R.ok(submissionService.queryById(id));
	}
	
	@SaCheckPermission("portCustomer:submission:add")
	@Log(title = "Submission", businessType = BusinessType.INSERT)
	@RepeatSubmit()
	@PostMapping()
	public R<Void> add(@Validated(AddGroup.class) @RequestBody SubmissionBo bo) {
		return toAjax(submissionService.insertByBo(bo));
	}
	
	@SaCheckPermission("portCustomer:submission:edit")
	@Log(title = "Submission", businessType = BusinessType.UPDATE)
	@RepeatSubmit()
	@PutMapping()
	public R<Void> edit(@Validated(EditGroup.class) @RequestBody SubmissionBo bo) {
		return toAjax(submissionService.updateByBo(bo));
	}
	
	@SaCheckPermission("portCustomer:submission:remove")
	@Log(title = "Submission", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
	public R<Void> remove(@NotEmpty(message = "Primary key cannot be empty") @PathVariable Long[] ids) {
		return toAjax(submissionService.deleteWithValidByIds(List.of(ids), true));
	}
}
