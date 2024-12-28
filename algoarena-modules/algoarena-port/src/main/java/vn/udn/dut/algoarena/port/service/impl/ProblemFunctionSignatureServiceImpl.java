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
import vn.udn.dut.algoarena.port.domain.ProblemFunctionSignature;
import vn.udn.dut.algoarena.port.domain.bo.ProblemFunctionSignatureBo;
import vn.udn.dut.algoarena.port.domain.vo.ProblemFunctionSignatureVo;
import vn.udn.dut.algoarena.port.mapper.ProblemFunctionSignatureMapper;
import vn.udn.dut.algoarena.port.service.IProblemFunctionSignatureService;

import java.util.Collection;
import java.util.List;

/**
 * ProblemFunctionSignatureService business layer processing
 *
 * @author HoaLD
 * @date 2024-12-25
 */
@RequiredArgsConstructor
@Service
public class ProblemFunctionSignatureServiceImpl implements IProblemFunctionSignatureService {

    private final ProblemFunctionSignatureMapper baseMapper;

    /**
     * Query ProblemFunctionSignature
     */
    @Override
    public ProblemFunctionSignatureVo queryById(Long id) {
        return baseMapper.selectVoById(id);
    }

    /**
     * Query ProblemFunctionSignature list
     */
    @Override
    public TableDataInfo<ProblemFunctionSignatureVo> queryPageList(ProblemFunctionSignatureBo bo, PageQuery pageQuery) {
        Page<ProblemFunctionSignatureVo> page = baseMapper.selectPageProblemFunctionSignatureList(pageQuery.build(), this.buildQueryWrapper(bo));
        return TableDataInfo.build(page);
    }

    /**
     * Query ProblemFunctionSignature list
     */
    @Override
    public List<ProblemFunctionSignatureVo> queryList(ProblemFunctionSignatureBo bo) {
        return baseMapper.selectProblemFunctionSignatureList(this.buildQueryWrapper(bo));
    }

    private Wrapper<ProblemFunctionSignature> buildQueryWrapper(ProblemFunctionSignatureBo bo) {
//      Map<String, Object> params = bo.getParams();
        QueryWrapper<ProblemFunctionSignature> wrapper = Wrappers.query();
        wrapper.eq(bo.getProblemId() != null, "pfs.problem_id", bo.getProblemId())
                .eq(bo.getKeyPath() != null, "p.key_path", bo.getKeyPath())
                .like(bo.getRemark() != null, "pfs.remark", bo.getRemark());
        return wrapper;
    }

    /**
     * Add ProblemFunctionSignature
     */
    @Override
    public Boolean insertByBo(ProblemFunctionSignatureBo bo) {
        ProblemFunctionSignature add = MapstructUtils.convert(bo, ProblemFunctionSignature.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * Edit ProblemFunctionSignature
     */
    @Override
    public Boolean updateByBo(ProblemFunctionSignatureBo bo) {
        ProblemFunctionSignature update = MapstructUtils.convert(bo, ProblemFunctionSignature.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * Data verification before saving
     */
    private void validEntityBeforeSave(ProblemFunctionSignature entity) {
        // TODO Do some data validation, such as unique constraints
    }

    /**
     * Batch delete ProblemFunctionSignature
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
