package com.kun.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kun.domain.Result;
import com.kun.domain.dto.TagDto;
import com.kun.domain.entity.Tag;

import java.util.List;

/**
 * <p>
 * 标签 服务类
 * </p>
 *
 * @author kun
 * @since 2022-11-18
 */
public interface ITagService extends IService<Tag> {

    /**
     * 分页查询标签列表
     * @param pageNum 当前页
     * @param pageSize 每页记录数
     * @param tagDto
     * @return
     */
    Result pageTagList(Integer pageNum, Integer pageSize, TagDto tagDto);

    Result addTag(Tag tag);

    Result deleteTag(Long id);

    Result getTagById(Long id);

    Result updateTag(Tag tag);

    Result listAllTag();

}
