package cn.everythinggrows.boot.egboot.portal.controller;


import cn.everythinggrows.boot.egboot.portal.Utils.CookieUtils;
import cn.everythinggrows.boot.egboot.portal.Utils.DateHelper;
import cn.everythinggrows.boot.egboot.portal.Utils.HttpClientUtil;
import cn.everythinggrows.boot.egboot.portal.Utils.HttpRequsetUtil;
import cn.everythinggrows.boot.egboot.portal.model.TopicDetail;
import cn.everythinggrows.boot.egboot.portal.model.TopicIndex;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ForumController {
    private Logger logger = LoggerFactory.getLogger(ForumController.class);

    @Value("${FORUM_BASE_URL}")
    String FORUM_BASE_URL;

    @RequestMapping(value = "/forum/index")
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
            topicIndex.setCreateDate(DateHelper.stampToDate(String.valueOf(topicIndex.getCreateAt())));
        }
        List<Integer> pageList = new ArrayList<>();
        for (int i = 0; i < page; i++) {
            pageList.add(i + 1);
        }
        HttpSession session = request.getSession();
        session.setAttribute("topicIndices", topicList);
        session.setAttribute("pageList", pageList);
        return "lw-forum";
    }

    @RequestMapping(value = "/forum/insert")
    public String insertForum(HttpServletRequest request) throws UnsupportedEncodingException {
        String tokenVal = CookieUtils.getCookieValue(request, "eg_cookie_token");
        if (tokenVal == null || tokenVal.length() == 0) {
            return "lw-log";
        }
        tokenVal = URLDecoder.decode(tokenVal, "utf-8");
        return "lw-forumwrite";
    }

    @RequestMapping(value = "forum/forum/index/write", method = RequestMethod.POST)
    public String writeForum(@RequestParam(value = "content") String content,
                             HttpServletRequest request) throws UnsupportedEncodingException {
        String tokenVal = CookieUtils.getCookieValue(request, "eg_cookie_token");
        if (tokenVal == null || tokenVal.length() == 0) {
            return "lw-log";
        }
        tokenVal = URLDecoder.decode(tokenVal, "utf-8");
        String inseertFourumUrl = FORUM_BASE_URL + "/index/insert";
        Map<String, String> param = new HashMap<>();
        param.put("content", content);
        String ret = HttpClientUtil.doGetWithHander(inseertFourumUrl, param, "x-eg-session", tokenVal);
        JSONObject json = JSON.parseObject(ret);
        Map dataMap = JSONObject.toJavaObject(json, Map.class);
        if ((Integer) dataMap.get("status") == 200) {
            return "publishForumSuccess";
        }
        return "lw-forumwrite";
    }

    @RequestMapping(value = "/index/forum/detail/{tid}")
    public String getForumDetail(@PathVariable(value = "tid") long tid,
                                 HttpServletRequest request) throws UnsupportedEncodingException {
        String detailUrl = FORUM_BASE_URL + "/topic/detail/" + String.valueOf(tid);
        JSONObject detailJson = HttpRequsetUtil.requestGet(detailUrl, null);
        String topicdList = detailJson.getString("topicDetails");
        List<TopicDetail> topicDetailList = JSONArray.parseArray(topicdList, TopicDetail.class);
        String tokenVal = CookieUtils.getCookieValue(request, "eg_cookie_token");
        tokenVal = URLDecoder.decode(tokenVal, "utf-8");
        long uid = getUid(tokenVal);
        for (TopicDetail topicDetail : topicDetailList) {
            logger.info("forum pot : {}", topicDetail.getPortrait());
        }
        HttpSession session = request.getSession();
        session.setAttribute("topicTidd", tid);
        session.setAttribute("topicDetailList", topicDetailList);
        return "lw-topicDetail";
    }

    @RequestMapping(value = "/topic/detail/delete")
    public String deleteTopicDetail(@RequestParam(value = "tid") long tid,
                                    @RequestParam(value = "id") int id,
                                    HttpServletRequest request) throws UnsupportedEncodingException {
        String delDetailUrl = FORUM_BASE_URL + "/topic/detail/delete";
        Map<String, String> param = new HashMap<>();
        param.put("tid", String.valueOf(tid));
        param.put("id", String.valueOf(id));
        String tokenVal = CookieUtils.getCookieValue(request, "eg_cookie_token");
        if (tokenVal == null || tokenVal.length() == 0) {
            return "lw-log";
        }
        tokenVal = URLDecoder.decode(tokenVal, "utf-8");
        HttpSession session = request.getSession();
        session.setAttribute("topicTid", tid);
        String ret = HttpClientUtil.doGetWithHander(delDetailUrl, null, "x-eg-session", tokenVal);
        JSONObject json = JSON.parseObject(ret);
        Map dataMap = JSONObject.toJavaObject(json, Map.class);
        if ((Integer) dataMap.get("status") == 200) {
            return "delSuccess";
        }
        return "lw-topicDetail";
    }

    @RequestMapping(value = "/topic/detail/write/{tid}")
    public String insertTopic(@PathVariable(value = "tid") long tid,
                              HttpServletRequest request) throws UnsupportedEncodingException {
        String tokenVal = CookieUtils.getCookieValue(request, "eg_cookie_token");
        if (tokenVal == null || tokenVal.length() == 0) {
            return "lw-log";
        }
        HttpSession session = request.getSession();
        session.setAttribute("topicTidw", tid);
        return "lw-topicwrite";
    }

    @RequestMapping(value = "/topic/detail/write/topic/detail/insert/{tid}", method = RequestMethod.POST)
    public String insertTopicDetail(@PathVariable(value = "tid") long tid,
                                    @RequestParam(value = "content") String content,
                                    HttpServletRequest request) throws UnsupportedEncodingException {
        HttpSession session = request.getSession();
        session.setAttribute("tidinsert", tid);
        String tokenVal = CookieUtils.getCookieValue(request, "eg_cookie_token");
        if (tokenVal == null || tokenVal.length() == 0) {
            return "lw-log";
        }
        tokenVal = URLDecoder.decode(tokenVal, "utf-8");
        String url = FORUM_BASE_URL + "/topic/detail/insert";
        Map<String, String> param = new HashMap<>();
        param.put("tid", String.valueOf(tid));
        param.put("content", content);
        String ret = HttpClientUtil.doGetWithHander(url, param, "x-eg-session", tokenVal);
        JSONObject json = JSON.parseObject(ret);
        Map dataMap = JSONObject.toJavaObject(json, Map.class);
        if ((Integer) dataMap.get("status") == 200) {
            return "publishTopicSuccess";
        }
        return "lw-topicwrite";
    }


    public long getUid(String session) {
        if (session == null || session.length() == 0) {
            return 0;
        }
        String[] line = session.split(";");
        long uid = Long.parseLong(line[0]);
        return uid;
    }
}
