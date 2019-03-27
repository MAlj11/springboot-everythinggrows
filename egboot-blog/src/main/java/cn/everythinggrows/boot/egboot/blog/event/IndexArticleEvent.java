package cn.everythinggrows.boot.egboot.blog.event;

import cn.everythinggrows.boot.egboot.blog.service.RedisClientTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public class IndexArticleEvent {
    public static final String INDEX_ARTICLE_AID = "eg/index/article/aid/";

    @Autowired
    private static RedisClientTemplate redisClientTemplate;

    public static void IndexArticleScore(long aid,double score){
        Double oldScore = redisClientTemplate.zscore(INDEX_ARTICLE_AID,String.valueOf(aid));
        if(oldScore == null){
            redisClientTemplate.zadd(INDEX_ARTICLE_AID,1,String.valueOf(aid));
            return;
        }
        double newScore = oldScore + score;
        if(newScore <= 0){
            newScore = 0;
        }
        redisClientTemplate.zadd(INDEX_ARTICLE_AID,newScore,String.valueOf(aid));
    }

    public static void derArticleScore(long aid,double score){
        score = 0 - score;
        Double oldScore = redisClientTemplate.zscore(INDEX_ARTICLE_AID,String.valueOf(aid));
        if(oldScore == null){
            redisClientTemplate.zadd(INDEX_ARTICLE_AID,oldScore,String.valueOf(aid));
            return;
        }
        double newScore = oldScore + score;
        if(newScore <= 0){
            newScore = 0;
        }
        redisClientTemplate.zadd(INDEX_ARTICLE_AID,newScore,String.valueOf(aid));
    }
}
