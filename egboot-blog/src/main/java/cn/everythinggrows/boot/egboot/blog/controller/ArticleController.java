package cn.everythinggrows.boot.egboot.blog.controller;


import cn.everythinggrows.boot.egboot.blog.Utils.EgResult;
import cn.everythinggrows.boot.egboot.blog.aop.loginRequired;
import cn.everythinggrows.boot.egboot.blog.dao.IndexDao;
import cn.everythinggrows.boot.egboot.blog.dao.TypeArticleDao;
import cn.everythinggrows.boot.egboot.blog.dao.UidArticleDao;
import cn.everythinggrows.boot.egboot.blog.event.IndexArticleEvent;
import cn.everythinggrows.boot.egboot.blog.model.EgTypeArticle;
import cn.everythinggrows.boot.egboot.blog.model.egArticle;
import cn.everythinggrows.boot.egboot.blog.model.egUidArticle;
import cn.everythinggrows.boot.egboot.blog.service.RedisClientTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;

@RestController
public class ArticleController {

    public static final String INDEX_ARTICLE_AID = "eg/index/article/aid/";

    @Autowired
    private IndexDao indexDao;
    @Autowired
    private UidArticleDao uidArticleDao;
    @Autowired
    private TypeArticleDao typeArticleDao;
    @Autowired
    private RedisClientTemplate redisClientTemplate;

    @RequestMapping(value = "/blog/article/insert",method = RequestMethod.POST)
    @loginRequired
    public EgResult InsertArticle(@RequestParam(value = "articleName",defaultValue = "") String articleName,
                                  @RequestParam(value = "content",defaultValue = "") String content,
                                  @RequestParam(value = "coverPic",defaultValue = "") String coverPic,
                                  @RequestParam(value = "type",defaultValue = "1") int type,
                                  @RequestParam(value = "title",defaultValue = "") String title,
                                  @RequestHeader(value = "x-eg-session") String session){
        long uid = getUid(session);
        egArticle egArticle = new egArticle();
        long aid = redisClientTemplate.aidGeneration();
        Calendar calendar=Calendar.getInstance();
        int month=calendar.get(Calendar.MONTH)+1;
        int year = calendar.get(Calendar.YEAR);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String today = String.valueOf(year) + String.valueOf(month) + String.valueOf(day);
        int createAt = Integer.parseInt(today);
        egArticle.setId(aid);
        egArticle.setArticleName(articleName);
        egArticle.setUid(uid);
        egArticle.setContent(content);
        egArticle.setCoverPic(coverPic);
        egArticle.setType(type);
        egArticle.setTitle(title);
        egArticle.setCreateAt(createAt);
        int i = indexDao.insertArticle(egArticle);

        egUidArticle egUidArticle = new egUidArticle();
        egUidArticle.setAid(aid);
        egUidArticle.setArticleName(articleName);
        egUidArticle.setCoverPic(coverPic);
        egUidArticle.setUid(uid);
        egUidArticle.setType(type);
        egUidArticle.setTitle(title);
        egUidArticle.setCreateAt(createAt);
        uidArticleDao.insertUidArticle(egUidArticle);

        EgTypeArticle egTypeArticle = new EgTypeArticle();
        egTypeArticle.setType(type);
        egTypeArticle.setArticleName(articleName);
        egTypeArticle.setCoverPic(coverPic);
        egTypeArticle.setAid(aid);
        egTypeArticle.setUid(uid);
        egTypeArticle.setTitle(title);
        typeArticleDao.insertUidArticle(egTypeArticle);

        //增加文章事件
        IndexArticleEvent.IndexArticleScore(aid,1);
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
