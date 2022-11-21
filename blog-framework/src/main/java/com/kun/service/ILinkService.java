package com.kun.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kun.domain.Result;
import com.kun.domain.entity.Link;

/**
 * <p>
 * 友链 服务类
 * </p>
 *
 * @author kun
 * @since 2022-11-18
 */
public interface ILinkService extends IService<Link> {

    /**
     * 获取所有审核通过的友链
     * @return
     */
    Result getAllLink();

}
