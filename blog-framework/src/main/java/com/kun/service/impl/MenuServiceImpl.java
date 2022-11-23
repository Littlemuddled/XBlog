package com.kun.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kun.constants.SystemConstants;
import com.kun.domain.Result;
import com.kun.domain.entity.Menu;
import com.kun.domain.vo.AdminMenuVO;
import com.kun.domain.vo.MenuTreeVo;
import com.kun.domain.vo.MenuVO;
import com.kun.domain.vo.RoleMenuTreeSelectVo;
import com.kun.enums.AppHttpCodeEnum;
import com.kun.exception.SystemException;
import com.kun.mapper.MenuMapper;
import com.kun.service.IMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kun.utils.BeanCopyUtils;
import com.kun.utils.SystemConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 菜单权限表 服务实现类
 * </p>
 *
 * @author kun
 * @since 2022-11-19
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Resource
    private MenuMapper menuMapper;

    @Override
    public List<String> selectPermsByUserId(Long id) {
        if (id.equals(1L)) {
            //超级管理员拥有所有的权限
            LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.in(Menu::getMenuType, SystemConstants.MENU_TYPE_F,SystemConstants.MENU_TYPE_C)
                    .eq(Menu::getStatus,SystemConstants.STATUS_NORMAL);
            List<Menu> menuList = this.list(queryWrapper);
            List<String> perms = menuList.stream()
                    .map(Menu::getPerms)
                    .collect(Collectors.toList());
            return perms;
        }
        List<String> perms = menuMapper.selectPermsByUserId(id);
        if (perms == null) {
            throw new SystemException(AppHttpCodeEnum.SELECT_PERMS_ERROR);
        }
        return perms;
    }

    @Override
    public List<MenuVO> selectRouterMenuTreeByUserId(Long userId) {
        List<MenuVO> menuVOList = null;
        if (userId.equals(1L)) {
            LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.in(Menu::getMenuType, SystemConstants.MENU_TYPE_M,SystemConstants.MENU_TYPE_C)
                    .eq(Menu::getStatus,SystemConstants.STATUS_NORMAL)
                    .orderByAsc(Menu::getParentId)
                    .orderByAsc(Menu::getOrderNum);
            List<Menu> menuList = this.list(queryWrapper);
            menuVOList = BeanCopyUtils.copyBeanList(menuList, MenuVO.class);
        } else {
            menuVOList = menuMapper.selectRouterMenuTreeByUserId(userId);
        }
        return builderMenuTree(menuVOList);
    }

    private List<MenuVO> builderMenuTree(List<MenuVO> menuVOList) {
        List<MenuVO> menuVOS = menuVOList.stream()
                .filter(menuVO -> menuVO.getParentId().equals(0L))
                .map(menuVO -> menuVO.setChildren(getChildren(menuVO, menuVOList)))
                .collect(Collectors.toList());
        return menuVOS;
    }

    /**
     * 获取存入参数的子menu集合
     * @param menuVO
     * @param menuVOList
     * @return
     */
    private List<MenuVO> getChildren(MenuVO menuVO, List<MenuVO> menuVOList) {
        List<MenuVO> childrenList = menuVOList.stream()
                .filter(m -> m.getParentId().equals(menuVO.getId()))
                .map(m -> m.setChildren(getChildren(m, menuVOList)))
                .collect(Collectors.toList());
        return childrenList;
    }

    @Override
    public List<Menu> menuList(Menu menu) {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.hasText(menu.getStatus()),Menu::getStatus,menu.getStatus());
        queryWrapper.like(StringUtils.hasText(menu.getMenuName()), Menu::getMenuName,menu.getMenuName());
        queryWrapper.orderByAsc(Menu::getParentId)
                .orderByAsc(Menu::getOrderNum);
        List<Menu> menuList = this.list(queryWrapper);
        return menuList;
    }

    @Override
    public boolean hasChild(Long menuId) {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Menu::getParentId,menuId);
        return this.count(queryWrapper) != 0;
    }

    @Override
    public Result treeselect() {
        List<Menu> menuList = this.menuList(new Menu());
        List<MenuTreeVo> options =  SystemConverter.buildMenuSelectTree(menuList);
        return Result.okResult(options);
    }

    @Override
    public Result roleMenuTreeSelect(Long roleId) {
        List<Menu> menus = menuList(new Menu());
        List<Long> checkedKeys = menuMapper.selectMenuListByRoleId(roleId);
        List<MenuTreeVo> menuTreeVos = SystemConverter.buildMenuSelectTree(menus);
        RoleMenuTreeSelectVo vo = new RoleMenuTreeSelectVo(checkedKeys,menuTreeVos);
        return Result.okResult(vo);
    }
}
