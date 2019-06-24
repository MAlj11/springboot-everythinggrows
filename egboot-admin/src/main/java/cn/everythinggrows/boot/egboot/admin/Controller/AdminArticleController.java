package cn.everythinggrows.boot.egboot.admin.Controller;


import cn.everythinggrows.boot.egboot.admin.Utils.HttpClientUtil;
import cn.everythinggrows.boot.egboot.admin.Utils.HttpRequsetUtil;
import cn.everythinggrows.boot.egboot.admin.dao.AdminArticleDao;
import cn.everythinggrows.boot.egboot.admin.model.Comment;
import cn.everythinggrows.boot.egboot.admin.model.EgTypeArticle;
import cn.everythinggrows.boot.egboot.admin.model.egArticle;
import cn.everythinggrows.boot.egboot.admin.model.egUser;
import cn.everythinggrows.boot.egboot.admin.service.UserService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AdminArticleController {

    @Value("${BLOG_BASE_URL}")
    String BLOG_BASE_URL;

    @Autowired
    private UserService userService;
    @Autowired
    private AdminArticleDao adminArticleDao;

    @RequestMapping(value = "/admin-index.html")
    public String adminIndex() {
        return "admin-index";
    }

    @RequestMapping(value = "/admin/index/article")
    public String adminArticle(HttpServletRequest request) {
        HttpSession session = request.getSession();
        List<egArticle> articleList = new ArrayList<>();
        for(int i=0;i<=7;i++){
            for(int j=0;j<=31;j++){
                String tableName = "eg_article_" + String.valueOf(j);
                List<egArticle> article = adminArticleDao.selectArticle(i,tableName);
                articleList.addAll(article);
            }
        }
        session.setAttribute("articleList", articleList);
        return "lw-admin-article";
    }

    @RequestMapping(value = "/admin/article/delete/{aid}")
    public String adminDelete(@PathVariable(value = "aid") long aid,
                              HttpServletRequest request,
                              HttpServletResponse response) {
        String uidUrl = BLOG_BASE_URL + "/article/getuid/" + String.valueOf(aid);
        JSONObject data = HttpRequsetUtil.requestGet(uidUrl, null);
        String uid = data.getString("uid");
        String tokenVal = uid + ";safjkhaikfhidsfhidsk";
        String delUrl = BLOG_BASE_URL + "/article/delete/" + String.valueOf(aid);
        String ret = HttpClientUtil.doGetWithHander(delUrl, null, "x-eg-session", tokenVal);
        JSONObject json = JSON.parseObject(ret);
        Map dataMap = JSONObject.toJavaObject(json, Map.class);
        return "delSuccess";
    }

    @RequestMapping(value = "/admin/article/detail/{aid}")
    public String adminArticleDetail(@PathVariable(value = "aid") long aid,
                                     HttpServletRequest request) {
        HttpSession session = request.getSession();
        String detailUrl = BLOG_BASE_URL + "/article/get/" + String.valueOf(aid);
        JSONObject detailJson = HttpRequsetUtil.requestGet(detailUrl, null);
        String detart = detailJson.getString("article");
        JSON artjson = JSONObject.parseObject(detart);
        egArticle article = JSONObject.toJavaObject(artjson, egArticle.class);
        session.setAttribute("adminarticleDetail", article);
        if (article.getUid() != 0) {
            egUser user = userService.getUser(article.getUid());
            session.setAttribute("adminuser", user);
        }
        String commentUrl = BLOG_BASE_URL + "/comment/" + String.valueOf(aid);
        JSONObject comObj = HttpRequsetUtil.requestGet(commentUrl, null);
        String comList = comObj.getString("comments");
        List<Comment> comments = JSONObject.parseArray(comList, Comment.class);
        session.setAttribute("admincommentDetail", comments);
        return "admin-article-fullwidth";
    }

    @RequestMapping(value = "/admin/article/commit/delete")
    public String adminCommentDelete(@RequestParam(value = "cid") long cid,
                                     @RequestParam(value = "aid") long aid,
                                     HttpServletRequest request) {

        HttpSession session = request.getSession();
        String comUrl = BLOG_BASE_URL + "/comment/delete";
        Map<String, String> param = new HashMap<>();
        param.put("cid", String.valueOf(cid));
        param.put("aid", String.valueOf(aid));
        String ret = HttpClientUtil.doGet(comUrl, param);
        return "comdelSuccess";
    }
}
