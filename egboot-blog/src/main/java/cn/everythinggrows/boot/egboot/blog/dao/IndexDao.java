package cn.everythinggrows.boot.egboot.blog.dao;

import cn.everythinggrows.boot.egboot.blog.Utils.DBUtils;
import cn.everythinggrows.boot.egboot.blog.Utils.beanUtils;
import cn.everythinggrows.boot.egboot.blog.datasource.DatabaseContextHolder;
import cn.everythinggrows.boot.egboot.blog.datasource.DatabaseType;
import cn.everythinggrows.boot.egboot.blog.model.egArticle;
import com.google.common.collect.Maps;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class IndexDao {
    @Autowired
    private SqlSessionTemplate blogSqlSession;

    public egArticle getArtcleOne(long id){
        Map<String, Object> dataMap = Maps.newHashMap();
        dataMap.put("tableName", "eg_article_" + DBUtils.getTableKey(id));
        dataMap.put("id",id);
        int DBkey = DBUtils.getDBKey(id);
        DatabaseType type = DatabaseType.getType(DBkey);
        DatabaseContextHolder.setDatabaseType(type);
        egArticle egArticle = blogSqlSession.selectOne("blogIndexDao.selectArticle", dataMap);
        DatabaseContextHolder.clearDatabaseType();
        return egArticle;
    }

    public int insertArticle(egArticle egArticle){
        Map<String,Object> dataMap = beanUtils.bean2map(egArticle);
        long id = egArticle.getId();
        dataMap.put("tableName", "eg_article_" + DBUtils.getTableKey(id));
        int DBkey = DBUtils.getDBKey(id);
        DatabaseType type = DatabaseType.getType(DBkey);
        DatabaseContextHolder.setDatabaseType(type);
        int i = blogSqlSession.insert("insertArticle.selectArticle", dataMap);
        DatabaseContextHolder.clearDatabaseType();
        return i;
    }



}
