package vn.udn.dut.algoarena.port.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.udn.dut.algoarena.common.core.utils.MapstructUtils;
import vn.udn.dut.algoarena.common.mybatis.core.page.PageQuery;
import vn.udn.dut.algoarena.common.mybatis.core.page.TableDataInfo;
import vn.udn.dut.algoarena.port.domain.ExamResultDetail;
import vn.udn.dut.algoarena.port.domain.bo.ExamResultDetailBo;
import vn.udn.dut.algoarena.port.domain.vo.ExamResultDetailVo;
import vn.udn.dut.algoarena.port.mapper.ExamResultDetailMapper;
import vn.udn.dut.algoarena.port.service.IExamResultDetailService;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * ExamResultDetailService business layer processing
 *
 * @author HoaLD
 * @date 2024-11-23
 */
@RequiredArgsConstructor
@Service
public class ExamResultDetailServiceImpl implements IExamResultDetailService {

	private final ExamResultDetailMapper baseMapper;

	/**
	 * Query ExamResultDetail
	 */
	@Override
	public ExamResultDetailVo queryById(Long id) {
		return baseMapper.selectVoById(id);
	}

	/**
	 * Query ExamResultDetail list
	 */
	@Override
	public TableDataInfo<ExamResultDetailVo> queryPageList(ExamResultDetailBo bo, PageQuery pageQuery) {
		LambdaQueryWrapper<ExamResultDetail> lqw = buildQueryWrapper(bo);
		Page<ExamResultDetailVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
		return TableDataInfo.build(result);
	}

	/**
	 * Query ExamResultDetail list
	 */
	@Override
	public List<ExamResultDetailVo> queryList(ExamResultDetailBo bo) {
		LambdaQueryWrapper<ExamResultDetail> lqw = buildQueryWrapper(bo);
		return baseMapper.selectVoList(lqw);
	}

	private LambdaQueryWrapper<ExamResultDetail> buildQueryWrapper(ExamResultDetailBo bo) {
//        Map<String, Object> params = bo.getParams();
		LambdaQueryWrapper<ExamResultDetail> lqw = Wrappers.lambdaQuery();
		lqw.eq(!Objects.isNull(bo.getExamId()), ExamResultDetail::getExamId, bo.getExamId());
		
		return lqw;
	}

	/**
	 * Add ExamResultDetail
	 */
	@Override
	public Boolean insertByBo(ExamResultDetailBo bo) {
		ExamResultDetail add = MapstructUtils.convert(bo, ExamResultDetail.class);
		validEntityBeforeSave(add);
		boolean flag = baseMapper.insert(add) > 0;
		if (flag) {
			bo.setId(add.getId());
		}
		return flag;
	}

	/**
	 * Edit ExamResultDetail
	 */
	@Override
	public Boolean updateByBo(ExamResultDetailBo bo) {
		ExamResultDetail update = MapstructUtils.convert(bo, ExamResultDetail.class);
		validEntityBeforeSave(update);
		return baseMapper.updateById(update) > 0;
	}

	/**
	 * Data verification before saving
	 */
	private void validEntityBeforeSave(ExamResultDetail entity) {
		// TODO Do some data validation, such as unique constraints
	}

	/**
	 * Batch delete ExamResultDetail
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
