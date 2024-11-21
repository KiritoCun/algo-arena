package vn.udn.dut.algoarena.port.service.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.RequiredArgsConstructor;
import vn.udn.dut.algoarena.common.core.utils.MapstructUtils;
import vn.udn.dut.algoarena.common.core.utils.StringUtils;
import vn.udn.dut.algoarena.common.mybatis.core.page.PageQuery;
import vn.udn.dut.algoarena.common.mybatis.core.page.TableDataInfo;
import vn.udn.dut.algoarena.port.domain.AlgoArena;
import vn.udn.dut.algoarena.port.domain.bo.AlgoArenaBo;
import vn.udn.dut.algoarena.port.domain.vo.AlgoArenaVo;
import vn.udn.dut.algoarena.port.mapper.AlgoArenaMapper;
import vn.udn.dut.algoarena.port.service.IAlgoArenaService;

@RequiredArgsConstructor
@Service
public class AlgoArenaServiceImpl implements IAlgoArenaService {
	private final AlgoArenaMapper baseMapper;

	public AlgoArenaVo queryById(Long id) {
		return baseMapper.selectVoById(id);
	}

	/**
	 * Query AlgoArena list
	 */
	@Override
	public TableDataInfo<AlgoArenaVo> queryPageList(AlgoArenaBo bo, PageQuery pageQuery) {
		LambdaQueryWrapper<AlgoArena> lqw = buildQueryWrapper(bo);
		Page<AlgoArenaVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
		return TableDataInfo.build(result);
	}

	/**
	 * Query AlgoArena list
	 */
	@Override
	public List<AlgoArenaVo> queryList(AlgoArenaBo bo) {
		LambdaQueryWrapper<AlgoArena> lqw = buildQueryWrapper(bo);
		return baseMapper.selectVoList(lqw);
	}

	private LambdaQueryWrapper<AlgoArena> buildQueryWrapper(AlgoArenaBo bo) {
//        Map<String, Object> params = bo.getParams();
		LambdaQueryWrapper<AlgoArena> lqw = Wrappers.lambdaQuery();
		lqw.like(StringUtils.isNotBlank(bo.getAlgoArenaName()), AlgoArena::getAlgoArenaName, bo.getAlgoArenaName());
		lqw.like(StringUtils.isNotBlank(bo.getAlgoArenaAddress()), AlgoArena::getAlgoArenaAddress, bo.getAlgoArenaAddress());
		return lqw;
	}

	/**
	 * Add AlgoArena
	 */
	@Override
	public Boolean insertByBo(AlgoArenaBo bo) {
		AlgoArena add = MapstructUtils.convert(bo, AlgoArena.class);
		validEntityBeforeSave(add);
		boolean flag = baseMapper.insert(add) > 0;
		if (flag) {
			bo.setId(add.getId());
		}
		return flag;
	}

	/**
	 * Edit AlgoArena
	 */
	@Override
	public Boolean updateByBo(AlgoArenaBo bo) {
		AlgoArena update = MapstructUtils.convert(bo, AlgoArena.class);
		validEntityBeforeSave(update);
		return baseMapper.updateById(update) > 0;
	}

	/**
	 * Data verification before saving
	 */
	private void validEntityBeforeSave(AlgoArena entity) {
		// TODO Do some data validation, such as unique constraints
	}

	/**
	 * Batch delete AlgoArena
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
