package cn.everythinggrows.boot.egboot.blog.service;

import cn.everythinggrows.boot.egboot.blog.Utils.ArticleUtils;
import cn.everythinggrows.boot.egboot.blog.Utils.EgResult;
import cn.everythinggrows.boot.egboot.blog.Utils.SerializeUtil;
import cn.everythinggrows.boot.egboot.blog.dao.BannerDao;
import cn.everythinggrows.boot.egboot.blog.dao.IndexDao;
import cn.everythinggrows.boot.egboot.blog.dao.RecommendArticleDao;
import cn.everythinggrows.boot.egboot.blog.dao.TypeArticleDao;
import cn.everythinggrows.boot.egboot.blog.event.ArticleList;
import cn.everythinggrows.boot.egboot.blog.model.Banner;
import cn.everythinggrows.boot.egboot.blog.model.EgTypeArticle;
import cn.everythinggrows.boot.egboot.blog.model.RecommendArticle;
import cn.everythinggrows.boot.egboot.blog.model.egArticle;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class IndexService {
    private static Logger log = LoggerFactory.getLogger(IndexService.class);
    public static final String INDEX_ARTICLE_CACHE = "eg/index/indexarticle/cache";
    public static final String RECOMMEND_ARTICLE_CACHE = "eg/recommendarticle/cache";
    public static final String TYPE_ARTICLE_CACHE = "eg/typearticle/cache";
    public static final String BANNER_CACHE = "eg/banner/cache";

    @Autowired
    private RedisClientTemplate redisClientTemplate;
    @Autowired
    private IndexDao indexDao;
    @Autowired
    private BannerDao bannerDao;
    @Autowired
    private TypeArticleDao typeArticleDao;
    @Autowired
    private ArticleList articleList;
    @Autowired
    private RecommendArticleDao recommendArticleDao;

    @Value("${blog_coverPic_dns}")
    String blog_coverPic_dns;


    public EgResult getArticleList() throws IOException, ClassNotFoundException {
        byte[] data = redisClientTemplate.getRedisByte(INDEX_ARTICLE_CACHE.getBytes());
        List<egArticle> articles = new ArrayList<>();
        if (data == null || data.length == 0) {
            List<String> aidlist = articleList.getArticleList(0, 9);
            for (String aid : aidlist) {
                egArticle egArticle = indexDao.getArtcleOne(Long.parseLong(aid));
                egArticle.setTypeString(ArticleUtils.getTypeWithInt(egArticle.getType()));
                String coverdns = blog_coverPic_dns + egArticle.getCoverPic();
                egArticle.setCoverPic(coverdns);
                articles.add(egArticle);
            }
            redisClientTemplate.setRedisByte(INDEX_ARTICLE_CACHE.getBytes(), SerializeUtil.serialize(articles));
        } else {
            articles = (List<egArticle>) SerializeUtil.deserialize(data);
        }
        HashMap<String, Object> ret = Maps.newHashMap();
        ret.put("articleList", articles);
        return EgResult.ok(ret);
    }

    public EgResult getRecommend() throws IOException, ClassNotFoundException {
        byte[] data = redisClientTemplate.getRedisByte(RECOMMEND_ARTICLE_CACHE.getBytes());
        List<RecommendArticle> list = new ArrayList<>();
        if (data == null || data.length == 0) {
            list = recommendArticleDao.selectRecommendArticleList();
            redisClientTemplate.setRedisByte(RECOMMEND_ARTICLE_CACHE.getBytes(), SerializeUtil.serialize(list));
        } else {
            list = (List<RecommendArticle>) SerializeUtil.deserialize(data);
        }
        HashMap<String, Object> ret = Maps.newHashMap();
        ret.put("recommendList", list);
        return EgResult.ok(ret);
    }

    public EgResult insertRecommendArticle(RecommendArticle recommendArticle) {
        int i = recommendArticleDao.insertRecommendArticle(recommendArticle);
        if (i > 0) {
            redisClientTemplate.delRedisByte(RECOMMEND_ARTICLE_CACHE.getBytes());
            return EgResult.ok();
        } else {
            return EgResult.systemError();
        }
    }

    public EgResult getIndexBanner() throws IOException, ClassNotFoundException {
        byte[] data = redisClientTemplate.getRedisByte(BANNER_CACHE.getBytes());
        List<Banner> bannerList = new ArrayList<>();
        if (data == null || data.length == 0) {
            bannerList = bannerDao.getBanner();
            for (Banner banner : bannerList) {
                String bannerdns = blog_coverPic_dns + banner.getBannerPic();
                banner.setBannerPic(bannerdns);

            }
            redisClientTemplate.setRedisByte(BANNER_CACHE.getBytes(), SerializeUtil.serialize(bannerList));
        } else {
            bannerList = (List<Banner>) SerializeUtil.deserialize(data);
        }
        Map<String, Object> ret = Maps.newHashMap();
        ret.put("bannerList", bannerList);
        return EgResult.ok(ret);
    }

    public EgResult insertBanner(String bannerPic, String bannerTitle, String bannerUrl) {
        Banner banner = new Banner();
        banner.setBannerPic(bannerPic);
        banner.setBannerTitle(bannerTitle);
        banner.setBannerUrl(bannerUrl);
        int i = bannerDao.insertBanner(banner);
        if (i > 0) {
            redisClientTemplate.delRedisByte(BANNER_CACHE.getBytes());
            return EgResult.ok();
        } else {
            return EgResult.systemError();
        }
    }

    public EgResult getArticleWithType(int type) throws IOException, ClassNotFoundException {
        String typeKey = TYPE_ARTICLE_CACHE + String.valueOf(type);
        byte[] data = redisClientTemplate.getRedisByte(typeKey.getBytes());
        List<EgTypeArticle> egTypeArticleList = new ArrayList<>();
        if (data == null || data.length == 0) {
            egTypeArticleList = typeArticleDao.getTypeArticleList(type);
            for (EgTypeArticle egTypeArticle : egTypeArticleList) {
                egTypeArticle.setTypeString(ArticleUtils.getTypeWithInt(type));
                String artidns = blog_coverPic_dns + egTypeArticle.getCoverPic();
                egTypeArticle.setCoverPic(artidns);
            }
            redisClientTemplate.setRedisByte(typeKey.getBytes(), SerializeUtil.serialize(egTypeArticleList));
        } else {
            egTypeArticleList = (List<EgTypeArticle>) SerializeUtil.deserialize(data);
        }
        Map<String, Object> ret = Maps.newHashMap();
        ret.put("articleWithTypeList", egTypeArticleList);
        return EgResult.ok(ret);
    }


}
