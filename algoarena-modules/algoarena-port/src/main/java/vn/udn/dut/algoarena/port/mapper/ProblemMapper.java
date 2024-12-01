package vn.udn.dut.algoarena.port.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import vn.udn.dut.algoarena.common.mybatis.core.mapper.BaseMapperPlus;
import vn.udn.dut.algoarena.port.domain.Problem;
import vn.udn.dut.algoarena.port.domain.vo.ProblemVo;

import java.util.List;

/**
 * Problem mapper interface
 *
 * @author HoaLD
 * @date 2024-11-20
 */
public interface ProblemMapper extends BaseMapperPlus<Problem, ProblemVo> {

	/**
	 *
	 * @param page
	 * @param queryWrapper
	 * @return
	 */
	Page<ProblemVo> selectPagePublicProblemList(@Param("page") Page<Problem> page,
										  @Param(Constants.WRAPPER) Wrapper<Problem> queryWrapper);

	/**
	 * 
	 * @param page
	 * @param queryWrapper
	 * @return
	 */
	Page<ProblemVo> selectPageProblemList(@Param("page") Page<Problem> page,
			@Param(Constants.WRAPPER) Wrapper<Problem> queryWrapper);

	/**
	 * 
	 * @param wrapper
	 * @return
	 */
	List<ProblemVo> selectProblemList(@Param(Constants.WRAPPER) Wrapper<Problem> wrapper);

}
