package com.kun.job;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.kun.constants.RedisKey;
import com.kun.domain.entity.Article;
import com.kun.enums.AppHttpCodeEnum;
import com.kun.exception.SystemException;
import com.kun.service.IArticleService;
import com.kun.utils.RedisCache;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author kun
 * @since 2022-11-21 16:57
 */
@Component
public class UpdateViewCountJob {

    @Resource
    private RedisCache redisCache;
    @Resource
    private IArticleService articleService;

    @Scheduled(cron = "0/30 * * * * ?")
    public void updateViewCount() {
        Map<String, Integer> map = redisCache.getCacheMap(RedisKey.ARTICLE_VIEW_COUNT);
        Set<Map.Entry<String, Integer>> entrySet = map.entrySet();
        List<Article> list = entrySet.stream()
                .map(entry -> new Article(Long.valueOf(entry.getKey()), entry.getValue().longValue()))
                .collect(Collectors.toList());

        LambdaUpdateWrapper<Article> updateWrapper = new LambdaUpdateWrapper<>();
        for (Article article : list) {
            updateWrapper.eq(Article::getId,article.getId()).set(Article::getViewCount,article.getViewCount());
            articleService.update(updateWrapper);
        }
    }

    public void update() {
        Map<String, Integer> map = redisCache.getCacheMap(RedisKey.ARTICLE_VIEW_COUNT);
        Set<Map.Entry<String, Integer>> entrySet = map.entrySet();
        List<Article> list = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : entrySet) {
            Long id = Long.valueOf(entry.getKey());
            Long count = entry.getValue().longValue();
            Article article = new Article(id, count);
            list.add(article);
        }
        articleService.updateBatchById(list);
    }
}
