package cn.everythinggrows.boot.egboot.blog.dao;


import cn.everythinggrows.boot.egboot.blog.Utils.DBUtils;
import cn.everythinggrows.boot.egboot.blog.Utils.beanUtils;
import cn.everythinggrows.boot.egboot.blog.datasource.DatabaseContextHolder;
import cn.everythinggrows.boot.egboot.blog.datasource.DatabaseType;
import cn.everythinggrows.boot.egboot.blog.model.egUidArticle;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UidArticleDao {
    @Autowired
    private SqlSessionTemplate blogSqlSession;


    public int insertUidArticle(egUidArticle egUidArticle){
            Map<String,Object> dataMap = beanUtils.bean2map(egUidArticle);
            long uid = egUidArticle.getUid();
            dataMap.put("tableName", "eg_uid_article_alt_" + DBUtils.getTableKey(uid));
            int DBkey = DBUtils.getDBKey(uid);
            DatabaseType type = DatabaseType.getType(DBkey);
            DatabaseContextHolder.setDatabaseType(type);
            int i = blogSqlSession.insert("UidArticleDao.insertUidArticle", dataMap);
            DatabaseContextHolder.clearDatabaseType();
            return i;
    }

    public List<egUidArticle> selectArticles(long uid){
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("tableName", "eg_uid_article_alt_" + DBUtils.getTableKey(uid));
        dataMap.put("uid",uid);
        int DBkey = DBUtils.getDBKey(uid);
        DatabaseType type = DatabaseType.getType(DBkey);
        DatabaseContextHolder.setDatabaseType(type);
        List<egUidArticle> egUidArticles = blogSqlSession.selectList("UidArticleDao.selectUidArticle",dataMap);
        DatabaseContextHolder.clearDatabaseType();
        return egUidArticles;
    }

}
