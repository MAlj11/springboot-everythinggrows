package cn.everythinggrows.boot.egboot.admin.Controller;


import cn.everythinggrows.boot.egboot.admin.Utils.DateHelper;
import cn.everythinggrows.boot.egboot.admin.Utils.HttpClientUtil;
import cn.everythinggrows.boot.egboot.admin.Utils.HttpRequsetUtil;
import cn.everythinggrows.boot.egboot.admin.model.TopicIndex;
import com.alibaba.fastjson.JSONObject;
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


    @RequestMapping(value = "/admin/index/forum")
    public String getForumIndex(@RequestParam(value = "perPage") String perPage,
                                HttpServletRequest request) {
        int pageSize = 30;
        String url = FORUM_BASE_URL + "/index/all";
        Map<String, String> param = new HashMap<>();
        param.put("perPage", perPage);
        param.put("pageSize", String.valueOf(pageSize));
        JSONObject forumJson = HttpRequsetUtil.requestGet(url, param);
        String artList = forumJson.getString("topicIndices");
        String count = forumJson.getString("count");
        int page = Integer.parseInt(count) / pageSize;
        if (page < 1) {
            page = 1;
        }
        List<TopicIndex> topicList = JSONObject.parseArray(artList, TopicIndex.class);
        for (TopicIndex topicIndex : topicList) {
            topicIndex.setCreateDate(DateHelper.dateFormat(DateHelper.stampToDate(String.valueOf(topicIndex.getCreateAt()))));
        }
        List<Integer> pageList = new ArrayList<>();
        for (int i = 0; i < page; i++) {
            pageList.add(i + 1);
        }
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
