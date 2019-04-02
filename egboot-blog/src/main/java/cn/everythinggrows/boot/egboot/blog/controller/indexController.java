package cn.everythinggrows.boot.egboot.blog.controller;

import cn.everythinggrows.boot.egboot.blog.Utils.EgResult;
import cn.everythinggrows.boot.egboot.blog.model.RecommendArticle;
import cn.everythinggrows.boot.egboot.blog.service.IndexService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * Auther MA
 */

@RestController
public class indexController {
    private static Logger log = LoggerFactory.getLogger(indexController.class);
    @Autowired
    private IndexService indexService;
    @Value("${blog_coverPic_dns}")
    String blog_coverPic_dns;


    /**首页文章
     */
    @RequestMapping(value = "/blog/index/article", method = RequestMethod.GET)
    public EgResult getArticleList() throws IOException, ClassNotFoundException {
        EgResult ret = indexService.getArticleList();
        return ret;
    }


    /**首页右边推荐
     *
     * @return
     */
    @RequestMapping(value = "/blog/index/recommend/get", method = RequestMethod.GET)
    public EgResult getRecommend() throws IOException, ClassNotFoundException {
        EgResult ret = indexService.getRecommend();
        return ret;
    }

    /**
     * 插入推荐
     * CMS
     */
    @RequestMapping(value = "/blog/index/recommend/insert",method = RequestMethod.POST)
    public EgResult insertRecommend(@RequestParam(value = "aid") long aid,
                                    @RequestParam(value = "articleName",defaultValue = "") String articleName,
                                    @RequestParam(value = "title",defaultValue = "") String title,
                                    @RequestParam(value = "coverPic",defaultValue = "") String coverPic,
                                    @RequestParam(value = "type",defaultValue = "1") int type,
                                    @RequestParam(value = "uid") long uid,
                                    @RequestParam(value = "typeString",defaultValue = "摄影") String typeString){
        RecommendArticle recommendArticle = new RecommendArticle();
        recommendArticle.setAid(aid);
        recommendArticle.setArticleName(articleName);
        recommendArticle.setTitle(title);
        recommendArticle.setCoverPic(coverPic);
        recommendArticle.setType(type);
        recommendArticle.setUid(uid);
        recommendArticle.setTypeString(typeString);
        return indexService.insertRecommendArticle(recommendArticle);

    }


    /**
     * 首页banner
     */
    @RequestMapping(value = "/blog/index/banner/get", method = RequestMethod.GET)
    public EgResult getBanner() throws IOException, ClassNotFoundException {
      EgResult ret = indexService.getIndexBanner();
      return ret;
    }

    /**
     * 插入banner
     */
    @RequestMapping(value = "/blog/index/banner/insert",method = RequestMethod.POST)
    public EgResult insertBanner(@RequestParam(value = "bannerPic") String bannerPic,
                                 @RequestParam(value = "bannerTitle") String bannerTitle,
                                 @RequestParam(value = "bannerUrl") String bannerUrl){
       return indexService.insertBanner(bannerPic,bannerTitle,bannerUrl);
    }

    /**
     * 分类文章
     */
    @RequestMapping(value = "/blog/type/{typeId}")
    public EgResult getTypeArticle(@PathVariable(value = "typeId") int typeId) throws IOException, ClassNotFoundException {
      if(typeId == 0){
          EgResult retIndex = indexService.getArticleList();
          return retIndex;
      }
     EgResult ret = indexService.getArticleWithType(typeId);
      return ret;
    }

}
