package vn.udn.dut.algoarena.port.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.udn.dut.algoarena.common.core.utils.MapstructUtils;
import vn.udn.dut.algoarena.common.core.utils.StringUtils;
import vn.udn.dut.algoarena.common.mybatis.core.page.PageQuery;
import vn.udn.dut.algoarena.common.mybatis.core.page.TableDataInfo;
import vn.udn.dut.algoarena.port.domain.ExamProblem;
import vn.udn.dut.algoarena.port.domain.bo.ExamProblemBo;
import vn.udn.dut.algoarena.port.domain.vo.ExamProblemVo;
import vn.udn.dut.algoarena.port.mapper.ExamProblemMapper;
import vn.udn.dut.algoarena.port.service.IExamProblemService;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * ExamProblemService business layer processing
 *
 * @author HoaLD
 * @date 2024-11-23
 */
@RequiredArgsConstructor
@Service
public class ExamProblemServiceImpl implements IExamProblemService {

	private final ExamProblemMapper baseMapper;

	/**
	 * Query ExamProblem
	 */
	@Override
	public ExamProblemVo queryById(Long id) {
		return baseMapper.selectVoById(id);
	}

	/**
	 * Query ExamProblem list
	 */
	@Override
	public TableDataInfo<ExamProblemVo> queryPageList(ExamProblemBo bo, PageQuery pageQuery) {
		LambdaQueryWrapper<ExamProblem> lqw = buildQueryWrapper(bo);
		Page<ExamProblemVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
		return TableDataInfo.build(result);
	}

	/**
	 * Query ExamProblem list
	 */
	@Override
	public List<ExamProblemVo> queryList(ExamProblemBo bo) {
		LambdaQueryWrapper<ExamProblem> lqw = buildQueryWrapper(bo);
		return baseMapper.selectVoList(lqw);
	}

	private LambdaQueryWrapper<ExamProblem> buildQueryWrapper(ExamProblemBo bo) {
//        Map<String, Object> params = bo.getParams();
		LambdaQueryWrapper<ExamProblem> lqw = Wrappers.lambdaQuery();
		lqw.eq(!Objects.isNull(bo.getExamId()), ExamProblem::getExamId, bo.getExamId());
		lqw.eq(!Objects.isNull(bo.getProblemId()), ExamProblem::getProblemId, bo.getProblemId());
		
		return lqw;
	}

	/**
	 * Add ExamProblem
	 */
	@Override
	public Boolean insertByBo(ExamProblemBo bo) {
		ExamProblem add = MapstructUtils.convert(bo, ExamProblem.class);
		validEntityBeforeSave(add);
		boolean flag = baseMapper.insert(add) > 0;
		if (flag) {
			bo.setId(add.getId());
		}
		return flag;
	}

	/**
	 * Edit ExamProblem
	 */
	@Override
	public Boolean updateByBo(ExamProblemBo bo) {
		ExamProblem update = MapstructUtils.convert(bo, ExamProblem.class);
		validEntityBeforeSave(update);
		return baseMapper.updateById(update) > 0;
	}

	/**
	 * Data verification before saving
	 */
	private void validEntityBeforeSave(ExamProblem entity) {
		// TODO Do some data validation, such as unique constraints
	}

	/**
	 * Batch delete ExamProblem
	 */
	@Override
	public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
		if (isValid) {
			// TODO Do some business verification to determine whether verification is
			// required
		}
		return baseMapper.deleteBatchIds(ids) > 0;
	}
}
