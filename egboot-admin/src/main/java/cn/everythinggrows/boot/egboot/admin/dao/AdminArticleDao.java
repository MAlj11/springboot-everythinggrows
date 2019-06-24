package cn.everythinggrows.boot.egboot.admin.dao;


import cn.everythinggrows.boot.egboot.admin.datasource.DatabaseContextHolder;
import cn.everythinggrows.boot.egboot.admin.datasource.DatabaseType;
import cn.everythinggrows.boot.egboot.admin.model.egArticle;
import com.google.common.collect.Maps;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class AdminArticleDao {

    @Autowired
    private SqlSessionTemplate adminSqlSession;

    public List<egArticle> selectArticle(int dbKey, String tableName) {
        Map<String, Object> dataMap = Maps.newHashMap();
        dataMap.put("tableName", tableName);
        DatabaseType type = DatabaseType.getType(dbKey);
        DatabaseContextHolder.setDatabaseType(type);
        List<egArticle> articles = adminSqlSession.selectList("adminArticleDao.selectAll", dataMap);
        DatabaseContextHolder.clearDatabaseType();
        return articles;
    }

}
