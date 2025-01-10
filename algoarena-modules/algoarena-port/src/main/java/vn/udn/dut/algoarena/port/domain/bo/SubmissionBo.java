package vn.udn.dut.algoarena.port.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import vn.udn.dut.algoarena.port.domain.Problem;
import vn.udn.dut.algoarena.port.domain.Submission;

import java.util.Date;

/**
 * Submission business object
 *
 * @author HoaLD
 * @date 2024-11-20
 */
@Data
@AutoMapper(target = Submission.class, reverseConvertGenerate = false)
public class SubmissionBo {

	private String tenantId;

	private Long id;

	private Long examId;

	private Long problemId;

	private Long userId;

	private String programingLanguage;

	private String code;

	private Integer status;

	private Integer score;

	private String remark;

	private Date createTime;
}
