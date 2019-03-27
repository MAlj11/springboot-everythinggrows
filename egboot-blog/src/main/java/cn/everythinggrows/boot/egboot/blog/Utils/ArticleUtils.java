package cn.everythinggrows.boot.egboot.blog.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisCluster;

public class ArticleUtils {
    public static final String COMMENT_ID = "eg/blog/comment/id";
    @Autowired
    private static JedisCluster jedisCluster;
    public static String getTypeWithInt(int type){
        switch(type){
            case 1:
                return "摄影";
            case 2:
                return "互联网";
            case 3:
                return "影音";
            case 4:
                return "感悟";
            default:
                return "分类";
        }
    }

    public static long getCommentId(){
        //todo 生成commitid
        long cid = jedisCluster.incr(COMMENT_ID);
        return cid;
    }

}
