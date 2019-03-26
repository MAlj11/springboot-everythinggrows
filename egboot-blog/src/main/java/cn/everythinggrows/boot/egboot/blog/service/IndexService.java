package cn.everythinggrows.boot.egboot.blog.service;

import cn.everythinggrows.boot.egboot.blog.Utils.ArticleUtils;
import cn.everythinggrows.boot.egboot.blog.Utils.EgResult;
import cn.everythinggrows.boot.egboot.blog.dao.BannerDao;
import cn.everythinggrows.boot.egboot.blog.dao.IndexDao;
import cn.everythinggrows.boot.egboot.blog.dao.TypeArticleDao;
import cn.everythinggrows.boot.egboot.blog.model.Banner;
import cn.everythinggrows.boot.egboot.blog.model.EgTypeArticle;
import cn.everythinggrows.boot.egboot.blog.model.egArticle;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Tuple;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class IndexService {
    public static final String INDEX_ARTICLE_AID = "eg/index/article/aid/";

    @Autowired
    private RedisClientTemplate redisClientTemplate;
    @Autowired
    private IndexDao indexDao;
    @Autowired
    private BannerDao bannerDao;
    @Autowired
    private TypeArticleDao typeArticleDao;


    public EgResult getArticleList(){
        Set<Tuple> tuples = redisClientTemplate.zrangeWithScores(INDEX_ARTICLE_AID,0,9);
        List<egArticle> articles = Lists.newArrayList();
        for(Tuple tuple : tuples){
            egArticle egArticle = indexDao.getArtcleOne(Long.parseLong(tuple.getElement()));
            egArticle.setTypeString(ArticleUtils.getTypeWithInt(egArticle.getType()));
            articles.add(egArticle);
        }
        HashMap<String,Object> ret = Maps.newHashMap();
        ret.put("articleList",articles);
        return EgResult.ok(ret);
    }

    public EgResult getRecommend(){
        Set<Tuple> tuples = redisClientTemplate.zrangeWithScores(INDEX_ARTICLE_AID,10,14);
        List<egArticle> articles = Lists.newArrayList();
        for(Tuple tuple : tuples){
            egArticle egArticle = indexDao.getArtcleOne(Long.parseLong(tuple.getElement()));
            egArticle.setTypeString(ArticleUtils.getTypeWithInt(egArticle.getType()));
            articles.add(egArticle);
        }
        HashMap<String,Object> ret = Maps.newHashMap();
        ret.put("recommendList",articles);
        return EgResult.ok(ret);
    }


    public EgResult getIndexBanner(){
        List<Banner> bannerList = bannerDao.getBanner();
        Map<String,Object> ret = Maps.newHashMap();
        ret.put("bannerList",bannerList);
        return EgResult.ok(ret);
    }

     public EgResult getArticleWithType(int type){
        List<EgTypeArticle> egTypeArticleList = typeArticleDao.getTypeArticleList(type);
        for(EgTypeArticle egTypeArticle : egTypeArticleList){
            egTypeArticle.setTypeString(ArticleUtils.getTypeWithInt(type));
        }
        Map<String,Object> ret = Maps.newHashMap();
        ret.put("articleWithTypeList",ret);
        return EgResult.ok(ret);
     }



}
