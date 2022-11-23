package com.kun.service.impl;

import com.kun.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author kun
 * @since 2022-11-23 16:04
 */
@Service("os")
public class PermissionServiceImpl {

    /**
     * 判断当前用户是否具有权限
     * @param perm 要判断的权限
     * @return
     */
    public boolean hasPermission(String perm) {
        //如果是超级管理员,直接返回true
        Long userId = SecurityUtils.getUserId();
        if(userId.equals(1L)) {
            return true;
        }
        List<String> permissions = SecurityUtils.getLoginUser().getPermissions();
        return permissions.contains(perm);
    }
}
