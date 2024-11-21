package vn.udn.dut.algoarena.port.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import vn.udn.dut.algoarena.port.domain.Problem;
import vn.udn.dut.algoarena.port.domain.Submission;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * Submission view object
 *
 * @author HoaLD
 * @date 2024-11-20
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = Submission.class)
public class SubmissionVo implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	private String tenantId;

	private Long id;

	private Long examId;

	private Long problemId;

	private Long userId;

	private String code;

	private Integer status;

	private Integer score;

	private String remark;

	private Date createTime;
}
