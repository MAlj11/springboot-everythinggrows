package cn.everythinggrows.boot.egboot.blog.dao;

import cn.everythinggrows.boot.egboot.blog.Utils.beanUtils;
import cn.everythinggrows.boot.egboot.blog.datasource.DatabaseContextHolder;
import cn.everythinggrows.boot.egboot.blog.datasource.DatabaseType;
import cn.everythinggrows.boot.egboot.blog.model.EgTypeArticle;
import com.google.common.collect.Maps;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
public class TypeArticleDao {

    @Autowired
    private SqlSessionTemplate blogSqlSession;

    public int insertUidArticle(EgTypeArticle egTypeArticle){
        Map<String,Object> dataMap = beanUtils.bean2map(egTypeArticle);
        long id = egTypeArticle.getType();
        dataMap.put("tableName", "eg_type_article_" + String.valueOf(id));
        DatabaseType type = DatabaseType.getType(0);
        DatabaseContextHolder.setDatabaseType(type);
        int i = blogSqlSession.insert("TypeArticleDao.insertArticle", dataMap);
        DatabaseContextHolder.clearDatabaseType();
        return i;
    }

    public List<EgTypeArticle> getTypeArticleList(int type){
        Map<String,Object> dataMap = Maps.newHashMap();
        dataMap.put("tableName", "eg_type_article_" + String.valueOf(type));
        DatabaseType dbtype = DatabaseType.getType(0);
        DatabaseContextHolder.setDatabaseType(dbtype);
        List<EgTypeArticle> egTypeArticleList = blogSqlSession.selectList("TypeArticleDao.selectArticle",dataMap);
        DatabaseContextHolder.clearDatabaseType();
        return egTypeArticleList;
    }

    public int deleteTypeArticle(int type,long aid){
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("tableName", "eg_type_article_" + String.valueOf(type));
        dataMap.put("aid",aid);
        DatabaseType dbtype = DatabaseType.getType(0);
        DatabaseContextHolder.setDatabaseType(dbtype);
        int i = blogSqlSession.delete("TypeArticleDao.deleteTypeArticle",dataMap);
        DatabaseContextHolder.clearDatabaseType();
        return i;
    }

}
