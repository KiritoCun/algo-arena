package vn.udn.dut.algoarena.publicapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vn.udn.dut.algoarena.common.mybatis.core.page.PageQuery;
import vn.udn.dut.algoarena.common.mybatis.core.page.TableDataInfo;
import vn.udn.dut.algoarena.common.web.core.BaseController;
import vn.udn.dut.algoarena.port.domain.bo.ProblemBo;
import vn.udn.dut.algoarena.port.domain.bo.ProblemFunctionSignatureBo;
import vn.udn.dut.algoarena.port.domain.bo.TestcaseBo;
import vn.udn.dut.algoarena.port.domain.vo.ProblemFunctionSignatureVo;
import vn.udn.dut.algoarena.port.domain.vo.ProblemVo;
import vn.udn.dut.algoarena.port.domain.vo.TestcaseVo;
import vn.udn.dut.algoarena.port.service.*;
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

    private final ISlideService slideService;
    private final IAlgoArenaService algoarenaService;
    private final IVnpHistoryService vnpHistoryService;
    private final IBookingService bookingService;
    private final IBookingDetailService bookingDetailService;

    private final IProblemService problemService;
    private final ITestcaseService testcaseService;
    private final IProblemFunctionSignatureService problemFunctionSignatureService;

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

    @GetMapping("/problem-function-signature/{keyPath}")
    public List<ProblemFunctionSignatureVo> publicProblemFunctionSignatureList(@PathVariable String keyPath) {
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

        ProblemBo problemBo = new ProblemBo();
        problemBo.setKeyPath(keyPath);
        List<ProblemVo> problemVoList = problemService.queryPublicList(problemBo);
        ProblemVo problemVo = problemVoList.get(0);
        TestcaseBo testcaseBo = new TestcaseBo();
        testcaseBo.setProblemId(problemVo.getId());
        List<TestcaseVo> testcaseList = testcaseService.queryList(testcaseBo);

        return HomepageSearchService2.submitSolution(submittedCode, language, version, testcaseList);
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
}
