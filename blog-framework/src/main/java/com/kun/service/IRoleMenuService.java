package com.kun.service;

import com.kun.domain.entity.RoleMenu;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 角色和菜单关联表 服务类
 * </p>
 *
 * @author kun
 * @since 2022-11-19
 */
public interface IRoleMenuService extends IService<RoleMenu> {

    void deleteRoleMenuByRoleId(Long id);
}
