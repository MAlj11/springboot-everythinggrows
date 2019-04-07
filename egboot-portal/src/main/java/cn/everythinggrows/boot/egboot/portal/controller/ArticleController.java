package cn.everythinggrows.boot.egboot.portal.controller;

import cn.everythinggrows.boot.egboot.portal.Utils.CookieUtils;
import cn.everythinggrows.boot.egboot.portal.Utils.HttpClientUtil;
import cn.everythinggrows.boot.egboot.portal.Utils.HttpRequsetUtil;
import cn.everythinggrows.boot.egboot.portal.model.EgTypeArticle;
import cn.everythinggrows.boot.egboot.portal.model.egUidArticle;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ArticleController {
    private Logger logger = LoggerFactory.getLogger(ArticleController.class);
    @Value("${BLOG_BASE_URL}")
    String BLOG_BASE_URL;

    @RequestMapping(value = "/writearticle.html")
    public String writeArticle(HttpServletRequest request){
        String tokenVal = CookieUtils.getCookieValue(request,"eg_cookie_token");
        if(tokenVal == null || tokenVal.length() == 0){
            return "lw-log";
        }
        return "lw-write";
    }

    @RequestMapping(value = "/blog/article/publish.html", method = RequestMethod.POST)
    public String writePublic(@RequestParam(value = "articleName") String articleName,
                              @RequestParam(value = "title") String title,
                              @RequestParam(value = "content") String content,
                              @RequestParam(value = "type") int type,
                              HttpServletRequest request) throws UnsupportedEncodingException {

        String insertArtUrl = BLOG_BASE_URL + "/article/insert";
        Map<String,String> param = new HashMap<>();
        param.put("articleName",articleName);
        param.put("title",title);
        param.put("type",String.valueOf(type));
        param.put("content",content);
        String tokenVal = CookieUtils.getCookieValue(request,"eg_cookie_token");
        tokenVal = URLDecoder.decode(tokenVal,"utf-8");
        String ret = HttpClientUtil.doPostWithHander(insertArtUrl,param,"x-eg-session",tokenVal);
        JSONObject json = JSON.parseObject(ret);
        Map dataMap = JSONObject.toJavaObject(json, Map.class);
        if((Integer)dataMap.get("status")==200){
            return "publishSuccess";
        }
        return "lw-write";
    }

    //todo 前端增加type字段的选项


    @RequestMapping(value = "/myArticle.html")
    public String getMyArticle(HttpServletRequest request) throws UnsupportedEncodingException {
        String tokenVal = CookieUtils.getCookieValue(request,"eg_cookie_token");
        if(tokenVal == null || tokenVal.length() == 0){
            return "lw-log";
        }
        tokenVal = URLDecoder.decode(tokenVal,"utf-8");
        HttpSession session = request.getSession();
        long uid = getUid(tokenVal);
        String myArtUrl = BLOG_BASE_URL + "/article/myarticle/" + String.valueOf(uid);
        JSONObject myartJson = HttpRequsetUtil.requestGet(myArtUrl,null);
        String myartList = myartJson.getString("egUidArticles");
        List<egUidArticle> egUidArticleList = JSONObject.parseArray(myartList, egUidArticle.class);
        logger.info("myarticle:{}",egUidArticleList);
        session.setAttribute("myArticles",egUidArticleList);
        return "lw-myArticle";
    }

    @RequestMapping("/delete/article/{aid}")
    public String deleteArticle(@PathVariable(value = "aid") long aid,
                                HttpServletRequest request) throws UnsupportedEncodingException {
        String tokenVal = CookieUtils.getCookieValue(request,"eg_cookie_token");
        if(tokenVal == null || tokenVal.length() == 0){
            return "lw-log";
        }
        tokenVal = URLDecoder.decode(tokenVal,"utf-8");
        String delUrl = BLOG_BASE_URL + "/article/delete/" + String.valueOf(aid);
        String ret = HttpClientUtil.doGetWithHander(delUrl,null,"x-eg-session",tokenVal);
        JSONObject json = JSON.parseObject(ret);
        Map dataMap = JSONObject.toJavaObject(json, Map.class);
        if((Integer)dataMap.get("status")==200){
            return "delSuccess";
        }
        return "lw-myArticle";
    }

    public long getUid(String session){
        if(session == null || session.length() == 0){
            return 0;
        }
        String[] line = session.split(";");
        long uid = Long.parseLong(line[0]);
        return uid;
    }
}
