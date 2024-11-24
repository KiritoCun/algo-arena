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
import vn.udn.dut.algoarena.port.domain.SubmissionTestcase;
import vn.udn.dut.algoarena.port.domain.bo.SubmissionTestcaseBo;
import vn.udn.dut.algoarena.port.domain.vo.SubmissionTestcaseVo;
import vn.udn.dut.algoarena.port.mapper.SubmissionTestcaseMapper;
import vn.udn.dut.algoarena.port.service.ISubmissionTestcaseService;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * SubmissionTestcaseService business layer processing
 *
 * @author HoaLD
 * @date 2024-11-23
 */
@RequiredArgsConstructor
@Service
public class SubmissionTestcaseServiceImpl implements ISubmissionTestcaseService {

	private final SubmissionTestcaseMapper baseMapper;

	/**
	 * Query SubmissionTestcase
	 */
	@Override
	public SubmissionTestcaseVo queryById(Long id) {
		return baseMapper.selectVoById(id);
	}

	/**
	 * Query SubmissionTestcase list
	 */
	@Override
	public TableDataInfo<SubmissionTestcaseVo> queryPageList(SubmissionTestcaseBo bo, PageQuery pageQuery) {
		LambdaQueryWrapper<SubmissionTestcase> lqw = buildQueryWrapper(bo);
		Page<SubmissionTestcaseVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
		return TableDataInfo.build(result);
	}

	/**
	 * Query SubmissionTestcase list
	 */
	@Override
	public List<SubmissionTestcaseVo> queryList(SubmissionTestcaseBo bo) {
		LambdaQueryWrapper<SubmissionTestcase> lqw = buildQueryWrapper(bo);
		return baseMapper.selectVoList(lqw);
	}

	private LambdaQueryWrapper<SubmissionTestcase> buildQueryWrapper(SubmissionTestcaseBo bo) {
//        Map<String, Object> params = bo.getParams();
		LambdaQueryWrapper<SubmissionTestcase> lqw = Wrappers.lambdaQuery();
		lqw.eq(!Objects.isNull(bo.getSubmissionId()), SubmissionTestcase::getSubmissionId, bo.getSubmissionId());
		lqw.eq(!Objects.isNull(bo.getTestcaseId()), SubmissionTestcase::getTestcaseId, bo.getTestcaseId());

		return lqw;
	}

	/**
	 * Add SubmissionTestcase
	 */
	@Override
	public Boolean insertByBo(SubmissionTestcaseBo bo) {
		SubmissionTestcase add = MapstructUtils.convert(bo, SubmissionTestcase.class);
		validEntityBeforeSave(add);
		boolean flag = baseMapper.insert(add) > 0;
		if (flag) {
			bo.setId(add.getId());
		}
		return flag;
	}

	/**
	 * Edit SubmissionTestcase
	 */
	@Override
	public Boolean updateByBo(SubmissionTestcaseBo bo) {
		SubmissionTestcase update = MapstructUtils.convert(bo, SubmissionTestcase.class);
		validEntityBeforeSave(update);
		return baseMapper.updateById(update) > 0;
	}

	/**
	 * Data verification before saving
	 */
	private void validEntityBeforeSave(SubmissionTestcase entity) {
		// TODO Do some data validation, such as unique constraints
	}

	/**
	 * Batch delete SubmissionTestcase
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
