package vn.udn.dut.algoarena.port.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import vn.udn.dut.algoarena.port.domain.Testcase;

import java.util.Date;

/**
 * Testcase business object
 *
 * @author HoaLD
 * @date 2024-11-20
 */
@Data
@AutoMapper(target = Testcase.class, reverseConvertGenerate = false)
public class TestcaseBo {

	private String tenantId;

	private Long id;

	private Long problemId;

	private String inputData;

	private String expectedOutput;

	private String illustration;

	private String explanation;

	private Long ossId;

	private Integer isHidden;

	private String remark;

	private Date createTime;
}
