package cn.everythinggrows.boot.egboot.forum.service;


import cn.everythinggrows.boot.egboot.forum.Utils.EgResult;
import cn.everythinggrows.boot.egboot.forum.dao.ForumAllDao;
import cn.everythinggrows.boot.egboot.forum.model.Topic;
import cn.everythinggrows.boot.egboot.forum.model.TopicIndex;
import cn.everythinggrows.boot.egboot.forum.model.egUser;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

@Service
public class ForumAllService {
    public static final String FORUM_TOPIC_INDEX = "eg/forum/topic/index/tid";

    @Autowired
    private ForumAllDao forumAllDao;
    @Autowired
    private TopicService topicService;
    @Autowired
    private RedisClientTemplate redisClientTemplate;


    public EgResult getForumAll(int perPage, int PageSize){
      long tid = redisClientTemplate.getTidNotIncr();
      long minId = tid - (perPage - 1) * PageSize;
      long maxId = tid - (PageSize - 1);
      List<TopicIndex> topicIndices = Lists.newArrayList();
      TopicIndex topicIndex = new TopicIndex();
      for(long i=maxId;i>=minId;i--){
          String key = FORUM_TOPIC_INDEX + "/" + String.valueOf(i);
          String content = redisClientTemplate.hget(key,"content");
          String createAt = redisClientTemplate.hget(key,"createAt");
          if(StringUtils.isEmpty(content) || StringUtils.isEmpty(createAt)){
              Topic topic = forumAllDao.getTopicOne(i);
              topicIndex.setTid(i);
              topicIndex.setContent(topic.getContent());
              topicIndex.setCreateAt(topic.getCreateAt());
              topicIndices.add(topicIndex);
              redisClientTemplate.hset(key,"tid",String.valueOf(tid));
              redisClientTemplate.hset(key,"content",content);
              redisClientTemplate.hset(key,"createAt",String.valueOf(topic.getCreateAt()));
              continue;
          }
          topicIndex.setTid(i);
          topicIndex.setContent(content);
          topicIndex.setCreateAt(Integer.parseInt(createAt));
          topicIndices.add(topicIndex);
      }
        Map<String,Object> ret = Maps.newHashMap();
      ret.put("topicIndices",topicIndices);
     return EgResult.ok(ret);
    }


    public int insertTopic(egUser user, String content){
       long tid = redisClientTemplate.tidGeneration();
        Topic topic = new Topic();
        topic.setTid(tid);
        topic.setContent(content);
        topic.setUid(user.getUid());
        topic.setPortrait(user.getPortrait());
        topic.setUsername(user.getUsername());
        topic.setExtend("");
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH ) + 1;
        //获取到0-11，与我们正常的月份差1
        int day = cal.get(Calendar.HOUR_OF_DAY);
        String createAtStr = String.valueOf(year) + String.valueOf(month) + String.valueOf(day);
        int createAt = Integer.parseInt(createAtStr);
        topic.setCreateAt(createAt);
        int i = forumAllDao.insertTopic(topic);
        int j = topicService.createTable(tid);
        int ret = 0;
        if(i > 0 && j > 0){
            ret = 1;
        }
        String key = FORUM_TOPIC_INDEX + "/" + String.valueOf(tid);
        redisClientTemplate.hset(key,"tid",String.valueOf(tid));
        redisClientTemplate.hset(key,"content",content);
        redisClientTemplate.hset(key,"createAt",createAtStr);
        return ret;
    }


    /**
     * 删除某条话题，同时删除该话题对应的详情表
     * @param tid
     * @return
     */
    public int deleteTopic(long tid){
        int i = forumAllDao.deleteTopic(tid);
        int j = topicService.deleteTable(tid);
        int ret = 0;
        if(i>0 && j>0){
            ret = 1;
        }
        return ret;
    }
}
