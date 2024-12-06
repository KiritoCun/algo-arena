package vn.udn.dut.algoarena.port.domain.bo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import vn.udn.dut.algoarena.port.domain.Exam;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

	/**
	 * request parameters
	 */
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@TableField(exist = false)
	private Map<String, Object> params = new HashMap<>();
}
