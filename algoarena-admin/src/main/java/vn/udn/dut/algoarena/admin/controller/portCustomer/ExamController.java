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
import vn.udn.dut.algoarena.port.domain.bo.ExamBo;
import vn.udn.dut.algoarena.port.domain.bo.ProblemBo;
import vn.udn.dut.algoarena.port.domain.vo.ExamVo;
import vn.udn.dut.algoarena.port.service.IExamService;
import vn.udn.dut.algoarena.port.service.IProblemService;

import java.util.List;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/portCustomer/exam")
public class ExamController extends BaseController {
	private final IExamService examService;
	private final IProblemService problemService;
	
	@SaCheckPermission("portCustomer:exam:list")
	@GetMapping("/list")
	public TableDataInfo<ExamVo> list(ExamBo bo, PageQuery pageQuery) {
		return examService.queryPageList(bo, pageQuery);
	}
	
	@SaCheckPermission("portCustomer:exam:query")
	@GetMapping(value = { "/", "/{id}" })
	public R<ExamVo> getInfo(@PathVariable(value = "id", required = false) Long id) {
		return R.ok(examService.queryById(id));
	}
	
	@SaCheckPermission("portCustomer:exam:add")
	@Log(title = "Exam", businessType = BusinessType.INSERT)
	@RepeatSubmit()
	@PostMapping()
	public R<Void> add(@Validated(AddGroup.class) @RequestBody ExamBo bo) {
		return toAjax(examService.insertByBo(bo));
	}
	
	@SaCheckPermission("portCustomer:exam:edit")
	@Log(title = "Exam", businessType = BusinessType.UPDATE)
	@RepeatSubmit()
	@PutMapping()
	public R<Void> edit(@Validated(EditGroup.class) @RequestBody ExamBo bo) {
		return toAjax(examService.updateByBo(bo));
	}

	@SaCheckPermission("portCustomer:exam:edit")
	@Log(title = "Exam", businessType = BusinessType.UPDATE)
	@RepeatSubmit()
	@PutMapping("/{id}")
	public R<Void> editProblem(@PathVariable Long id, @RequestBody List<ProblemBo> problemList) {
		for(ProblemBo problemBo : problemList){
			problemService.updateByBo(problemBo);
		}
		return toAjax(true);
	}
	
	@SaCheckPermission("portCustomer:exam:remove")
	@Log(title = "Exam", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
	public R<Void> remove(@NotEmpty(message = "Primary key cannot be empty") @PathVariable Long[] ids) {
		return toAjax(examService.deleteWithValidByIds(List.of(ids), true));
	}
}
