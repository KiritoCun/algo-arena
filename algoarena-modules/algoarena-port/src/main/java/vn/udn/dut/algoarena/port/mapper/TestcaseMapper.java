package vn.udn.dut.algoarena.port.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import vn.udn.dut.algoarena.common.mybatis.core.mapper.BaseMapperPlus;
import vn.udn.dut.algoarena.port.domain.Testcase;
import vn.udn.dut.algoarena.port.domain.vo.TestcaseVo;

import java.util.List;

/**
 * Testcase mapper interface
 *
 * @author HoaLD
 * @date 2024-11-20
 */
public interface TestcaseMapper extends BaseMapperPlus<Testcase, TestcaseVo> {

	/**
	 * 
	 * @param page
	 * @param queryWrapper
	 * @return
	 */
	Page<TestcaseVo> selectPageTestcaseList(@Param("page") Page<Testcase> page,
			@Param(Constants.WRAPPER) Wrapper<Testcase> queryWrapper);

	/**
	 * 
	 * @param queryWrapper
	 * @return
	 */
	List<TestcaseVo> selectTestcaseList(@Param(Constants.WRAPPER) Wrapper<Testcase> queryWrapper);

}
