package vn.udn.dut.algoarena.port.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import vn.udn.dut.algoarena.port.domain.Exam;

import java.util.Date;

/**
 * Exam business object
 *
 * @author HoaLD
 * @date 2024-11-20
 */
@Data
@AutoMapper(target = Exam.class, reverseConvertGenerate = false)
public class ExamBo {

	private String tenantId;

	private Long id;

	private String title;

	private String description;

	private Date startTime;

	private Date endTime;

	private String remark;

	private Date createTime;
}
