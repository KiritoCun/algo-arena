package vn.udn.dut.algoarena.admin.controller.portCustomer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.util.ObjectUtil;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import vn.udn.dut.algoarena.common.core.domain.R;
import vn.udn.dut.algoarena.common.core.validate.AddGroup;
import vn.udn.dut.algoarena.common.core.validate.EditGroup;
import vn.udn.dut.algoarena.common.idempotent.annotation.RepeatSubmit;
import vn.udn.dut.algoarena.common.log.annotation.Log;
import vn.udn.dut.algoarena.common.log.enums.BusinessType;
import vn.udn.dut.algoarena.common.mybatis.core.page.PageQuery;
import vn.udn.dut.algoarena.common.mybatis.core.page.TableDataInfo;
import vn.udn.dut.algoarena.common.web.core.BaseController;
import vn.udn.dut.algoarena.port.domain.bo.AlgoArenaBo;
import vn.udn.dut.algoarena.port.domain.vo.AlgoArenaVo;
import vn.udn.dut.algoarena.port.service.IAlgoArenaService;

/**
 * AlgoArena api
 *
 * @author HoaLD
 * @date 2023-12-02
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/portCustomer/algoarena")
public class AlgoArenaController extends BaseController {

	private final IAlgoArenaService algoarenaService;
	
	@GetMapping(value = { "support/", "support/{algoArenaId}" })
	public R<List<AlgoArenaVo>> getAlgoArenaInfo(@PathVariable(value = "userId", required = false) Long algoArenaId) {
		if (ObjectUtil.isNotNull(algoArenaId)) {
			List<AlgoArenaVo> algoarenas = new ArrayList<>();
			algoarenas.add(algoarenaService.queryById(algoArenaId));
			return R.ok(algoarenas);
		}
		return R.ok(algoarenaService.queryList(new AlgoArenaBo()));
	}

	/**
	 * Query AlgoArena list
	 */
	@SaCheckPermission("portCustomer:algoarena:list")
	@GetMapping("/list")
	public TableDataInfo<AlgoArenaVo> list(AlgoArenaBo bo, PageQuery pageQuery) {
		return algoarenaService.queryPageList(bo, pageQuery);
	}

	/**
	 * Get AlgoArena
	 *
	 * @param id primary key
	 */
	@SaCheckPermission("portCustomer:algoarena:query")
	@GetMapping(value = { "/", "/{id}" })
	public R<AlgoArenaVo> getInfo(@PathVariable(value = "id", required = false) Long id) {
		return R.ok(algoarenaService.queryById(id));
	}

	/**
	 * Add AlgoArena
	 */
	@SaCheckPermission("portCustomer:algoarena:add")
	@Log(title = "AlgoArena", businessType = BusinessType.INSERT)
	@RepeatSubmit()
	@PostMapping()
	public R<Void> add(@Validated(AddGroup.class) @RequestBody AlgoArenaBo bo) {
		return toAjax(algoarenaService.insertByBo(bo));
	}

	/**
	 * Edit AlgoArena
	 */
	@SaCheckPermission("portCustomer:algoarena:edit")
	@Log(title = "AlgoArena", businessType = BusinessType.UPDATE)
	@RepeatSubmit()
	@PutMapping()
	public R<Void> edit(@Validated(EditGroup.class) @RequestBody AlgoArenaBo bo) {
		return toAjax(algoarenaService.updateByBo(bo));
	}

	/**
	 * Delete AlgoArena
	 *
	 * @param ids primary key string
	 */
	@SaCheckPermission("portCustomer:algoarena:remove")
	@Log(title = "AlgoArena", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
	public R<Void> remove(@NotEmpty(message = "Primary key cannot be empty") @PathVariable Long[] ids) {
		return toAjax(algoarenaService.deleteWithValidByIds(List.of(ids), true));
	}
}
