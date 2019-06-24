package cn.everythinggrows.boot.egboot.forum.service;


import cn.everythinggrows.boot.egboot.forum.Utils.HttpClientUtil;
import cn.everythinggrows.boot.egboot.forum.model.egUser;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class HttpRequestToUser {
    private static Logger log = LoggerFactory.getLogger(HttpRequestToUser.class);
    @Value("${USER_BASE_URL}")
    String userUrl;

    public egUser getUser(long uid) {

        String url = userUrl + "/detail/" + String.valueOf(uid);
        String ret = HttpClientUtil.doGet(url);
        JSONObject json = JSON.parseObject(ret);
        egUser user = JSONObject.toJavaObject(json, egUser.class);
        return user;


    }



}
