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
import vn.udn.dut.algoarena.port.domain.Exam;
import vn.udn.dut.algoarena.port.domain.bo.ExamBo;
import vn.udn.dut.algoarena.port.domain.vo.ExamVo;
import vn.udn.dut.algoarena.port.mapper.ExamMapper;
import vn.udn.dut.algoarena.port.service.IExamService;

import java.util.Collection;
import java.util.List;

/**
 * ExamService business layer processing
 *
 * @author HoaLD
 * @date 2024-11-23
 */
@RequiredArgsConstructor
@Service
public class ExamServiceImpl implements IExamService {

	private final ExamMapper baseMapper;

	/**
	 * Query Exam
	 */
	@Override
	public ExamVo queryById(Long id) {
		return baseMapper.selectVoById(id);
	}

	/**
	 * Query Exam list
	 */
	@Override
	public TableDataInfo<ExamVo> queryPageList(ExamBo bo, PageQuery pageQuery) {
		LambdaQueryWrapper<Exam> lqw = buildQueryWrapper(bo);
		Page<ExamVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
		return TableDataInfo.build(result);
	}

	/**
	 * Query Exam list
	 */
	@Override
	public List<ExamVo> queryList(ExamBo bo) {
		LambdaQueryWrapper<Exam> lqw = buildQueryWrapper(bo);
		return baseMapper.selectVoList(lqw);
	}

	private LambdaQueryWrapper<Exam> buildQueryWrapper(ExamBo bo) {
//        Map<String, Object> params = bo.getParams();
		LambdaQueryWrapper<Exam> lqw = Wrappers.lambdaQuery();
		lqw.like(StringUtils.isNotBlank(bo.getTitle()), Exam::getTitle, bo.getTitle());
		
		return lqw;
	}

	/**
	 * Add Exam
	 */
	@Override
	public Boolean insertByBo(ExamBo bo) {
		Exam add = MapstructUtils.convert(bo, Exam.class);
		validEntityBeforeSave(add);
		boolean flag = baseMapper.insert(add) > 0;
		if (flag) {
			bo.setId(add.getId());
		}
		return flag;
	}

	/**
	 * Edit Exam
	 */
	@Override
	public Boolean updateByBo(ExamBo bo) {
		Exam update = MapstructUtils.convert(bo, Exam.class);
		validEntityBeforeSave(update);
		return baseMapper.updateById(update) > 0;
	}

	/**
	 * Data verification before saving
	 */
	private void validEntityBeforeSave(Exam entity) {
		// TODO Do some data validation, such as unique constraints
	}

	/**
	 * Batch delete Exam
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
