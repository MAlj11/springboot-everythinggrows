package cn.everythinggrows.boot.egboot.blog.dao;


import cn.everythinggrows.boot.egboot.blog.Utils.beanUtils;
import cn.everythinggrows.boot.egboot.blog.datasource.DatabaseContextHolder;
import cn.everythinggrows.boot.egboot.blog.datasource.DatabaseType;
import cn.everythinggrows.boot.egboot.blog.model.RecommendArticle;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class RecommendArticleDao {
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    public int insertRecommendArticle(RecommendArticle recommendArticle) {
        Map<String, Object> dataMap = beanUtils.bean2map(recommendArticle);
        dataMap.put("tableName", "eg_recomment_article");
        DatabaseType type = DatabaseType.getType(0);
        DatabaseContextHolder.setDatabaseType(type);
        int i = sqlSessionTemplate.insert("RecommendArticleDao.insertRecommendArticle", dataMap);
        DatabaseContextHolder.clearDatabaseType();
        return i;
    }

    public List<RecommendArticle> selectRecommendArticleList() {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("tableName", "eg_recomment_article");
        DatabaseType type = DatabaseType.getType(0);
        DatabaseContextHolder.setDatabaseType(type);
        List<RecommendArticle> list = sqlSessionTemplate.selectList("RecommendArticleDao.selectRecommendArticle");
        DatabaseContextHolder.clearDatabaseType();
        return list;
    }
}
