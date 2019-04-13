package cn.everythinggrows.boot.egboot.forum.service;


import cn.everythinggrows.boot.egboot.forum.dao.Topicdao;
import cn.everythinggrows.boot.egboot.forum.model.TopicDetail;
import cn.everythinggrows.boot.egboot.forum.model.egUser;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

@Service
public class TopicService {

    @Autowired
    private Topicdao topicdao;

    public int insertTopicDetail(egUser user, String content, long tid) {
        String portrairDns = user.getPortrait();
        String[] line = portrairDns.split("/");
        int lastIndex = line.length - 1;
        String portrait = line[lastIndex];
        TopicDetail topicDetail = new TopicDetail();
        topicDetail.setTid(tid);
        topicDetail.setContent(content);
        topicDetail.setUid(user.getUid());
        topicDetail.setUsername(user.getUsername());
        topicDetail.setPortrait(portrait);
        topicDetail.setToUid(0L);
        topicDetail.setToUsername("");
        topicDetail.setToPortrait("");
        int createAt = (int) (System.currentTimeMillis() / 1000);
        topicDetail.setCreateAt(createAt);
        int i = topicdao.insertTopicDetail(topicDetail);
        return i;
    }

    public List<TopicDetail> getTopicDetailLsit(long tid) {
        List<TopicDetail> topicDetails = Lists.newArrayList();
        if (tid == 0) {
            return topicDetails;
        }
        topicDetails = topicdao.getTopicDetailList(tid);
        return topicDetails;
    }

    public int createTable(long tid) {
        int i = topicdao.createTopicDetailTable(tid);
        return i;
    }

    public int deleteTable(long tid) {
        int i = topicdao.deleteTopicDetailTable(tid);
        return i;
    }

    public int deleteTopicDetail(long id, long tid) {
        int i = topicdao.deleteTopicDetail(id, tid);
        return i;
    }
}
