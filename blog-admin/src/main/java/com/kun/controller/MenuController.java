package com.kun.controller;

import com.kun.domain.Result;
import com.kun.domain.entity.Menu;
import com.kun.domain.vo.AdminMenuVO;
import com.kun.service.IMenuService;
import com.kun.utils.BeanCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 菜单权限表 前端控制器
 * </p>
 *
 * @author kun
 * @since 2022-11-19
 */
@RestController
@RequestMapping("/system/menu")
public class MenuController {

    @Resource
    private IMenuService menuService;

    @GetMapping("/list")
    public Result menuList(Menu menu) {
        List<Menu> menuList = menuService.menuList(menu);
        List<AdminMenuVO> adminMenuVOS = BeanCopyUtils.copyBeanList(menuList, AdminMenuVO.class);
        return Result.okResult(adminMenuVOS);
    }

    @GetMapping(value = "/{menuId}")
    public Result getInfo(@PathVariable Long menuId) {
        return Result.okResult(menuService.getById(menuId));
    }

    @PostMapping
    public Result addMenu(@RequestBody Menu menu) {
        menuService.save(menu);
        return Result.okResult();
    }

    @PutMapping
    public Result updateMenu(@RequestBody Menu menu) {
        if (menu.getId().equals(menu.getParentId())) {
            return Result.errorResult(500,"修改菜单'" + menu.getMenuName() + "'失败，上级菜单不能选择自己");
        }
        menuService.updateById(menu);
        return Result.okResult();
    }

    @DeleteMapping("/{menuId}")
    public Result remove(@PathVariable Long menuId) {
        if (menuService.hasChild(menuId)) {
            return Result.errorResult(500,"存在子菜单不允许删除");
        }
        menuService.removeById(menuId);
        return Result.okResult();
    }

    @GetMapping("/treeselect")
    public Result treeselect() {
        return menuService.treeselect();
    }

    /**
     * 加载对应角色菜单列表树
     */
    @GetMapping(value = "/roleMenuTreeselect/{roleId}")
    public Result roleMenuTreeSelect(@PathVariable("roleId") Long roleId) {
        return menuService.roleMenuTreeSelect(roleId);
    }


}
