package cn.everythinggrows.boot.egboot.forum.controller;


import cn.everythinggrows.boot.egboot.forum.Utils.EgResult;
import cn.everythinggrows.boot.egboot.forum.aop.NeedSession;
import cn.everythinggrows.boot.egboot.forum.dao.Topicdao;
import cn.everythinggrows.boot.egboot.forum.dubboapi.IUserAccount;
import cn.everythinggrows.boot.egboot.forum.model.TopicDetail;
import cn.everythinggrows.boot.egboot.forum.model.egUser;
import cn.everythinggrows.boot.egboot.forum.service.HttpRequestToUser;
import cn.everythinggrows.boot.egboot.forum.service.TopicService;
import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;

@RestController
public class TopicDetailController {

    @Autowired
    private Topicdao topicdao;
    @Autowired
    private TopicService topicService;
    @Autowired
    HttpRequestToUser httpRequestToUser;


    /**
     * 根据tid查询该标题的所有内容
     * @param tid
     * @return
     */
    @RequestMapping(value = "/forum/topic/detail/{tid}")
    public EgResult getTopicDetailWithTid(@PathVariable("tid") long tid){
        List<TopicDetail> topicDetails = topicService.getTopicDetailLsit(tid);
        Map<String,Object> ret = Maps.newHashMap();
        ret.put("topicDetails",topicDetails);
        return EgResult.ok(ret);

    }


    /**
     * 向该标题插入一条内容
     * @param tid
     * @param content
     * @param session
     * @return
     */
    @NeedSession
    @RequestMapping(value = "/forum/topic/detail/insert")
    public EgResult inseretTopicDetail(@RequestParam(value = "tid",defaultValue = "0") long tid,
                                       @RequestParam(value = "content",defaultValue = "") String content,
                                       @RequestHeader(value = "x-eg-session") String session){

//        String token = String.valueOf(uid) + ";" + uuid;
        long uid = getUid(session);
        egUser user = httpRequestToUser.getUser(uid);
        int i = topicService.insertTopicDetail(user,content,tid);
        if (i > 0){
            return EgResult.ok();
        }
        else {
            return EgResult.systemError();
        }
    }


    /**
     * 删除论坛话题的某一条内容
     * @param id
     * @param tid
     * @return
     */
    @NeedSession
    @RequestMapping(value = "/forum/topic/detail/delete")
    public EgResult deleteTopicDetail(@RequestParam(value = "id") long id,
                                        @RequestParam(value = "tid") long tid){
        int i = topicService.deleteTopicDetail(id,tid);
        return EgResult.ok();
    }

    public long getUid(String session){
        if(session == null || session.length() == 0){
            return 0;
        }
        String[] line = session.split(";");
        long uid = Long.parseLong(line[0]);
        return uid;
    }

}
