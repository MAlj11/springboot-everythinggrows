package cn.everythinggrows.boot.egboot.user.service;


import cn.everythinggrows.boot.egboot.user.config.JedisClusterConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RedisClientTemplate {

    private static final Logger log=LoggerFactory.getLogger(RedisClientTemplate.class);
    public static String EG_UID_PREFIX = "eg/uid/generation";

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

    public Object getRedis(String key){
        String str=null;
        try {
            str=jedisClusterConfig.getJedisCluster().get(key);
        }catch (Exception ex){
            log.error("getRedis:{Key:"+key+"}",ex);
        }
        return str;
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
    public long incrUid(){
        long uid = 0;
        uid = jedisClusterConfig.getJedisCluster().incr(EG_UID_PREFIX);
        return uid;
    }
}
