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
import vn.udn.dut.algoarena.port.domain.bo.ProblemBo;
import vn.udn.dut.algoarena.port.domain.vo.ProblemVo;
import vn.udn.dut.algoarena.port.service.IProblemService;

import java.util.List;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("portCustomer/problem")
public class ProblemController extends BaseController{
	private final IProblemService problemService;
	
	@SaCheckPermission("portCustomer:problem:list")
	@GetMapping("/list")
	public TableDataInfo<ProblemVo> list(ProblemBo bo, PageQuery pageQuery) {
		return problemService.queryPageList(bo, pageQuery);
	}
	
	@SaCheckPermission("portCustomer:problem:query")
	@GetMapping(value = { "/", "/{id}" })
	public R<ProblemVo> getInfo(@PathVariable(value = "id", required = false) Long id) {
		return R.ok(problemService.queryById(id));
	}
	
	@SaCheckPermission("portCustomer:problem:add")
	@Log(title = "Problem", businessType = BusinessType.INSERT)
	@RepeatSubmit()
	@PostMapping()
	public R<Void> add(@Validated(AddGroup.class) @RequestBody ProblemBo bo) {
		return toAjax(problemService.insertByBo(bo));
	}
	
	@SaCheckPermission("portCustomer:problem:edit")
	@Log(title = "Problem", businessType = BusinessType.UPDATE)
	@RepeatSubmit()
	@PutMapping()
	public R<Void> edit(@Validated(EditGroup.class) @RequestBody ProblemBo bo) {
		return toAjax(problemService.updateByBo(bo));
	}
	
	@SaCheckPermission("portCustomer:problem:remove")
	@Log(title = "Problem", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
	public R<Void> remove(@NotEmpty(message = "Primary key cannot be empty") @PathVariable Long[] ids) {
		return toAjax(problemService.deleteWithValidByIds(List.of(ids), true));
	}
}
