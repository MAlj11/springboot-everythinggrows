package cn.everythinggrows.boot.egboot.user.service;

import cn.everythinggrows.boot.egboot.user.dubboapi.idGenreate;
import org.springframework.beans.factory.annotation.Autowired;

public class idGenreateImpl implements idGenreate {

    @Autowired
    private RedisClientTemplate redisClientTemplate;

    @Override
    public long uidGeneration() {
        long uid = redisClientTemplate.incrUid();
        return uid;
    }
}
