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
import vn.udn.dut.algoarena.port.domain.bo.ProblemFunctionSignatureBo;
import vn.udn.dut.algoarena.port.domain.vo.ProblemFunctionSignatureVo;
import vn.udn.dut.algoarena.port.service.IBookingService;
import vn.udn.dut.algoarena.port.service.IProblemFunctionSignatureService;

import java.util.List;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/portCustomer/functionDefinition")
public class FunctionDefinitionController extends BaseController{
	private final IProblemFunctionSignatureService functionDefinitionService;
	
	private final IBookingService bookingService;
	
	@SaCheckPermission("portCustomer:problem:list")
	@GetMapping("/list")
	public TableDataInfo<ProblemFunctionSignatureVo> list(ProblemFunctionSignatureBo bo, PageQuery pageQuery) {
		return functionDefinitionService.queryPageList(bo, pageQuery);
	}

	@SaCheckPermission("portCustomer:problem:query")
	@GetMapping(value = { "/", "/{id}" })
	public R<ProblemFunctionSignatureVo> getInfo(@PathVariable(value = "id", required = false) Long id) {
		return R.ok(functionDefinitionService.queryById(id));
	}
	
	@SaCheckPermission("portCustomer:problem:add")
	@Log(title = "ProblemFunctionSignature", businessType = BusinessType.INSERT)
	@RepeatSubmit()
	@PostMapping()
	public R<Void> add(@Validated(AddGroup.class) @RequestBody ProblemFunctionSignatureBo bo) {
		return toAjax(functionDefinitionService.insertByBo(bo));
	}

	@SaCheckPermission("portCustomer:problem:edit")
	@Log(title = "ProblemFunctionSignature", businessType = BusinessType.UPDATE)
	@RepeatSubmit()
	@PutMapping()
	public R<Void> edit(@Validated(EditGroup.class) @RequestBody ProblemFunctionSignatureBo bo) {
		return toAjax(functionDefinitionService.updateByBo(bo));
	}

	@SaCheckPermission("portCustomer:problem:remove")
	@Log(title = "ProblemFunctionSignature", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
	public R<Void> remove(@NotEmpty(message = "Primary key cannot be empty") @PathVariable Long[] ids) {
		return toAjax(functionDefinitionService.deleteWithValidByIds(List.of(ids), true));
	}
}
