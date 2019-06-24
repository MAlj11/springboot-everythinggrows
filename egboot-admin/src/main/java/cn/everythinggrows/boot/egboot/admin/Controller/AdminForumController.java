package cn.everythinggrows.boot.egboot.admin.Controller;


import cn.everythinggrows.boot.egboot.admin.Utils.DateHelper;
import cn.everythinggrows.boot.egboot.admin.Utils.HttpClientUtil;
import cn.everythinggrows.boot.egboot.admin.Utils.HttpRequsetUtil;
import cn.everythinggrows.boot.egboot.admin.dao.AdminTopicDao;
import cn.everythinggrows.boot.egboot.admin.model.Topic;
import cn.everythinggrows.boot.egboot.admin.model.TopicIndex;
import cn.everythinggrows.boot.egboot.admin.model.egArticle;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AdminForumController {

    @Value("${FORUM_BASE_URL}")
    String FORUM_BASE_URL;
    @Autowired
    private AdminTopicDao adminTopicDao;


    @RequestMapping(value = "/admin/index/forum")
    public String getForumIndex(@RequestParam(value = "perPage") String perPage,
                                HttpServletRequest request) {
        List<Topic> topicList = new ArrayList<>();
        for(int i=0;i<=7;i++){
            for(int j=0;j<=31;j++){
                String tableName = "eg_topic_" + String.valueOf(j);
                List<Topic> topics = adminTopicDao.selectTopic(i,tableName);
                topicList.addAll(topics);
            }
        }
        List<Integer> pageList = new ArrayList<>();
        pageList.add(1);
        HttpSession session = request.getSession();
        session.setAttribute("admintopicIndices", topicList);
        session.setAttribute("adminpageList", pageList);
        return "admin-forum";
    }

    @RequestMapping(value = "/admin/forum/delete/{tid}")
    public String seleteForum(@PathVariable(value = "tid") long tid,
                              HttpServletRequest request) {
        String session = "111;ashgfhcadgcfdacsa";
        String delforumUrl = FORUM_BASE_URL + "/index/delete/" + String.valueOf(tid);
        String ret = HttpClientUtil.doGetWithHander(delforumUrl, null, "x-eg-session", session);
        return "fordelSuccess";
    }

}
