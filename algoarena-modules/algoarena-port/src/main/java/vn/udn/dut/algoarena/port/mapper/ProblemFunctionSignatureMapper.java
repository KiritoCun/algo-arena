package vn.udn.dut.algoarena.port.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import vn.udn.dut.algoarena.common.mybatis.core.mapper.BaseMapperPlus;
import vn.udn.dut.algoarena.port.domain.ProblemFunctionSignature;
import vn.udn.dut.algoarena.port.domain.vo.ProblemFunctionSignatureVo;

import java.util.List;

/**
 * ProblemFunctionSignature mapper interface
 *
 * @author HoaLD
 * @date 2024-12-25
 */
public interface ProblemFunctionSignatureMapper extends BaseMapperPlus<ProblemFunctionSignature, ProblemFunctionSignatureVo> {

	/**
	 * 
	 * @param page
	 * @param queryWrapper
	 * @return
	 */
	Page<ProblemFunctionSignatureVo> selectPageProblemFunctionSignatureList(@Param("page") Page<ProblemFunctionSignature> page,
			@Param(Constants.WRAPPER) Wrapper<ProblemFunctionSignature> queryWrapper);

	/**
	 * 
	 * @param queryWrapper
	 * @return
	 */
	List<ProblemFunctionSignatureVo> selectProblemFunctionSignatureList(@Param(Constants.WRAPPER) Wrapper<ProblemFunctionSignature> queryWrapper);

	/**
	 *
	 * @param queryWrapper
	 * @return
	 */
	List<ProblemFunctionSignatureVo> selectProblemFunctionSignatureListWithUserId(@Param(Constants.WRAPPER) Wrapper<ProblemFunctionSignature> queryWrapper);

}
