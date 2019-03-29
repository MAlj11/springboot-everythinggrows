package cn.everythinggrows.boot.egboot.forum.controller;

import cn.everythinggrows.boot.egboot.forum.Utils.EgResult;
import cn.everythinggrows.boot.egboot.forum.aop.NeedSession;
import cn.everythinggrows.boot.egboot.forum.service.ForumAllService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class ForumAllController {
    private static Logger log = LoggerFactory.getLogger(ForumAllController.class);
    @Autowired
    private ForumAllService forumAllService;

    /**
     * 查询论坛所有话题
     * @return
     */
    @RequestMapping("/forum/index/all")
    public EgResult getForumAll(@RequestParam(value = "perPage") int perPage,
                                @RequestParam(value = "pageSize") int pageSize){
     //todo 分库分表进行分页查询
       EgResult ret = forumAllService.getForumAll(perPage,pageSize);
       return ret;
    }


    /**
     * 删除某条话题，同时删除该话题对应的详情表
     * @param tid
     * @return
     */
    @NeedSession
    @RequestMapping(value = "/forum/index/delete/{tid}")
    public EgResult deleteForumTopic(@PathVariable("tid") long tid){
        int i = forumAllService.deleteTopic(tid);
        if(i<=0){
            return EgResult.systemError();
        }
        return EgResult.ok();
    }


    /**
     * 插入一条话题
     * @param content
     * @param session
     * @return
     */
    @NeedSession
    @RequestMapping(value = "/forum/index/insert")
    public EgResult insertTopic(@RequestParam(value = "content") String content,
                           @RequestHeader(value = "x-eg-session") String session){
        long uid = getUid(session);
        int i = forumAllService.insertTopic(uid,content);
        if(i<=0){
            return EgResult.systemError();
        }
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
