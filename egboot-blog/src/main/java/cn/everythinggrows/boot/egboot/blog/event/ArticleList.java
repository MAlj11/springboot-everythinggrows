package cn.everythinggrows.boot.egboot.blog.event;

import cn.everythinggrows.boot.egboot.blog.service.RedisClientTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ArticleList {

    @Autowired
    private RedisClientTemplate redisClientTemplate;

    public static final String EG_ARTICLE_LIST = "eg/boot/blog/article/list";

    public void insertArtilceListRedis(String aid) {
        redisClientTemplate.lpush(EG_ARTICLE_LIST, aid);
    }


    public List<String> getArticleList(long start, long end) {
        List<String> list = redisClientTemplate.lrange(EG_ARTICLE_LIST, start, end);
        return list;
    }

    public void deleteArticle(long aid) {
        redisClientTemplate.lrem(EG_ARTICLE_LIST, 1, String.valueOf(aid));
    }
}
