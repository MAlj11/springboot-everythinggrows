package cn.everythinggrows.boot.egboot.blog.event;

import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisCluster;

public class IndexArticleEvent {
    public static final String INDEX_ARTICLE_AID = "eg/index/article/aid/";

    @Autowired
    private static JedisCluster jedisCluster;

    public static void IndexArticleScore(long aid,double score){
        Double oldScore = jedisCluster.zscore(INDEX_ARTICLE_AID,String.valueOf(aid));
        if(oldScore == null){
            jedisCluster.zadd(INDEX_ARTICLE_AID,oldScore,String.valueOf(aid));
            return;
        }
        double newScore = oldScore + score;
        if(newScore <= 0){
            newScore = 0;
        }
        jedisCluster.zadd(INDEX_ARTICLE_AID,newScore,String.valueOf(aid));
    }

    public static void derArticleScore(long aid,double score){
        score = 0 - score;
        Double oldScore = jedisCluster.zscore(INDEX_ARTICLE_AID,String.valueOf(aid));
        if(oldScore == null){
            jedisCluster.zadd(INDEX_ARTICLE_AID,oldScore,String.valueOf(aid));
            return;
        }
        double newScore = oldScore + score;
        if(newScore <= 0){
            newScore = 0;
        }
        jedisCluster.zadd(INDEX_ARTICLE_AID,newScore,String.valueOf(aid));
    }
}
