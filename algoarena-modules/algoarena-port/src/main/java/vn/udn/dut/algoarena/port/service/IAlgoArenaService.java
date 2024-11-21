package vn.udn.dut.algoarena.port.service;

import java.util.Collection;
import java.util.List;

import vn.udn.dut.algoarena.common.mybatis.core.page.PageQuery;
import vn.udn.dut.algoarena.common.mybatis.core.page.TableDataInfo;
import vn.udn.dut.algoarena.port.domain.bo.AlgoArenaBo;
import vn.udn.dut.algoarena.port.domain.vo.AlgoArenaVo;

public interface IAlgoArenaService {
	AlgoArenaVo queryById(Long id);

	TableDataInfo<AlgoArenaVo> queryPageList(AlgoArenaBo bo, PageQuery pageQuery);

	List<AlgoArenaVo> queryList(AlgoArenaBo bo);

	Boolean insertByBo(AlgoArenaBo bo);

	Boolean updateByBo(AlgoArenaBo bo);

	Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
