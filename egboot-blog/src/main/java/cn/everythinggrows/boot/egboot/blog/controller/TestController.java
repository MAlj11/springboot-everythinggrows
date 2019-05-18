package cn.everythinggrows.boot.egboot.blog.controller;


import cn.everythinggrows.boot.egboot.blog.Utils.EgResult;
import cn.everythinggrows.boot.egboot.blog.model.egUser;
import cn.everythinggrows.boot.egboot.blog.service.HttpRequestToUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private ArticleController articleController;
    @Autowired
    private HttpRequestToUser httpRequestToUser;

    @RequestMapping("/covertest")
    public String getCover() {
        String cover = articleController.getRandomCoverPic();
        return cover;
    }

    @RequestMapping(value = "/test/getuser/{uid}")
    public EgResult getUser(@PathVariable(value = "uid") long uid){
        egUser user = httpRequestToUser.getUserWithCloud(uid);
        return EgResult.ok(user);
    }
}
