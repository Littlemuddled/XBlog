package com.kun.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kun.constants.SystemConstants;
import com.kun.domain.Result;
import com.kun.domain.entity.Role;
import com.kun.domain.entity.RoleMenu;
import com.kun.domain.vo.PageVO;
import com.kun.domain.vo.RoleVO;
import com.kun.enums.AppHttpCodeEnum;
import com.kun.exception.SystemException;
import com.kun.mapper.RoleMapper;
import com.kun.mapper.RoleMenuMapper;
import com.kun.service.IRoleMenuService;
import com.kun.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色信息表 服务实现类
 * </p>
 *
 * @author kun
 * @since 2022-11-19
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Resource
    private RoleMapper roleMapper;
    @Resource
    private IRoleMenuService roleMenuService;

    @Override
    public List<String> selectRoleKeyByUserId(Long id) {
        if (id.equals(1L)) {
            //超级用户拥有所有的角色
            LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Role::getStatus, SystemConstants.STATUS_NORMAL);
            List<Role> roleList = this.list(queryWrapper);
            List<String> roleKeys = roleList.stream()
                    .map(Role::getRoleKey)
                    .collect(Collectors.toList());
            return roleKeys;
        }
        List<String> roleKeys = roleMapper.selectRoleKeyByUserId(id);
        if (roleKeys == null) {
            throw new SystemException(AppHttpCodeEnum.SELECT_ROLE_ERROR);
        }
        return roleKeys;
    }

    @Override
    public Result getAllRole(Role role, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(role.getRoleName()), Role::getRoleName, role.getRoleName());
        queryWrapper.eq(StringUtils.hasText(role.getStatus()), Role::getStatus, role.getStatus());
        queryWrapper.orderByAsc(Role::getRoleSort);

        Page<Role> page = new Page<>(pageNum, pageSize);
        Page<Role> rolePage = this.page(page, queryWrapper);

        List<Role> roleList = rolePage.getRecords();
        long total = rolePage.getTotal();

        PageVO<Role> pageVO = new PageVO<>(roleList, total);

        return Result.okResult(pageVO);
    }

    @Override
    public Result changeStatus(RoleVO roleVO) {
        Role role = new Role();
        role.setId(roleVO.getRoleId());
        role.setStatus(roleVO.getStatus());
        this.updateById(role);
        return Result.okResult();
    }

    @Override
    @Transactional
    public Result addRole(Role role) {
        save(role);
        if (role.getMenuIds() != null && role.getMenuIds().length > 0) {
            insertRoleMenu(role);
        }
        return Result.okResult();
    }

    @Override
    public void updateRole(Role role) {
        updateById(role);
        roleMenuService.deleteRoleMenuByRoleId(role.getId());
        insertRoleMenu(role);
    }

    @Override
    public List<Role> selectRoleAll() {
        return list(Wrappers.<Role>lambdaQuery().eq(Role::getStatus, SystemConstants.NORMAL));
    }

    @Override
    public List<Long> selectRoleIdByUserId(Long userId) {
        return roleMapper.selectRoleIdByUserId(userId);
    }

    private void insertRoleMenu(Role role) {
        List<RoleMenu> roleMenuList = Arrays.stream(role.getMenuIds())
                .map(menuId -> new RoleMenu(role.getId(), menuId))
                .collect(Collectors.toList());
        roleMenuService.saveBatch(roleMenuList);
    }
}
