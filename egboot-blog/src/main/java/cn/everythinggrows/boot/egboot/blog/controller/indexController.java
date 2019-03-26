package cn.everythinggrows.boot.egboot.blog.controller;

import cn.everythinggrows.boot.egboot.blog.Utils.EgResult;
import cn.everythinggrows.boot.egboot.blog.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Auther MA
 */

@RestController
public class indexController {
    @Autowired
    private IndexService indexService;


    /**首页文章
     */
    @RequestMapping(value = "/blog/index/article", method = RequestMethod.GET)
    public EgResult getArticleList(){
        EgResult ret = indexService.getArticleList();
        return ret;
    }


    /**首页右边推荐
     *
     * @return
     */
    @RequestMapping(value = "blog/index/recommend", method = RequestMethod.GET)
    public EgResult getRecommend(){
        EgResult ret = indexService.getRecommend();
        return ret;
    }

    /**
     * 首页banner
     */
    @RequestMapping(value = "blog/index/banner", method = RequestMethod.GET)
    public EgResult getBanner(){
      EgResult ret = indexService.getIndexBanner();
      return ret;
    }

    /**
     * 分类文章
     */
    @RequestMapping(value = "/blog/type/{typeId}")
    public EgResult getTypeArticle(@PathVariable(value = "typeId") int typeId){
      if(typeId == 0){
          EgResult retIndex = indexService.getArticleList();
          return retIndex;
      }
     EgResult ret = indexService.getArticleWithType(typeId);
      return ret;
    }

}
