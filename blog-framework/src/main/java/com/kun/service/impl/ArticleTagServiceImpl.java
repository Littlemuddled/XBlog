package com.kun.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kun.domain.entity.ArticleTag;
import com.kun.mapper.ArticleTagMapper;
import com.kun.service.IArticleTagService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 文章标签关联表 服务实现类
 * </p>
 *
 * @author kun
 * @since 2022-11-18
 */
@Service
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements IArticleTagService {

}
