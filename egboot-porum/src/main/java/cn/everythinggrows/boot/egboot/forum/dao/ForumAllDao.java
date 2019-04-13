package cn.everythinggrows.boot.egboot.forum.dao;


import cn.everythinggrows.boot.egboot.forum.Utils.DBUtils;
import cn.everythinggrows.boot.egboot.forum.Utils.beanUtils;
import cn.everythinggrows.boot.egboot.forum.datasource.DatabaseContextHolder;
import cn.everythinggrows.boot.egboot.forum.datasource.DatabaseType;
import cn.everythinggrows.boot.egboot.forum.model.Topic;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class ForumAllDao {
    private static org.slf4j.Logger log = LoggerFactory.getLogger(ForumAllDao.class);
    @Autowired
    private SqlSessionTemplate forumSqlSession;

    /**
     * 分库分表查表（暂废）
     *
     * @param limit
     * @param offset
     * @return
     */
    public List<Topic> getTopicList(int limit, int offset) {
        List<Topic> list = Lists.newArrayList();
        /*
        todo  分库分表进行分页查询
         */
        return list;
    }

    public int insertTopic(Topic topic) {
        Map<String, Object> dataMap = beanUtils.bean2map(topic);
        dataMap.put("tableName", "eg_topic_" + DBUtils.getTableKey(topic.getTid()));
        int DBkey = DBUtils.getDBKey(topic.getTid());
        DatabaseType type = DatabaseType.getType(DBkey);
        DatabaseContextHolder.setDatabaseType(type);
        log.info("tablename:{}", dataMap.get("tableName"));
        log.info("database:{}", type);
        int i = forumSqlSession.insert("ForumAllDao.insertTopic", dataMap);
        DatabaseContextHolder.clearDatabaseType();
        return i;
    }

    public int deleteTopic(long tid) {
        Map<String, Object> dataMap = Maps.newHashMap();
        dataMap.put("tableName", "eg_topic_" + DBUtils.getTableKey(tid));
        dataMap.put("tid", tid);
        int DBkey = DBUtils.getDBKey(tid);
        DatabaseType type = DatabaseType.getType(DBkey);
        DatabaseContextHolder.setDatabaseType(type);
        int i = forumSqlSession.delete("ForumAllDao.deleteTopic", dataMap);
        DatabaseContextHolder.clearDatabaseType();
        return i;
    }

    public Topic getTopicOne(long tid) {
        Map<String, Object> dataMap = Maps.newHashMap();
        dataMap.put("tableName", "eg_topic_" + DBUtils.getTableKey(tid));
        dataMap.put("tid", tid);
        int DBkey = DBUtils.getDBKey(tid);
        DatabaseType type = DatabaseType.getType(DBkey);
        DatabaseContextHolder.setDatabaseType(type);
        List<Topic> topics = forumSqlSession.selectList("ForumAllDao.selectTopic", dataMap);
        DatabaseContextHolder.clearDatabaseType();
        if (topics.size() == 0 || topics == null) {
            return null;
        }
        return topics.get(0);
    }

}
