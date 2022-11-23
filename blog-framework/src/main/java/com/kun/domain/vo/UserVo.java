package com.kun.domain.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author kun
 * @since 2022-11-23 21:55
 */
@Data
@Accessors(chain = true)
public class UserVo {

    private Long id;
    private String userName;
    private String nickName;
    private String status;
    private String email;
    private String phonenumber;
    private String sex;
    private String avatar;
    private Long createBy;
    private LocalDateTime createTime;
    private Long updateBy;
    private LocalDateTime updateTime;
}
