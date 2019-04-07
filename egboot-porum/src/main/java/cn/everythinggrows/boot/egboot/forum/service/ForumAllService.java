package cn.everythinggrows.boot.egboot.forum.service;


import cn.everythinggrows.boot.egboot.forum.Utils.DateUtils;
import cn.everythinggrows.boot.egboot.forum.Utils.EgResult;
import cn.everythinggrows.boot.egboot.forum.dao.ForumAllDao;
import cn.everythinggrows.boot.egboot.forum.model.Topic;
import cn.everythinggrows.boot.egboot.forum.model.TopicIndex;
import cn.everythinggrows.boot.egboot.forum.model.egUser;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ForumAllService {
    private static org.slf4j.Logger log = LoggerFactory.getLogger(ForumAllService.class);
    public static final String FORUM_TOPIC_INDEX = "eg/forum/topic/index/tid";

    @Autowired
    private ForumAllDao forumAllDao;
    @Autowired
    private TopicService topicService;
    @Autowired
    private RedisClientTemplate redisClientTemplate;
    @Autowired
    private HttpRequestToUser httpRequestToUser;


    public EgResult getForumAll(int perPage, int PageSize){
      long tid = redisClientTemplate.getTidNotIncr();
      long maxId = tid - (perPage - 1) * PageSize;
      long minId = tid - (PageSize - 1);
      if(minId <= 0){
          minId = 1L;
      }
      log.info("max{},min{}",maxId,minId);
      List<TopicIndex> topicIndices = new ArrayList<>();
      for(long i=maxId;i>=minId;i--){
          log.info("i:{}",i);
          TopicIndex topicIndex = new TopicIndex();
          String key = FORUM_TOPIC_INDEX + "/" + String.valueOf(i);
          log.info("key:{}",key);
          String content = redisClientTemplate.hget(key,"content");
          String createAt = redisClientTemplate.hget(key,"createAt");
          log.info("content:{},createAt:{}",content,createAt);
          if(StringUtils.isEmpty(content) || StringUtils.isEmpty(createAt)){
              Topic topic = forumAllDao.getTopicOne(i);
              log.info("topic:{}",topic);
              if(topic != null) {
                  topicIndex.setTid(i);
                  topicIndex.setContent(topic.getContent());
                  topicIndex.setCreateAt(topic.getCreateAt());
                  topicIndices.add(topicIndex);
                  redisClientTemplate.hset(key, "tid", String.valueOf(i));
                  redisClientTemplate.hset(key, "content", content==null?"":content);
                  redisClientTemplate.hset(key, "createAt", String.valueOf(topic.getCreateAt())==null?"":String.valueOf(topic.getCreateAt()));
                  redisClientTemplate.expire(key,5*60);
              }
              continue;
          }
          topicIndex.setTid(i);
          topicIndex.setContent(content);
          topicIndex.setCreateAt(Integer.parseInt(createAt));
          topicIndices.add(topicIndex);
      }
        Map<String,Object> ret = Maps.newHashMap();
      ret.put("count",tid);
      ret.put("topicIndices",topicIndices);
     return EgResult.ok(ret);
    }


    public int insertTopic(long uid, String content){
        egUser user = httpRequestToUser.getUser(uid);
        long tid = redisClientTemplate.tidGeneration();
        String portrairDns = user.getPortrait();
        String[] line = portrairDns.split("/");
        int lastIndex = line.length - 1;
        String portrait = line[lastIndex];
        Topic topic = new Topic();
        topic.setTid(tid);
        topic.setContent(content);
        topic.setUid(user.getUid());
        topic.setPortrait(portrait);
        topic.setUsername(user.getUsername());
        topic.setExtend("");
        int createAt = (int)(System.currentTimeMillis() / 1000);
        topic.setCreateAt(createAt);
        int i = forumAllDao.insertTopic(topic);
        int j = topicService.createTable(tid);
        int ret = 0;
        if(i > 0 && j == 0){
            ret = 1;
        }
        String key = FORUM_TOPIC_INDEX + "/" + String.valueOf(tid);
        redisClientTemplate.hset(key,"tid",String.valueOf(tid));
        redisClientTemplate.hset(key,"content",content);
        redisClientTemplate.hset(key,"createAt",String.valueOf(createAt));
        redisClientTemplate.expire(key,5*60);
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
        if(i>0 && j==0){
            redisClientTemplate.hdel(FORUM_TOPIC_INDEX + String.valueOf(tid),"tid","content","createAt");
            ret = 1;
        }
        return ret;
    }
}
