package vn.udn.dut.algoarena.port.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import vn.udn.dut.algoarena.port.domain.ProblemFunctionSignature;
import vn.udn.dut.algoarena.port.domain.Testcase;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * ProblemFunctionSignature view object
 *
 * @author HoaLD
 * @date 2024-12-25
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = ProblemFunctionSignature.class)
public class ProblemFunctionSignatureVo implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	private String tenantId;

	private Long id;

	private Long problemId;

	private String keyPath;

	private String language;

	private String functionSignature;

	private String remark;

	private Date createTime;

	private String code;
}
