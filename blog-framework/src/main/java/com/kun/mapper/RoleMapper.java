package com.kun.mapper;

import com.kun.domain.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 角色信息表 Mapper 接口
 * </p>
 *
 * @author kun
 * @since 2022-11-19
 */
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 查询用户角色名
     * @param id 用户id
     * @return
     */
    List<String> selectRoleKeyByUserId(@Param("id") Long id);

    List<Long> selectRoleIdByUserId(Long userId);
}
