package cn.everythinggrows.boot.egboot.portal.controller;

import cn.everythinggrows.boot.egboot.portal.Utils.HttpRequsetUtil;
import cn.everythinggrows.boot.egboot.portal.model.Banner;
import cn.everythinggrows.boot.egboot.portal.model.EgTypeArticle;
import cn.everythinggrows.boot.egboot.portal.model.egArticle;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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

        String url = BLOG_BASE_URL + "/index/article";
        JSONObject json = HttpRequsetUtil.requestGet("articleList",url,null);
        List<egArticle> articleList = JSONObject.toJavaObject(json, List.class);
        modelAndView.addObject("articleList",articleList);


        String url2 = BLOG_BASE_URL + "/index/recommend/get";
        JSONObject json2 = HttpRequsetUtil.requestGet("recommendList",url2,null);
        List<egArticle> recommendList = JSONObject.toJavaObject(json2, List.class);
        modelAndView.addObject("recommendList",recommendList);


        String url3 = BLOG_BASE_URL + "/index/banner/get";
        JSONObject json3 = HttpRequsetUtil.requestGet("bannerList",url3,null);
        List<Banner> bannerList =  JSONObject.toJavaObject(json3, List.class);
        modelAndView.addObject("bannerList",bannerList);
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
        String typeUrl1 = BLOG_BASE_URL + "/type/1";
        JSONObject typeJson1 = HttpRequsetUtil.requestGet("articleWithTypeList",typeUrl1,null);
        List<EgTypeArticle> PhotographyList = JSONObject.toJavaObject(typeJson1, List.class);
        modelAndView.addObject("PhotographyList",PhotographyList);
        return "lw-Photography";
    }

    @RequestMapping(value = "/type/Internet.html")
    public String getInternet(HttpServletRequest request,
                              ModelAndView modelAndView){
        String typeUrl = BLOG_BASE_URL + "/type/2";
        JSONObject typeJson = HttpRequsetUtil.requestGet("articleWithTypeList",typeUrl,null);
        List<EgTypeArticle> InternetList = JSONObject.toJavaObject(typeJson, List.class);
        modelAndView.addObject("InternetList",InternetList);
        return "lw-Internet";
    }

    @RequestMapping(value = "/type/media.html")
    public String getMedia(HttpServletRequest request,
                           ModelAndView modelAndView){
        String typeUrl = BLOG_BASE_URL + "/type/3";
        JSONObject typeJson = HttpRequsetUtil.requestGet("mediaList",typeUrl,null);
        List<EgTypeArticle> mediaList = JSONObject.toJavaObject(typeJson, List.class);
        modelAndView.addObject("mediaList",mediaList);
        return "lw-media";
    }

    @RequestMapping(value = "/type/feeling.html")
    public String getFeeling(HttpServletRequest request,
                             ModelAndView modelAndView){
        String typeUrl = BLOG_BASE_URL + "/type/4";
        JSONObject typeJson = HttpRequsetUtil.requestGet("feelingList",typeUrl,null);
        List<EgTypeArticle> feelingList = JSONObject.toJavaObject(typeJson, List.class);
        modelAndView.addObject("feelingList",feelingList);
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
