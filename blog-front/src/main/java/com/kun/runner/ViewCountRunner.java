package com.kun.runner;

import com.kun.constants.RedisKey;
import com.kun.domain.entity.Article;
import com.kun.service.IArticleService;
import com.kun.utils.RedisCache;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author kun
 * @since 2022-11-21 15:52
 */
@Component
public class ViewCountRunner implements CommandLineRunner {

    @Resource
    private IArticleService articleService;
    @Resource
    private RedisCache redisCache;

    @Override
    public void run(String... args) throws Exception {
        //查询博客信息 id view_count
        List<Article> articleList = articleService.list(null);
        Map<String, Integer> map = articleList.stream().collect(Collectors.
                toMap(article -> article.getId().toString(), article -> article.getViewCount().intValue()));
        //存储到redis
        redisCache.setCacheMap(RedisKey.ARTICLE_VIEW_COUNT,map);
    }
}
