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
import vn.udn.dut.algoarena.port.domain.bo.ExamProblemBo;
import vn.udn.dut.algoarena.port.domain.vo.ExamProblemVo;
import vn.udn.dut.algoarena.port.service.IExamProblemService;

import java.util.List;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/portCustomer/examProblem")
public class ExamProblemController extends BaseController {
	private final IExamProblemService examProblemService;
	
	@SaCheckPermission("portCustomer:examProblem:list")
	@GetMapping("/list")
	public TableDataInfo<ExamProblemVo> list(ExamProblemBo bo, PageQuery pageQuery) {
		return examProblemService.queryPageList(bo, pageQuery);
	}
	
	@SaCheckPermission("portCustomer:examProblem:query")
	@GetMapping(value = { "/", "/{id}" })
	public R<ExamProblemVo> getInfo(@PathVariable(value = "id", required = false) Long id) {
		return R.ok(examProblemService.queryById(id));
	}
	
	@SaCheckPermission("portCustomer:examProblem:add")
	@Log(title = "ExamProblem", businessType = BusinessType.INSERT)
	@RepeatSubmit()
	@PostMapping()
	public R<Void> add(@Validated(AddGroup.class) @RequestBody ExamProblemBo bo) {
		return toAjax(examProblemService.insertByBo(bo));
	}
	
	@SaCheckPermission("portCustomer:examProblem:edit")
	@Log(title = "ExamProblem", businessType = BusinessType.UPDATE)
	@RepeatSubmit()
	@PutMapping()
	public R<Void> edit(@Validated(EditGroup.class) @RequestBody ExamProblemBo bo) {
		return toAjax(examProblemService.updateByBo(bo));
	}
	
	@SaCheckPermission("portCustomer:examProblem:remove")
	@Log(title = "ExamProblem", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
	public R<Void> remove(@NotEmpty(message = "Primary key cannot be empty") @PathVariable Long[] ids) {
		return toAjax(examProblemService.deleteWithValidByIds(List.of(ids), true));
	}
}
