package vn.udn.dut.algoarena.port.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import vn.udn.dut.algoarena.port.domain.ProblemFunctionSignature;
import vn.udn.dut.algoarena.port.domain.Testcase;

import java.util.Date;

/**
 * ProblemFunctionSignature business object
 *
 * @author HoaLD
 * @date 2024-12-25
 */
@Data
@AutoMapper(target = ProblemFunctionSignature.class, reverseConvertGenerate = false)
public class ProblemFunctionSignatureBo {

	private String tenantId;

	private Long id;

	private Long problemId;

	private String keyPath;

	private String language;

	private String functionSignature;

	private String remark;

	private Date createTime;

	private Long userId;
}
