package com.kun.service.impl;

import com.kun.domain.entity.Tag;
import com.kun.mapper.TagMapper;
import com.kun.service.ITagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
