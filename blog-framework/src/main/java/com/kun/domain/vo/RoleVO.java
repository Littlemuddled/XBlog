package com.kun.domain.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 角色信息表
 * </p>
 *
 * @author kun
 * @since 2022-11-19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleVO implements Serializable {

    private Long roleId;
    private String status;

}
