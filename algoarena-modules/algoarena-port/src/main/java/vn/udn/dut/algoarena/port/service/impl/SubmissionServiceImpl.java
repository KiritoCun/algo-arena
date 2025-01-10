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
import vn.udn.dut.algoarena.port.domain.Submission;
import vn.udn.dut.algoarena.port.domain.bo.SubmissionBo;
import vn.udn.dut.algoarena.port.domain.vo.SubmissionVo;
import vn.udn.dut.algoarena.port.mapper.SubmissionMapper;
import vn.udn.dut.algoarena.port.service.ISubmissionService;

import java.util.Collection;
import java.util.List;

/**
 * SubmissionService business layer processing
 *
 * @author HoaLD
 * @date 2024-11-23
 */
@RequiredArgsConstructor
@Service
public class SubmissionServiceImpl implements ISubmissionService {

	private final SubmissionMapper baseMapper;

	/**
	 * Query Submission
	 */
	@Override
	public SubmissionVo queryById(Long id) {
		return baseMapper.selectVoById(id);
	}

	/**
	 * Query Submission list
	 */
	@Override
	public TableDataInfo<SubmissionVo> queryPageList(SubmissionBo bo, PageQuery pageQuery) {
		LambdaQueryWrapper<Submission> lqw = buildQueryWrapper(bo);
		Page<SubmissionVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
		return TableDataInfo.build(result);
	}

	/**
	 * Query Submission list
	 */
	@Override
	public List<SubmissionVo> queryList(SubmissionBo bo) {
		LambdaQueryWrapper<Submission> lqw = buildQueryWrapper(bo);
		return baseMapper.selectVoList(lqw);
	}

	private LambdaQueryWrapper<Submission> buildQueryWrapper(SubmissionBo bo) {
//        Map<String, Object> params = bo.getParams();
		LambdaQueryWrapper<Submission> lqw = Wrappers.lambdaQuery();
		lqw.eq(bo.getProblemId() != null, Submission::getProblemId, bo.getProblemId());
		lqw.eq(bo.getProgramingLanguage() != null, Submission::getProgramingLanguage, bo.getProgramingLanguage());
		lqw.eq(bo.getUserId() != null, Submission::getUserId, bo.getUserId());
		lqw.like(StringUtils.isNotBlank(bo.getCode()), Submission::getCode, bo.getCode());
		
		return lqw;
	}

	/**
	 * Add Submission
	 */
	@Override
	public Boolean insertByBo(SubmissionBo bo) {
		Submission add = MapstructUtils.convert(bo, Submission.class);
		validEntityBeforeSave(add);
		boolean flag = baseMapper.insert(add) > 0;
		if (flag) {
			bo.setId(add.getId());
		}
		return flag;
	}

	/**
	 * Edit Submission
	 */
	@Override
	public Boolean updateByBo(SubmissionBo bo) {
		Submission update = MapstructUtils.convert(bo, Submission.class);
		validEntityBeforeSave(update);
		return baseMapper.updateById(update) > 0;
	}

	/**
	 * Data verification before saving
	 */
	private void validEntityBeforeSave(Submission entity) {
		// TODO Do some data validation, such as unique constraints
	}

	/**
	 * Batch delete Submission
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
