package cn.everythinggrows.boot.egboot.portal.service;


import cn.everythinggrows.boot.egboot.portal.Utils.CookieUtils;
import cn.everythinggrows.boot.egboot.portal.Utils.HttpClientUtil;
import cn.everythinggrows.boot.egboot.portal.controller.indexController;
import cn.everythinggrows.boot.egboot.portal.model.egUser;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {
    private Logger logger = LoggerFactory.getLogger(UserService.class);
    @Value("${USER_BASE_URL}")
    String USER_BASE_URL;

    public boolean vertifyTokenToUser(HttpServletRequest request) throws UnsupportedEncodingException {
        String tokenVal = CookieUtils.getCookieValue(request, "eg_cookie_token");
        if (tokenVal == null || tokenVal.length() == 0) {
            return false;
        }
        String tokenverUrl = USER_BASE_URL + "/token/vertify";
        tokenVal = URLDecoder.decode(tokenVal, "utf-8");
        logger.info("service tokenVal:{}", tokenVal);
        Map<String, String> postParam = new HashMap<>();
        postParam.put("token", tokenVal);
        String ret = HttpClientUtil.doPost(tokenverUrl, postParam);
        JSONObject tokenJson = JSON.parseObject(ret);
        Map dataMap = JSONObject.toJavaObject(tokenJson, Map.class);
        if ((Integer) dataMap.get("status") == 200) {
            return true;
        } else {
            return false;
        }
    }

    public egUser getUser(long uid) {

        String url = USER_BASE_URL + "/detail/" + String.valueOf(uid);
        String ret = HttpClientUtil.doGet(url);
        logger.info("user:{}",ret);
        JSONObject json = JSON.parseObject(ret);
        egUser user = JSONObject.toJavaObject(json, egUser.class);
        return user;

    }
}
