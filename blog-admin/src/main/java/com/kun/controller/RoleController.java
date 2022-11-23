package com.kun.controller;

import com.kun.domain.Result;
import com.kun.domain.entity.Role;
import com.kun.domain.vo.RoleVO;
import com.kun.service.IRoleService;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 角色信息表 前端控制器
 * </p>
 *
 * @author kun
 * @since 2022-11-19
 */
@Controller
@RequestMapping("/system/role")
public class RoleController {

    @Resource
    private IRoleService roleService;

    @GetMapping("list")
    public Result list(Role role, Integer pageNum, Integer pageSize) {
        return roleService.getAllRole(role,pageNum,pageSize);
    }

    @GetMapping("/listAllRole")
    public Result listAllRole() {
        List<Role> roles = roleService.selectRoleAll();
        return Result.okResult(roles);
    }

    @PutMapping("/changeStatus")
    public Result changeStatus(@RequestBody RoleVO roleVO) {
        return roleService.changeStatus(roleVO);
    }

    @PostMapping
    public Result addRole(@RequestBody Role role) {
        return roleService.addRole(role);
    }

    @GetMapping(value = "/{roleId}")
    public Result getInfo(@PathVariable Long roleId) {
        Role role = roleService.getById(roleId);
        return Result.okResult(role);
    }

    @PutMapping
    public Result updateRole(@RequestBody Role role) {
        roleService.updateRole(role);
        return Result.okResult();
    }

    @DeleteMapping("/{id}")
    public Result remove(@PathVariable(name = "id") Long id) {
        roleService.removeById(id);
        return Result.okResult();
    }

}
