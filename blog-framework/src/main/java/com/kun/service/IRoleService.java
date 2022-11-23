package com.kun.service;

import com.kun.domain.Result;
import com.kun.domain.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kun.domain.vo.RoleVO;

import java.util.List;

/**
 * <p>
 * 角色信息表 服务类
 * </p>
 *
 * @author kun
 * @since 2022-11-19
 */
public interface IRoleService extends IService<Role> {

    /**
     * 查询用户的角色信息
     * @param id 用户id
     * @return
     */
    List<String> selectRoleKeyByUserId(Long id);

    /**
     * 模糊查询得到所有的角色信息
     * @param role
     * @param pageNum
     * @param pageSize
     * @return
     */
    Result getAllRole(Role role, Integer pageNum, Integer pageSize);

    /**
     * 改变角色状态
     * @param roleVO
     * @return
     */
    Result changeStatus(RoleVO roleVO);

    Result addRole(Role role);

    /**
     * 修改角色
     * @param role
     */
    void updateRole(Role role);

    /**
     * 【查询所有的角色
     * @return
     */
    List<Role> selectRoleAll();

    /**
     * 当前用户所具有的角色id列表
     * @param userId
     * @return
     */
    List<Long> selectRoleIdByUserId(Long userId);
}
