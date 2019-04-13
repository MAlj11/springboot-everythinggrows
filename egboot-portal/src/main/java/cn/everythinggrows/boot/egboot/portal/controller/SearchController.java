package cn.everythinggrows.boot.egboot.portal.controller;

import cn.everythinggrows.boot.egboot.portal.Utils.HttpRequsetUtil;
import cn.everythinggrows.boot.egboot.portal.model.Article;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class SearchController {
    private Logger logger = LoggerFactory.getLogger(SearchController.class);

    @Value("${SEARCH_BASE_URL}")
    String SEARCH_BASE_URL;

    @RequestMapping(value = "/search.html")
    public String searchArt(HttpServletRequest request,
                            @RequestParam(value = "searchCon") String searchCon) {
        String url = SEARCH_BASE_URL + "/jest/get";
        Map<String, String> param = new HashMap<>();
        param.put("content", searchCon);
        JSONObject typeJson = HttpRequsetUtil.requestGet(url, param);
        String seaList = typeJson.getString("SearchList");
        List<Article> searchList = JSONObject.parseArray(seaList, Article.class);
        logger.info("searchList:{}", searchList);
        HttpSession session = request.getSession();
        session.setAttribute("searchList", searchList);
        return "lw-search";
    }
}
