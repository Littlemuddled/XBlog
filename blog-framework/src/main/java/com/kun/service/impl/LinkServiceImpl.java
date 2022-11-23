package com.kun.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kun.constants.SystemConstants;
import com.kun.domain.Result;
import com.kun.domain.entity.Link;
import com.kun.domain.vo.LinkVO;
import com.kun.domain.vo.PageVO;
import com.kun.mapper.LinkMapper;
import com.kun.service.ILinkService;
import com.kun.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 友链 服务实现类
 * </p>
 *
 * @author kun
 * @since 2022-11-18
 */
@Service
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements ILinkService {

    @Override
    public Result getAllLink() {
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Link::getStatus, SystemConstants.LINK_STATUS_NORMAL);
        List<Link> linkList = this.list(queryWrapper);
        List<LinkVO> linkVOS = BeanCopyUtils.copyBeanList(linkList, LinkVO.class);

        return Result.okResult(linkVOS);
    }

    @Override
    public PageVO<Link> selectLinkPage(Link link, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.like(StringUtils.hasText(link.getName()),Link::getName, link.getName());
        queryWrapper.eq(Objects.nonNull(link.getStatus()),Link::getStatus, link.getStatus());

        Page<Link> page = new Page<>(pageNum,pageSize);
        this.page(page,queryWrapper);

        //转换成VO
        List<Link> categories = page.getRecords();

        PageVO<Link> pageVo = new PageVO<>(categories,page.getTotal());
        return pageVo;
    }
}
