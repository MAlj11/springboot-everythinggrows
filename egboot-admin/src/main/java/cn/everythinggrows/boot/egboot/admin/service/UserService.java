package cn.everythinggrows.boot.egboot.admin.service;


import cn.everythinggrows.boot.egboot.admin.Utils.HttpClientUtil;
import cn.everythinggrows.boot.egboot.admin.model.egUser;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserService {
    private Logger logger = LoggerFactory.getLogger(UserService.class);
    @Value("${USER_BASE_URL}")
    String USER_BASE_URL;

    public egUser getUser(long uid){

        String url = USER_BASE_URL + "/detail/" + String.valueOf(uid);
        String ret = HttpClientUtil.doGet(url);
        JSONObject json = JSON.parseObject(ret);
        Map dataMap = JSONObject.toJavaObject(json, Map.class);
        egUser user = new egUser();
        if((Integer)dataMap.get("status")==200){
            JSONObject userdetailStr = (JSONObject) dataMap.get("data");
            Map userdetailMap = JSONObject.toJavaObject(userdetailStr,Map.class);
            JSONObject userStr = (JSONObject) userdetailMap.get("userDetail");
            user = JSONObject.toJavaObject(userStr,egUser.class);
        }
        return user;

    }
}
