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
import vn.udn.dut.algoarena.port.domain.ExamResult;
import vn.udn.dut.algoarena.port.domain.bo.ExamResultBo;
import vn.udn.dut.algoarena.port.domain.vo.ExamResultVo;
import vn.udn.dut.algoarena.port.mapper.ExamResultMapper;
import vn.udn.dut.algoarena.port.service.IExamResultService;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * ExamResultService business layer processing
 *
 * @author HoaLD
 * @date 2024-11-23
 */
@RequiredArgsConstructor
@Service
public class ExamResultServiceImpl implements IExamResultService {

	private final ExamResultMapper baseMapper;

	/**
	 * Query ExamResult
	 */
	@Override
	public ExamResultVo queryById(Long id) {
		return baseMapper.selectVoById(id);
	}

	/**
	 * Query ExamResult list
	 */
	@Override
	public TableDataInfo<ExamResultVo> queryPageList(ExamResultBo bo, PageQuery pageQuery) {
		LambdaQueryWrapper<ExamResult> lqw = buildQueryWrapper(bo);
		Page<ExamResultVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
		return TableDataInfo.build(result);
	}

	/**
	 * Query ExamResult list
	 */
	@Override
	public List<ExamResultVo> queryList(ExamResultBo bo) {
		LambdaQueryWrapper<ExamResult> lqw = buildQueryWrapper(bo);
		return baseMapper.selectVoList(lqw);
	}

	private LambdaQueryWrapper<ExamResult> buildQueryWrapper(ExamResultBo bo) {
//        Map<String, Object> params = bo.getParams();
		LambdaQueryWrapper<ExamResult> lqw = Wrappers.lambdaQuery();
		lqw.eq(!Objects.isNull(bo.getExamId()), ExamResult::getExamId, bo.getExamId());
		
		return lqw;
	}

	/**
	 * Add ExamResult
	 */
	@Override
	public Boolean insertByBo(ExamResultBo bo) {
		ExamResult add = MapstructUtils.convert(bo, ExamResult.class);
		validEntityBeforeSave(add);
		boolean flag = baseMapper.insert(add) > 0;
		if (flag) {
			bo.setId(add.getId());
		}
		return flag;
	}

	/**
	 * Edit ExamResult
	 */
	@Override
	public Boolean updateByBo(ExamResultBo bo) {
		ExamResult update = MapstructUtils.convert(bo, ExamResult.class);
		validEntityBeforeSave(update);
		return baseMapper.updateById(update) > 0;
	}

	/**
	 * Data verification before saving
	 */
	private void validEntityBeforeSave(ExamResult entity) {
		// TODO Do some data validation, such as unique constraints
	}

	/**
	 * Batch delete ExamResult
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
