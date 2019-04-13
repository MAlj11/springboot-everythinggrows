package cn.everythinggrows.cn.eg.search.contorller;


import cn.everythinggrows.cn.eg.search.Utils.EgResult;
import cn.everythinggrows.cn.eg.search.entity.Article;
import cn.everythinggrows.cn.eg.search.service.JestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class JestApiController {
    @Autowired
    private JestService jestService;

    @RequestMapping(value = "/search/jest/save")
    public EgResult saveArticle(@RequestParam(value = "id") long id,
                                @RequestParam(value = "title") String title,
                                @RequestParam(value = "auther") String auther) {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.HOUR_OF_DAY);
        String monthStr = String.valueOf(month);
        if (monthStr.length() == 1) {
            monthStr = "0" + monthStr;
        }
        String dayStr = String.valueOf(day);
        if (dayStr.length() == 1) {
            dayStr = "0" + dayStr;
        }
        String createAtStr = String.valueOf(year) + monthStr + dayStr;
        Article article = new Article(id, title, auther, createAtStr);
        String ret = jestService.saveEntity(article);
        return EgResult.ok();
    }

    @RequestMapping(value = "/search/jest/get")
    public EgResult getArticleList(@RequestParam(value = "content") String content) {
        List<Article> articleList = jestService.searchEntity(content);
        Map<String, Object> data = new HashMap<>();
        data.put("SearchList", articleList);
        return EgResult.ok(data);
    }


    @RequestMapping(value = "/search/jest/all/fields")
    public List<Article> getAllFields(@RequestParam(value = "query") String query) {
        List<Article> ret = null;
        try {
            ret = jestService.search(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }
}
