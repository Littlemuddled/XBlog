package com.kun.service;

import com.kun.domain.Result;
import com.kun.domain.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kun.domain.vo.MenuVO;

import java.util.List;

/**
 * <p>
 * 菜单权限表 服务类
 * </p>
 *
 * @author kun
 * @since 2022-11-19
 */
public interface IMenuService extends IService<Menu> {

    /**
     * 根据用户id查询用户权限信息
     * @param id 用户id
     * @return
     */
    List<String> selectPermsByUserId(Long id);

    /**
     * 根据用户id查询所有的菜单信息
     * @param userId 用户id
     * @return
     */
    List<MenuVO> selectRouterMenuTreeByUserId(Long userId);

    /**
     * 根据菜单状态或菜单名模糊查询菜单列表
     * @param menu
     * @return
     */
    List<Menu> menuList(Menu menu);

    /**
     * 判断菜单是否有子菜单
     * @param menuId 菜单id
     * @return
     */
    boolean hasChild(Long menuId);

    Result treeselect();

    /**
     * 加载对应角色菜单列表树
     * @param roleId
     * @return
     */
    Result roleMenuTreeSelect(Long roleId);
}
