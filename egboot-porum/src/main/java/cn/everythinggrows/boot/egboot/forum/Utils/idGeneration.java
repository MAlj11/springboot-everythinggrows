package cn.everythinggrows.boot.egboot.forum.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisCluster;

public class idGeneration {
    public static final String TOPIC_TID = "eg/froum/topic/tid";

    @Autowired
    private static JedisCluster jedisCluster;

    public static long getTid(){
        long tid = jedisCluster.incr(TOPIC_TID);
        return tid;
    }

    public static long getTidNotIncr(){
        String tid = jedisCluster.get(TOPIC_TID);
        return Long.parseLong(tid);
    }

}
