package cn.everythinggrows.boot.egboot.blog.dao;

import cn.everythinggrows.boot.egboot.blog.Utils.DBUtils;
import cn.everythinggrows.boot.egboot.blog.Utils.beanUtils;
import cn.everythinggrows.boot.egboot.blog.datasource.DatabaseContextHolder;
import cn.everythinggrows.boot.egboot.blog.datasource.DatabaseType;
import cn.everythinggrows.boot.egboot.blog.model.Comment;
import com.google.common.collect.Maps;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class CommentDao {
    @Autowired
    private SqlSessionTemplate blogSqlSession;

    public List<Comment> getCommentList(long aid){
        Map<String,Object> dataMap = Maps.newHashMap();
        dataMap.put("tableName", "eg_comment_" + DBUtils.getTableKey(aid));
        dataMap.put("id",aid);
        int DBkey = DBUtils.getDBKey(aid);
        DatabaseType type = DatabaseType.getType(DBkey);
        DatabaseContextHolder.setDatabaseType(type);
        List<Comment> comments = blogSqlSession.selectList("CommentDao.selectComment");
        DatabaseContextHolder.clearDatabaseType();
        return comments;
    }

    public int insertComment(Comment comment){
        Map<String,Object> dataMap = beanUtils.bean2map(comment);
        dataMap.put("tableName", "eg_comment_" + DBUtils.getTableKey(comment.getAid()));
        dataMap.put("id",comment.getAid());
        int DBkey = DBUtils.getDBKey(comment.getAid());
        DatabaseType type = DatabaseType.getType(DBkey);
        DatabaseContextHolder.setDatabaseType(type);
        int i = blogSqlSession.insert("CommentDao.insertComment");
        DatabaseContextHolder.clearDatabaseType();
        return i;
    }
}
