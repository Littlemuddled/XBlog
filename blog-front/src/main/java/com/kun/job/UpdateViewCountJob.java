package com.kun.job;

import com.kun.constants.RedisKey;
import com.kun.domain.entity.Article;
import com.kun.enums.AppHttpCodeEnum;
import com.kun.exception.SystemException;
import com.kun.service.IArticleService;
import com.kun.utils.RedisCache;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
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
        boolean b = articleService.updateBatchById(list);
        if (!b) {
            throw new SystemException(AppHttpCodeEnum.UPDATE_VIEW_COUNT_ERROR);
        }
    }
}
