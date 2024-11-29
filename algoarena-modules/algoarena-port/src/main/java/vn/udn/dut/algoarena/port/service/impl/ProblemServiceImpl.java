package vn.udn.dut.algoarena.port.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.udn.dut.algoarena.common.core.utils.MapstructUtils;
import vn.udn.dut.algoarena.common.mybatis.core.page.PageQuery;
import vn.udn.dut.algoarena.common.mybatis.core.page.TableDataInfo;
import vn.udn.dut.algoarena.port.domain.Problem;
import vn.udn.dut.algoarena.port.domain.bo.ProblemBo;
import vn.udn.dut.algoarena.port.domain.vo.ProblemVo;
import vn.udn.dut.algoarena.port.mapper.ProblemMapper;
import vn.udn.dut.algoarena.port.service.IProblemService;

import java.util.Collection;
import java.util.List;

/**
 * ProblemService business layer processing
 *
 * @author HoaLD
 * @date 2024-11-20
 */
@RequiredArgsConstructor
@Service
public class ProblemServiceImpl implements IProblemService {

	private final ProblemMapper baseMapper;

	/**
	 * Query Problem
	 */
	@Override
	public ProblemVo queryById(Long id) {
		return baseMapper.selectVoById(id);
	}

	/**
	 * Query Problem list
	 */
	@Override
	public TableDataInfo<ProblemVo> queryPageList(ProblemBo bo, PageQuery pageQuery) {
		Page<ProblemVo> page = baseMapper.selectPageProblemList(pageQuery.build(), this.buildQueryWrapper(bo));
		return TableDataInfo.build(page);
	}

	/**
	 * Query Problem list
	 */
	@Override
	public List<ProblemVo> queryList(ProblemBo bo) {
//		LambdaQueryWrapper<Problem> lqw = buildQueryWrapper(bo);
		return baseMapper.selectProblemList(this.buildQueryWrapper(bo));
	}

	private Wrapper<Problem> buildQueryWrapper(ProblemBo bo) {
//        Map<String, Object> params = bo.getParams();
		QueryWrapper<Problem> wrapper = Wrappers.query();
		wrapper.eq(bo.getTitle() != null, "p.title", bo.getTitle())
				.like(bo.getDescription() != null, "p.description", bo.getDescription())
				.eq(bo.getDifficulty() != null, "p.difficulty", bo.getDifficulty())
				.like(bo.getRemark() != null, "p.remark", bo.getRemark());
		return wrapper;
	}

	/**
	 * Add Problem
	 */
	@Override
	public Boolean insertByBo(ProblemBo bo) {
		Problem add = MapstructUtils.convert(bo, Problem.class);
		validEntityBeforeSave(add);
		boolean flag = baseMapper.insert(add) > 0;
		if (flag) {
			bo.setId(add.getId());
		}
		return flag;
	}

	/**
	 * Edit Problem
	 */
	@Override
	public Boolean updateByBo(ProblemBo bo) {
		Problem update = MapstructUtils.convert(bo, Problem.class);
		validEntityBeforeSave(update);
		return baseMapper.updateById(update) > 0;
	}

	/**
	 * Data verification before saving
	 */
	private void validEntityBeforeSave(Problem entity) {
		// TODO Do some data validation, such as unique constraints
	}

	/**
	 * Batch delete Problem
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