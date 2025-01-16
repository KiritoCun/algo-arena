package vn.udn.dut.algoarena.port.service;

import vn.udn.dut.algoarena.common.mybatis.core.page.PageQuery;
import vn.udn.dut.algoarena.common.mybatis.core.page.TableDataInfo;
import vn.udn.dut.algoarena.port.domain.bo.ProblemFunctionSignatureBo;
import vn.udn.dut.algoarena.port.domain.vo.ProblemFunctionSignatureVo;

import java.util.Collection;
import java.util.List;

/**
 * ProblemFunctionSignature service interface
 *
 * @author HoaLD
 * @date 2024-12-25
 */
public interface IProblemFunctionSignatureService {

	/**
	 * Query ProblemFunctionSignature
	 */
	ProblemFunctionSignatureVo queryById(Long id);

	/**
	 * Query ProblemFunctionSignature list
	 */
	TableDataInfo<ProblemFunctionSignatureVo> queryPageList(ProblemFunctionSignatureBo bo, PageQuery pageQuery);

	/**
	 * Query ProblemFunctionSignature list
	 */
	List<ProblemFunctionSignatureVo> queryList(ProblemFunctionSignatureBo bo);

	/**
	 * Query ProblemFunctionSignature list with user id
	 */
	List<ProblemFunctionSignatureVo> queryListWithUserId(ProblemFunctionSignatureBo bo);

	/**
	 * Add ProblemFunctionSignature
	 */
	Boolean insertByBo(ProblemFunctionSignatureBo bo);

	/**
	 * Edit ProblemFunctionSignature
	 */
	Boolean updateByBo(ProblemFunctionSignatureBo bo);

	/**
	 * Verify and delete ProblemFunctionSignature information in batches
	 */
	Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

}
