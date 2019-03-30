package cn.everythinggrows.boot.egboot.forum.dao;

import cn.everythinggrows.boot.egboot.forum.Utils.DBUtils;
import cn.everythinggrows.boot.egboot.forum.Utils.beanUtils;
import cn.everythinggrows.boot.egboot.forum.datasource.DatabaseContextHolder;
import cn.everythinggrows.boot.egboot.forum.datasource.DatabaseType;
import cn.everythinggrows.boot.egboot.forum.model.TopicDetail;
import com.google.common.collect.Maps;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class Topicdao {
    private static org.slf4j.Logger log = LoggerFactory.getLogger(Topicdao.class);
      @Autowired
      private SqlSessionTemplate topicSqlSession;

    public int createTopicDetailTable(long tid){
        Map<String,Object> dataMap = Maps.newHashMap();
        dataMap.put("tid",tid);
        dataMap.put("tableName", "eg_topic_detail_" + String.valueOf(tid));
        int DBkey = DBUtils.getDBKey(tid);
        DatabaseType type = DatabaseType.getType(DBkey);
        DatabaseContextHolder.setDatabaseType(type);
        log.info("eg_topic_detail_tablename:{}",dataMap.get("tableName"));
        log.info("eg_topic_detail_database:{}",type);
        int i = topicSqlSession.update("TopicDetailDao.createTable",dataMap);
        DatabaseContextHolder.clearDatabaseType();
        return i;
    }

    public int deleteTopicDetailTable(long tid){
        Map<String,Object> dataMap = Maps.newHashMap();
        dataMap.put("tid",tid);
        dataMap.put("tableName", "eg_topic_detail_" + DBUtils.getTableKey(tid));
        int DBkey = DBUtils.getDBKey(tid);
        DatabaseType type = DatabaseType.getType(DBkey);
        DatabaseContextHolder.setDatabaseType(type);
        int i = topicSqlSession.update("TopicDetailDao.dropTable",dataMap);
        DatabaseContextHolder.clearDatabaseType();
        return i;
    }

    public List<TopicDetail> getTopicDetailList(long tid){
        Map<String,Object> dataMap = Maps.newHashMap();
        dataMap.put("tid",tid);
        dataMap.put("tableName","eg_topic_detail_" + String.valueOf(tid));
        int DBKey = DBUtils.getDBKey(tid);
        DatabaseType type = DatabaseType.getType(DBKey);
        DatabaseContextHolder.setDatabaseType(type);
        List<TopicDetail> topicDetails = topicSqlSession.selectList("TopicDetailDao.selectTopicDetail",dataMap);
        DatabaseContextHolder.clearDatabaseType();
        return topicDetails;
    }

    public int insertTopicDetail(TopicDetail topicDetail){
        Map<String,Object> dataMap = beanUtils.bean2map(topicDetail);
        dataMap.put("tableName", "eg_topic_detail_" + String.valueOf(topicDetail.getTid()));
        dataMap.put("tid",topicDetail.getTid());
        int DBkey = DBUtils.getDBKey(topicDetail.getTid());
        DatabaseType type = DatabaseType.getType(DBkey);
        DatabaseContextHolder.setDatabaseType(type);
        int i = topicSqlSession.insert("TopicDetailDao.insertTopicDetail",dataMap);
        DatabaseContextHolder.clearDatabaseType();
        return i;
    }

    public int deleteTopicDetail(long id,long tid){
        Map<String,Object> dataMap = Maps.newHashMap();
        dataMap.put("tableName", "eg_topic_detail_" + String.valueOf(String.valueOf(tid)));
        dataMap.put("id",id);
        int DBkey = DBUtils.getDBKey(tid);
        DatabaseType type = DatabaseType.getType(DBkey);
        DatabaseContextHolder.setDatabaseType(type);
        int i = topicSqlSession.delete("TopicDetailDao.deleteTopicDetail",dataMap);
        DatabaseContextHolder.clearDatabaseType();
        return i;
    }
}
