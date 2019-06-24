package cn.everythinggrows.boot.egboot.admin.Controller;

import cn.everythinggrows.boot.egboot.admin.dao.AdminOtherDao;
import cn.everythinggrows.boot.egboot.admin.model.Banner;
import cn.everythinggrows.boot.egboot.admin.model.RecommendArticle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class OtherController {

    @Autowired
    private AdminOtherDao adminOtherDao;

    @RequestMapping("/admin/index/recommend")
    public String setRecommend(HttpServletRequest request){
        HttpSession session = request.getSession();
        List<RecommendArticle> recommendArticles = adminOtherDao.getRecommendList();
        session.setAttribute("recommendList",recommendArticles);
        return "lw-admin-recommend";
    }

    @RequestMapping("/admin/recommend/add")
    public String addRecommend(@RequestParam("recommendaid") long aid,
                               @RequestParam("articleName") String articleName,
                               @RequestParam("title") String title,
                               @RequestParam("coverPic") String coverPic,
                               @RequestParam("artType") int type,
                               @RequestParam("recommenduid") long uid,
                               @RequestParam("typeString") String tyeString){
      RecommendArticle recommendArticle = new RecommendArticle();
      recommendArticle.setAid(aid);
      recommendArticle.setArticleName(articleName);
      recommendArticle.setTitle(title);
      recommendArticle.setCoverPic(coverPic);
      recommendArticle.setType(type);
      recommendArticle.setUid(uid);
      recommendArticle.setTypeString(tyeString);
      adminOtherDao.insertRecommendArticle(recommendArticle);
      return "operateSuccess";
    }

    @RequestMapping("/admin/recommend/delete/{id}")
    public String delRecommend(@PathVariable("id") int id){
     adminOtherDao.deleteRecommend(id);
     return "operateSuccess";
    }

    @RequestMapping("/admin/index/banner")
    public String setBanner(HttpServletRequest request){
        HttpSession session = request.getSession();
        List<Banner> banners = adminOtherDao.getBanner();
        session.setAttribute("bannerList",banners);
        return "lw-admin-banner";
    }

    @RequestMapping("/admin/banner/add")
    public String addBanner(@RequestParam("bannerPic") String bannerPic,
                            @RequestParam("bannerTitle") String bannerTitle,
                            @RequestParam("bannerUrl") String bannerUrl){
        Banner banner = new Banner();
        banner.setBannerPic(bannerPic);
        banner.setBannerTitle(bannerTitle);
        banner.setBannerUrl(bannerUrl);
        adminOtherDao.insertBanner(banner);
        return "bannerSuccess";
    }

    @RequestMapping("/admin/banner/delete/{id}")
    public String deleteBanner(@PathVariable("id") int id){
        adminOtherDao.deleteBanner(id);
        return "bannerSuccess";
    }

}
