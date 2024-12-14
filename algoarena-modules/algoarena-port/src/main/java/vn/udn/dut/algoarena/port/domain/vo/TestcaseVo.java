package vn.udn.dut.algoarena.port.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import vn.udn.dut.algoarena.port.domain.Problem;
import vn.udn.dut.algoarena.port.domain.Testcase;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * Testcase view object
 *
 * @author HoaLD
 * @date 2024-11-20
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = Testcase.class)
public class TestcaseVo implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	private String tenantId;

	private Long id;

	private Long problemId;

	private String testcaseJson;

	private String illustration;

	private String explanation;

	private Long ossId;

	private Integer isHidden;

	private String remark;

	private Date createTime;
}
