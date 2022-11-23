package com.kun.mapper;

import com.kun.domain.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kun.domain.vo.MenuVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 菜单权限表 Mapper 接口
 * </p>
 *
 * @author kun
 * @since 2022-11-19
 */
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 根据用户id查询权限名字
     * @param id
     * @return
     */
    List<String> selectPermsByUserId(@Param("id") Long id);

    /**
     * 根据用户id查询权限信息
     * @param userId
     * @return
     */
    List<MenuVO> selectRouterMenuTreeByUserId(@Param("userId") Long userId);

    /**
     * 根据角色id查询对应的菜单列表
     * @param roleId
     * @return
     */
    List<Long> selectMenuListByRoleId(Long roleId);
}
