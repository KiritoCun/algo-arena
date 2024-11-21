package vn.udn.dut.algoarena.port.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import vn.udn.dut.algoarena.port.domain.ExamResult;
import vn.udn.dut.algoarena.port.domain.Problem;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * Exam result view object
 *
 * @author HoaLD
 * @date 2024-11-20
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = ExamResult.class)
public class ExamResultVo implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	private String tenantId;

	private Long id;

	private Long examId;

	private Long userId;

	private Integer totalScore;

	private Date completedAt;

	private String remark;

	private Date createTime;
}
