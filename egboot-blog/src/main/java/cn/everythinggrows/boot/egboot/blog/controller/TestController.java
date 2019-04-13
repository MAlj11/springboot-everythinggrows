package cn.everythinggrows.boot.egboot.blog.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private ArticleController articleController;

    @RequestMapping("/covertest")
    public String getCover() {
        String cover = articleController.getRandomCoverPic();
        return cover;
    }
}
