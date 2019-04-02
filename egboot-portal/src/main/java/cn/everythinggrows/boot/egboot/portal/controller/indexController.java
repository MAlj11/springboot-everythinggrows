package cn.everythinggrows.boot.egboot.portal.controller;

import cn.everythinggrows.boot.egboot.portal.Utils.HttpRequsetUtil;
import cn.everythinggrows.boot.egboot.portal.model.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * @author MA
 */
@Controller
public class indexController {
  private Logger logger = LoggerFactory.getLogger(indexController.class);



    @Value("${BLOG_BASE_URL}")
    String BLOG_BASE_URL;
    @Value("${FORUM_BASE_URL}")
    String FORUM_BASE_URL;

    @RequestMapping("/sindex.html")
    public String getIndex(){
        return "sindex";
    }


    @RequestMapping("/index.html")
    public String getInsexx(HttpServletRequest request,
                            ModelAndView modelAndView){
        HttpSession session = request.getSession();
        logger.info("进入index====================================");
        String url = BLOG_BASE_URL + "/index/article";
        JSONObject json = HttpRequsetUtil.requestGet(url,null);
        String artList = json.getString("articleList");
        List<egArticle> articleList = JSONObject.parseArray(artList, egArticle.class);
        session.setAttribute("articleList",articleList);


        String url2 = BLOG_BASE_URL + "/index/recommend/get";
        JSONObject json2 = HttpRequsetUtil.requestGet(url2,null);
        String recList = json2.getString("recommendList");
        List<RecommendArticle> recommendList = JSONObject.parseArray(recList, RecommendArticle.class);
        session.setAttribute("recommendList",recommendList);


        String url3 = BLOG_BASE_URL + "/index/banner/get";
        JSONObject json3 = HttpRequsetUtil.requestGet(url3,null);
        String banList = json3.getString("bannerList");
        List<Banner> bannerList =  JSONObject.parseArray(banList, Banner.class);
        session.setAttribute("bannerList",bannerList);
        return "lw-index";
    }


    @RequestMapping(value = "/registerPage.html")
    public String getRegisterPage(){
        return "lw-re";
    }

    @RequestMapping(value = "/loginPage.html")
    public String getLoginPage(){
        return "lw-log";
    }

    @RequestMapping(value = "/type/Photography.html")
    public String getPhotography(HttpServletRequest request,
                                 ModelAndView modelAndView){
        HttpSession session = request.getSession();
        String typeUrl1 = BLOG_BASE_URL + "/type/1";
        JSONObject typeJson1 = HttpRequsetUtil.requestGet(typeUrl1,null);
        String phtList = typeJson1.getString("articleWithTypeList");
        List<EgTypeArticle> PhotographyList = JSONObject.parseArray(phtList, EgTypeArticle.class);
        session.setAttribute("PhotographyList",PhotographyList);
        return "lw-Photography";
    }

    @RequestMapping(value = "/type/Internet.html")
    public String getInternet(HttpServletRequest request,
                              ModelAndView modelAndView){
        HttpSession session = request.getSession();
        String typeUrl = BLOG_BASE_URL + "/type/2";
        JSONObject typeJson = HttpRequsetUtil.requestGet(typeUrl,null);
        String phtList = typeJson.getString("articleWithTypeList");
        List<EgTypeArticle> InternetList = JSONObject.parseArray(phtList, EgTypeArticle.class);
        session.setAttribute("InternetList",InternetList);
        return "lw-Internet";
    }

    @RequestMapping(value = "/type/media.html")
    public String getMedia(HttpServletRequest request,
                           ModelAndView modelAndView){
        HttpSession session = request.getSession();
        String typeUrl = BLOG_BASE_URL + "/type/3";
        JSONObject typeJson = HttpRequsetUtil.requestGet(typeUrl,null);
        String phtList = typeJson.getString("articleWithTypeList");
        List<EgTypeArticle> mediaList = JSONObject.parseArray(phtList, EgTypeArticle.class);
        session.setAttribute("mediaList",mediaList);
        return "lw-media";
    }

    @RequestMapping(value = "/type/feeling.html")
    public String getFeeling(HttpServletRequest request,
                             ModelAndView modelAndView){
        HttpSession session = request.getSession();
        String typeUrl = BLOG_BASE_URL + "/type/4";
        JSONObject typeJson = HttpRequsetUtil.requestGet(typeUrl,null);
        String phtList = typeJson.getString("articleWithTypeList");
        List<EgTypeArticle> feelingList = JSONObject.parseArray(phtList, EgTypeArticle.class);
        session.setAttribute("feelingList",feelingList);
        return "lw-feeling";
    }

//    @RequestMapping(value = "/index/article/detail/{aid}")
//    public String getDetailArticle(@PathVariable("aid") long aid,
//                                   HttpServletRequest request){
//        String article = indexService.getDetailArticle(aid);
////        String user = indexService.getUserDetail(uid);
//        HttpSession session = request.getSession();
//        session.setAttribute("articleDetail",article);
////        session.setAttribute("userDetetail",user);
//        List<Comment> comments = articleDetailService.getCommentDetail(aid);
//        session.setAttribute("commentDetail",comments);
//        return "lw-article-fullwidth";
//    }

}
