package cn.everythinggrows.boot.egboot.admin.dao;


import cn.everythinggrows.boot.egboot.admin.Utils.beanUtils;
import cn.everythinggrows.boot.egboot.admin.datasource.DatabaseContextHolder;
import cn.everythinggrows.boot.egboot.admin.datasource.DatabaseType;
import cn.everythinggrows.boot.egboot.admin.model.Banner;
import cn.everythinggrows.boot.egboot.admin.model.RecommendArticle;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.xml.ws.Action;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class AdminOtherDao {

    @Autowired
    private SqlSessionTemplate adminSqlSession;

    public List<RecommendArticle> getRecommendList(){
        Map<String,Object> dataMap = new HashMap<>();
        DatabaseType type = DatabaseType.getType(0);
        DatabaseContextHolder.setDatabaseType(type);
        List<RecommendArticle> recommendArticles = adminSqlSession.selectList("AdminRecommendArticleDao.selectRecommendArticle", dataMap);
        DatabaseContextHolder.clearDatabaseType();
        return recommendArticles;
    }

    public int insertRecommendArticle(RecommendArticle recommendArticle) {
        Map<String, Object> dataMap = beanUtils.bean2map(recommendArticle);
        dataMap.put("tableName", "eg_recomment_article");
        DatabaseType type = DatabaseType.getType(0);
        DatabaseContextHolder.setDatabaseType(type);
        int i = adminSqlSession.insert("AdminRecommendArticleDao.insertRecommendArticle", dataMap);
        DatabaseContextHolder.clearDatabaseType();
        return i;
    }

    public int deleteRecommend(int id){
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("id", id);
        DatabaseType type = DatabaseType.getType(0);
        DatabaseContextHolder.setDatabaseType(type);
        int i = adminSqlSession.delete("AdminRecommendArticleDao.deleteRecommend", dataMap);
        DatabaseContextHolder.clearDatabaseType();
        return i;
    }

    public List<Banner> getBanner(){
        Map<String,Object> dataMap = new HashMap<>();
        DatabaseType type = DatabaseType.getType(0);
        DatabaseContextHolder.setDatabaseType(type);
        List<Banner> banners = adminSqlSession.selectList("AdminBannerDao.selectBanner", dataMap);
        DatabaseContextHolder.clearDatabaseType();
        return banners;
    }

    public int insertBanner(Banner banner){
        Map<String, Object> dataMap = beanUtils.bean2map(banner);
        dataMap.put("tableName", "eg_banner");
        DatabaseType type = DatabaseType.getType(0);
        DatabaseContextHolder.setDatabaseType(type);
        int i = adminSqlSession.insert("AdminBannerDao.insertBanner", dataMap);
        DatabaseContextHolder.clearDatabaseType();
        return i;
    }

    public int deleteBanner(int id){
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("id", id);
        DatabaseType type = DatabaseType.getType(0);
        DatabaseContextHolder.setDatabaseType(type);
        int i = adminSqlSession.delete("AdminBannerDao.deleteBanner", dataMap);
        DatabaseContextHolder.clearDatabaseType();
        return i;
    }
}
