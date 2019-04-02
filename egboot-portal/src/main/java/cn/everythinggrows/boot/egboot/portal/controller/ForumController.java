package cn.everythinggrows.boot.egboot.portal.controller;


import cn.everythinggrows.boot.egboot.portal.Utils.HttpRequsetUtil;
import cn.everythinggrows.boot.egboot.portal.model.TopicIndex;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ForumController {

    @Value("${FORUM_BASE_URL}")
    String FORUM_BASE_URL;

    @RequestMapping(value = "/forum/index")
    public String getForumIndex(HttpServletRequest request,
                                ModelAndView modelAndView){
        String url = FORUM_BASE_URL + "/index/all";
        JSONObject typeJson = HttpRequsetUtil.requestGet("topicIndices",url,null);
        List<TopicIndex> topicIndices = JSONObject.toJavaObject(typeJson, List.class);
        modelAndView.addObject("topicIndices",topicIndices);
        return "lw-forum";
    }
}
