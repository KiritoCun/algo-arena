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
import vn.udn.dut.algoarena.port.domain.bo.ProblemFunctionSignatureBo;
import vn.udn.dut.algoarena.port.domain.bo.TestcaseBo;
import vn.udn.dut.algoarena.port.domain.vo.ProblemVo;
import vn.udn.dut.algoarena.port.service.IProblemFunctionSignatureService;
import vn.udn.dut.algoarena.port.service.IProblemService;
import vn.udn.dut.algoarena.port.service.ITestcaseService;
import vn.udn.dut.algoarena.util.FunctionDefinitionInferer;

import java.util.List;
import java.util.Map;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("portCustomer/problem")
public class ProblemController extends BaseController{
	private final IProblemService problemService;

	private final ITestcaseService testcaseService;

	private final IProblemFunctionSignatureService problemFunctionSignatureService;

	@GetMapping("/public-list")
	public TableDataInfo<ProblemVo> publicList(ProblemBo bo, PageQuery pageQuery) {
		return problemService.queryPagePublicList(bo, pageQuery);
	}

	@GetMapping("/list")
	public TableDataInfo<ProblemVo> list(ProblemBo bo, PageQuery pageQuery) {
		return problemService.queryPageList(bo, pageQuery);
	}

	@GetMapping(value = { "/", "/{id}" })
	public R<ProblemVo> getInfo(@PathVariable(value = "id", required = false) Long id) {
		return R.ok(problemService.queryById(id));
	}

	@Log(title = "Problem", businessType = BusinessType.INSERT)
	@RepeatSubmit()
	@PostMapping()
	public R<Void> add(@Validated(AddGroup.class) @RequestBody ProblemBo bo) {
		problemService.insertByBo(bo);

		FunctionDefinitionInferer functionDefinitionInferer = new FunctionDefinitionInferer();

		Map<String, String> formattedFunctions = FunctionDefinitionInferer.formatFunctionForLanguages(bo.getFunctionDefinitionJava());
		formattedFunctions.forEach((language, defineFunction) -> {
			System.out.println("{language=" + language + ", defineFunction=" + defineFunction + "}");
			var problemFunctionSignatureBo = new ProblemFunctionSignatureBo();
			problemFunctionSignatureBo.setProblemId(bo.getId());
			problemFunctionSignatureBo.setLanguage(language);
			problemFunctionSignatureBo.setFunctionSignature(defineFunction);
			problemFunctionSignatureService.insertByBo(problemFunctionSignatureBo);
		});

		for (int i = 0; i < bo.getNumberTestcase(); i++) {
			var testcaseBo = new TestcaseBo();
			testcaseBo.setProblemId(bo.getId());
			testcaseService.insertByBo(testcaseBo);
		}

		return toAjax(true);
	}

	@Log(title = "Problem", businessType = BusinessType.UPDATE)
	@RepeatSubmit()
	@PutMapping()
	public R<Void> edit(@Validated(EditGroup.class) @RequestBody ProblemBo bo) {
		return toAjax(problemService.updateByBo(bo));
	}

	@Log(title = "Testcase Problem", businessType = BusinessType.UPDATE)
	@RepeatSubmit()
	@PutMapping("/{id}")
	public R<Void> editTestcase(@PathVariable Long id, @RequestBody List<TestcaseBo> testcaseList) {
		for(TestcaseBo testcase : testcaseList) {
			testcaseService.updateByBo(testcase);
		}
		return toAjax(true);
	}

	@Log(title = "Function Definition Problem", businessType = BusinessType.UPDATE)
	@RepeatSubmit()
	@PutMapping("/functionDefinition/{id}")
	public R<Void> editFunctionDefinition(@PathVariable Long id, @RequestBody List<ProblemFunctionSignatureBo> funtionSignatureList) {
		for(ProblemFunctionSignatureBo functionSignature : funtionSignatureList) {
			problemFunctionSignatureService.updateByBo(functionSignature);
		}
		return toAjax(true);
	}

	@Log(title = "Problem", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
	public R<Void> remove(@NotEmpty(message = "Primary key cannot be empty") @PathVariable Long[] ids) {
		return toAjax(problemService.deleteWithValidByIds(List.of(ids), true));
	}
}
