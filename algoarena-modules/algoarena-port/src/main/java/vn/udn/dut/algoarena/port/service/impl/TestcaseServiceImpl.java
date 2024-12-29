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
import vn.udn.dut.algoarena.port.domain.Testcase;
import vn.udn.dut.algoarena.port.domain.bo.TestcaseBo;
import vn.udn.dut.algoarena.port.domain.vo.TestcaseVo;
import vn.udn.dut.algoarena.port.mapper.TestcaseMapper;
import vn.udn.dut.algoarena.port.service.ITestcaseService;

import java.util.Collection;
import java.util.List;

/**
 * TestcaseService business layer processing
 *
 * @author HoaLD
 * @date 2024-11-20
 */
@RequiredArgsConstructor
@Service
public class TestcaseServiceImpl implements ITestcaseService {

	private final TestcaseMapper baseMapper;

	/**
	 * Query Testcase
	 */
	@Override
	public TestcaseVo queryById(Long id) {
		return baseMapper.selectVoById(id);
	}

	/**
	 * Query Testcase list
	 */
	@Override
	public TableDataInfo<TestcaseVo> queryPageList(TestcaseBo bo, PageQuery pageQuery) {
		Page<TestcaseVo> page = baseMapper.selectPageTestcaseList(pageQuery.build(), this.buildQueryWrapper(bo));
		return TableDataInfo.build(page);
	}

	/**
	 * Query Testcase list
	 */
	@Override
	public List<TestcaseVo> queryList(TestcaseBo bo) {
		return baseMapper.selectTestcaseList(this.buildQueryWrapper(bo));
	}

	private Wrapper<Testcase> buildQueryWrapper(TestcaseBo bo) {
//      Map<String, Object> params = bo.getParams();
		QueryWrapper<Testcase> wrapper = Wrappers.query();
		wrapper.eq(bo.getProblemId() != null, "t.problem_id", bo.getProblemId())
				.like(bo.getTestcaseJson() != null, "t.testcase_json", bo.getTestcaseJson())
				.eq(bo.getIsHidden() != null, "t.is_hidden", bo.getIsHidden())
				.eq(bo.getIllustration() != null, "t.illustration", bo.getIllustration())
				.like(bo.getRemark() != null, "t.remark", bo.getRemark());
		return wrapper;
	}

	/**
	 * Add Testcase
	 */
	@Override
	public Boolean insertByBo(TestcaseBo bo) {
		Testcase add = MapstructUtils.convert(bo, Testcase.class);
		validEntityBeforeSave(add);
		boolean flag = baseMapper.insert(add) > 0;
		if (flag) {
			bo.setId(add.getId());
		}
		return flag;
	}

	/**
	 * Edit Testcase
	 */
	@Override
	public Boolean updateByBo(TestcaseBo bo) {
		Testcase update = MapstructUtils.convert(bo, Testcase.class);
		validEntityBeforeSave(update);
		return baseMapper.updateById(update) > 0;
	}

	/**
	 * Data verification before saving
	 */
	private void validEntityBeforeSave(Testcase entity) {
		// TODO Do some data validation, such as unique constraints
	}

	/**
	 * Batch delete Testcase
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
