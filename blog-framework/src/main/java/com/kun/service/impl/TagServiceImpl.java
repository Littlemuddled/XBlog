package com.kun.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kun.domain.Result;
import com.kun.domain.dto.TagDto;
import com.kun.domain.entity.Tag;
import com.kun.domain.vo.PageVO;
import com.kun.domain.vo.TagVO;
import com.kun.domain.vo.TagVO2;
import com.kun.enums.AppHttpCodeEnum;
import com.kun.exception.SystemException;
import com.kun.mapper.TagMapper;
import com.kun.service.ITagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kun.utils.BeanCopyUtils;
import com.kun.utils.SecurityUtils;
import org.aspectj.weaver.ast.Var;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 标签 服务实现类
 * </p>
 *
 * @author kun
 * @since 2022-11-18
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements ITagService {

    @Override
    public Result pageTagList(Integer pageNum, Integer pageSize, TagDto tagDto) {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.hasText(tagDto.getName()),Tag::getName,tagDto.getName());
        queryWrapper.eq(StringUtils.hasText(tagDto.getRemark()),Tag::getRemark,tagDto.getRemark());

        Page<Tag> page = new Page<>(pageNum,pageSize);
        Page<Tag> tagPage = this.page(page, queryWrapper);

        PageVO<Tag> tagPageVO = new PageVO<>(tagPage.getRecords(), tagPage.getTotal());

        return Result.okResult(tagPageVO);
    }

    @Override
    public Result addTag(Tag tag) {
        if (!(StringUtils.hasText(tag.getName()) && StringUtils.hasText(tag.getRemark()))) {
            throw new SystemException(AppHttpCodeEnum.CONTENT_NOT_NULL);
        }
        boolean b = this.save(tag);
        if (!b) {
            throw new SystemException(AppHttpCodeEnum.TAG_SAVE_FAIL);
        }
        return Result.okResult();
    }

    @Override
    public Result deleteTag(Long id) {
        boolean b = this.removeById(id);
        if (!b) {
            throw new SystemException(AppHttpCodeEnum.DELETE_FAIL);
        }
        return Result.okResult();
    }

    @Override
    public Result getTagById(Long id) {
        Tag tag = this.getById(id);
        TagVO tagVO = BeanCopyUtils.copyBean(tag, TagVO.class);
        return Result.okResult(tagVO);
    }

    @Override
    public Result updateTag(Tag tag) {
        this.updateById(tag);
        return Result.okResult();
    }

    @Override
    public Result listAllTag() {
        List<Tag> tagList = this.list();
        List<TagVO2> tagVO2s = BeanCopyUtils.copyBeanList(tagList, TagVO2.class);
        return Result.okResult(tagVO2s);
    }




}
