package cn.everythinggrows.boot.egboot.blog.controller;


import cn.everythinggrows.boot.egboot.blog.Utils.EgResult;
import cn.everythinggrows.boot.egboot.blog.aop.loginRequired;
import cn.everythinggrows.boot.egboot.blog.model.Comment;
import cn.everythinggrows.boot.egboot.blog.service.CommentService;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class CommentController {
    @Autowired
    private CommentService commentService;

    @RequestMapping(value = "/blog/comment/{aid}")
    public EgResult getComment(@PathVariable("aid") long aid){
       List<Comment> comments = commentService.getCommentWithAid(aid);
        Map<String,Object> ret = Maps.newHashMap();
        ret.put("comments",comments);
        return EgResult.ok(ret);
    }

    @loginRequired
    @RequestMapping(value = "/blog/comment/insert")
    public EgResult insertComment(@RequestParam(value = "aid") long aid,
                                                            @RequestParam(value = "contnet")String content,
                                                            @RequestHeader(value = "x-eg-session") String session){
        long uid = getUid(session);
        int i = commentService.insertComment(aid,uid,content);
        if(i>0){
            return EgResult.ok();
        }else{
            return EgResult.error(10001,"system is error");
        }
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
