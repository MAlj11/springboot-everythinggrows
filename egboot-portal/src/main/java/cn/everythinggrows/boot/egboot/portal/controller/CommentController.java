package cn.everythinggrows.boot.egboot.portal.controller;


import cn.everythinggrows.boot.egboot.portal.Utils.CookieUtils;
import cn.everythinggrows.boot.egboot.portal.Utils.HttpClientUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

@Controller
public class CommentController {

    @Value("${BLOG_BASE_URL}")
    String BLOG_BASE_URL;

    @RequestMapping(value = "/article/comment")
    public String publishComment(@RequestParam(value = "content") String content,
                                 @RequestParam(value = "aid") long aid,
                                 HttpServletRequest request) throws UnsupportedEncodingException {
        String insertCommentUrl = BLOG_BASE_URL + "/comment/insert";
        Map<String, String> param = new HashMap<>();
        param.put("aid", String.valueOf(aid));
        param.put("content", content);
        String tokenVal = CookieUtils.getCookieValue(request, "eg_cookie_token");
        tokenVal = URLDecoder.decode(tokenVal, "utf-8");
        String ret = HttpClientUtil.doPostWithHander(insertCommentUrl, param, "x-eg-session", tokenVal);
        JSONObject json = JSON.parseObject(ret);
        Map dataMap = JSONObject.toJavaObject(json, Map.class);
        if ((Integer) dataMap.get("status") == 200) {
            HttpSession session = request.getSession();
            session.setAttribute("articleid",aid);
            return "commentSuccess";
        }
        return "lw-article-fullwidth";
    }
}
