package cn.everythinggrows.boot.egboot.forum.service;


import cn.everythinggrows.boot.egboot.forum.config.JedisClusterConfig;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Tuple;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class RedisClientTemplate {

    private static final Logger log=LoggerFactory.getLogger(RedisClientTemplate.class);
    public static String EG_UID_PREFIX = "eg/uid/generation";
    public static String EG_AID_PREFIX = "eg/aid/generation";
    public static final String EG_TID_PREFIX = "eg/tid/generation";

    @Autowired
    private JedisClusterConfig jedisClusterConfig;

    public boolean setToRedis(String key,Object value){
        try {
            String str=jedisClusterConfig.getJedisCluster().set(key, String.valueOf(value));
            if("OK".equals(str))
                return true;
        }catch (Exception ex){
            log.error("setToRedis:{Key:"+key+",value"+value+"}",ex);
        }
        return false;
    }

    public String getRedis(String key){
        String str=null;
        try {
            str=jedisClusterConfig.getJedisCluster().get(key);
        }catch (Exception ex){
            log.error("getRedis:{Key:"+key+"}",ex);
        }
        return str;
    }

    public byte[] getRedisByte(byte[] key){
        byte[] ret = jedisClusterConfig.getJedisCluster().get(key);
        return ret;
    }

    public void setRedisByte(byte[] key,byte[] value){
        jedisClusterConfig.getJedisCluster().set(key,value);
    }

    public void delRedisByte(byte[] key){
        jedisClusterConfig.getJedisCluster().del(key);
    }

    public boolean setex(String key,int seconds,String value){
        String ret = jedisClusterConfig.getJedisCluster().setex(key,seconds,value);
        if("OK".equals(ret)){
            return true;
        }else{
            return false;
        }
    }

    public Map<String,String> hgetAll(String key){
        Map<String,String> ret = jedisClusterConfig.getJedisCluster().hgetAll(key);
        return ret;
    }

    public String hget(String key,String field){
       String value =  jedisClusterConfig.getJedisCluster().hget(key,field);
       return value;
    }

    public void hset(String key,String field,String value){
        jedisClusterConfig.getJedisCluster().hset(key,field,value);
    }


    public boolean expire(String key,int seconds){
        Long str = jedisClusterConfig.getJedisCluster().expire(key,seconds);
        if(str.equals("0") || str==null)
            return true;
        return false;
    }
    public boolean hmset(String key,Map<String,String> map){
        String str = jedisClusterConfig.getJedisCluster().hmset(key,map);
        if("OK".equals(str))
            return true;
        return false;
    }

    public Set<Tuple> zrangeWithScores(String key,long start,long end){
        Set<Tuple> set = jedisClusterConfig.getJedisCluster().zrangeWithScores(key,start,end);
        return set;
    }

    public Double zscore(String key,String member){
        Double score = jedisClusterConfig.getJedisCluster().zscore(key,member);
        return score;
    }

    public void zadd(String key, double score,String member){
        jedisClusterConfig.getJedisCluster().zadd(key,score,member);
    }

    public void lpush(String key,String value){
        jedisClusterConfig.getJedisCluster().lpush(key,value);
    }

    public List<String> lrange(String key,long start,long end){
        List<String> list = jedisClusterConfig.getJedisCluster().lrange(key,start,end);
        return list;
    }

    public void lrem(String key,long count,String value){
        jedisClusterConfig.getJedisCluster().lrem(key,count,value);
    }

    public void hdel(String key,String... fields){
        jedisClusterConfig.getJedisCluster().hdel(key,fields);
    }



    /**
     * id生成
     * @return
     */
    public long incrUid(){
        long uid = 0;
        uid = jedisClusterConfig.getJedisCluster().incr(EG_UID_PREFIX);
        return uid;
    }

    public long aidGeneration(){
        long aid = 0;
        aid = jedisClusterConfig.getJedisCluster().incr(EG_AID_PREFIX);
        return aid;
    }

    public long tidGeneration(){
        long tid = 0;
        tid = jedisClusterConfig.getJedisCluster().incr(EG_TID_PREFIX);
        return tid;
    }

    public long getTidNotIncr(){
        long tid = 0;
        String tidStr = jedisClusterConfig.getJedisCluster().get(EG_TID_PREFIX);
        if(!StringUtils.isEmpty(tidStr)) {
            tid = Long.parseLong(tidStr);
        }
        return tid;
    }
}
