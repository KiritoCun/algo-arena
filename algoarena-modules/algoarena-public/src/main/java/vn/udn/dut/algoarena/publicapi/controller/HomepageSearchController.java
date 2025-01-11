package vn.udn.dut.algoarena.publicapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vn.udn.dut.algoarena.common.mybatis.core.page.PageQuery;
import vn.udn.dut.algoarena.common.mybatis.core.page.TableDataInfo;
import vn.udn.dut.algoarena.common.satoken.utils.LoginHelper;
import vn.udn.dut.algoarena.common.web.core.BaseController;
import vn.udn.dut.algoarena.port.domain.bo.ProblemBo;
import vn.udn.dut.algoarena.port.domain.bo.ProblemFunctionSignatureBo;
import vn.udn.dut.algoarena.port.domain.bo.SubmissionBo;
import vn.udn.dut.algoarena.port.domain.bo.TestcaseBo;
import vn.udn.dut.algoarena.port.domain.vo.ProblemFunctionSignatureVo;
import vn.udn.dut.algoarena.port.domain.vo.ProblemVo;
import vn.udn.dut.algoarena.port.domain.vo.SubmissionVo;
import vn.udn.dut.algoarena.port.domain.vo.TestcaseVo;
import vn.udn.dut.algoarena.port.service.*;
import vn.udn.dut.algoarena.publicapi.service.GPTService;
import vn.udn.dut.algoarena.publicapi.service.HomepageSearchService2;

import java.util.List;
import java.util.Map;

/**
 * Homepage api
 *
 * @author HOALD
 * @date 2023-11-11
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/homepage/search")
public class HomepageSearchController extends BaseController {
    private final IProblemService problemService;
    private final ITestcaseService testcaseService;
    private final IProblemFunctionSignatureService problemFunctionSignatureService;
    private final ISubmissionService submissionService;
    private final GPTService gptService;

    @GetMapping("/problem")
    public TableDataInfo<ProblemVo> publicProblemList(ProblemBo bo, PageQuery pageQuery) {
        return problemService.queryPagePublicList(bo, pageQuery);
    }

    @GetMapping("/testcase/{keyPath}")
    public List<TestcaseVo> publicTestcaseList(@PathVariable String keyPath) {
        ProblemBo problemBo = new ProblemBo();
        problemBo.setKeyPath(keyPath);

        ProblemVo problemVo = problemService.queryPublicList(problemBo).get(0);

        TestcaseBo bo = new TestcaseBo();
        bo.setProblemId(problemVo.getId());
        return testcaseService.queryList(bo);
    }

    @GetMapping("/problem-function-signature/{keyPath}/{userId}")
    public List<ProblemFunctionSignatureVo> publicProblemFunctionSignatureList(
            @PathVariable String keyPath, @PathVariable Long userId) {
        ProblemFunctionSignatureBo bo = new ProblemFunctionSignatureBo();
        bo.setKeyPath(keyPath);

        return problemFunctionSignatureService.queryList(bo);
    }

    @PostMapping("/submit-solution")
    public List<String> submitSolution(@RequestBody Map<String, String> request) {
        String submittedCode = request.get("submittedCode");
        String keyPath = request.get("problemId");
        String language = request.get("language");
        String version = request.get("version");
        long userId = Long.parseLong(request.get("userId"));

        ProblemBo problemBo = new ProblemBo();
        problemBo.setKeyPath(keyPath);
        List<ProblemVo> problemVoList = problemService.queryPublicList(problemBo);
        ProblemVo problemVo = problemVoList.get(0);
        TestcaseBo testcaseBo = new TestcaseBo();
        testcaseBo.setProblemId(problemVo.getId());
        List<TestcaseVo> testcaseList = testcaseService.queryList(testcaseBo);

        List<String> testcaseResult = HomepageSearchService2.submitSolution(submittedCode, language, version, testcaseList);

        // Handle save solution
        boolean passedSolution = true;
        for (String result : testcaseResult) {
            if ("false".equals(result.toLowerCase())) {
                passedSolution = false;
            }
        }

        if (passedSolution) {
            SubmissionBo submissionBo = new SubmissionBo();
            submissionBo.setProblemId(problemVo.getId());
            submissionBo.setUserId(userId);
            submissionBo.setCode(submittedCode);
            submissionBo.setProgramingLanguage(language);
            submissionService.insertByBo(submissionBo);
        }
        return testcaseResult;
    }

    @PostMapping("/run-solution")
    public List<String> runSolution(@RequestBody Map<String, String> request) {
        String submittedCode = request.get("submittedCode");
        String keyPath = request.get("problemId");
        String language = request.get("language");
        String version = request.get("version");

        ProblemBo problemBo = new ProblemBo();
        problemBo.setKeyPath(keyPath);
        List<ProblemVo> problemVoList = problemService.queryPublicList(problemBo);
        ProblemVo problemVo = problemVoList.get(0);
        TestcaseBo testcaseBo = new TestcaseBo();
        testcaseBo.setProblemId(problemVo.getId());
        testcaseBo.setIsHidden(0);
        List<TestcaseVo> testcaseList = testcaseService.queryList(testcaseBo);

        return HomepageSearchService2.submitSolution(submittedCode, language, version, testcaseList);
    }

    @PostMapping("/run-solution1")
    public String getGptResponse(@RequestBody Map<String, String> request) {
        String submittedCode = request.get("submittedCode");
        String keyPath = request.get("problemId");
        String language = request.get("language");
        String version = request.get("version");

        ProblemBo problemBo = new ProblemBo();
        problemBo.setKeyPath(keyPath);
        List<ProblemVo> problemVoList = problemService.queryPublicList(problemBo);
        ProblemVo problemVo = problemVoList.get(0);
        TestcaseBo testcaseBo = new TestcaseBo();
        testcaseBo.setProblemId(problemVo.getId());
        testcaseBo.setIsHidden(0);
        List<TestcaseVo> testcaseList = testcaseService.queryList(testcaseBo);

        return gptService.getGPTResponse(submittedCode);
    }
}
