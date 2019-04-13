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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CommentDao {
    @Autowired
    private SqlSessionTemplate blogSqlSession;

    public List<Comment> getCommentList(long aid) {
        Map<String, Object> dataMap = Maps.newHashMap();
        dataMap.put("tableName", "eg_comment_" + DBUtils.getTableKey(aid));
        dataMap.put("aid", aid);
        int DBkey = DBUtils.getDBKey(aid);
        DatabaseType type = DatabaseType.getType(DBkey);
        DatabaseContextHolder.setDatabaseType(type);
        List<Comment> comments = blogSqlSession.selectList("CommentDao.selectComment", dataMap);
        DatabaseContextHolder.clearDatabaseType();
        return comments;
    }

    public int insertComment(Comment comment) {
        Map<String, Object> dataMap = beanUtils.bean2map(comment);
        dataMap.put("tableName", "eg_comment_" + DBUtils.getTableKey(comment.getAid()));
        dataMap.put("aid", comment.getAid());
        int DBkey = DBUtils.getDBKey(comment.getAid());
        DatabaseType type = DatabaseType.getType(DBkey);
        DatabaseContextHolder.setDatabaseType(type);
        int i = blogSqlSession.insert("CommentDao.insertComment", dataMap);
        DatabaseContextHolder.clearDatabaseType();
        return i;
    }

    public int deleteComment(long cid, long aid) {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("tableName", "eg_comment_" + DBUtils.getTableKey(aid));
        dataMap.put("aid", aid);
        dataMap.put("cid", cid);
        int DBkey = DBUtils.getDBKey(aid);
        DatabaseType type = DatabaseType.getType(DBkey);
        DatabaseContextHolder.setDatabaseType(type);
        int i = blogSqlSession.delete("CommentDao.deleteComment", dataMap);
        return i;
    }
}
