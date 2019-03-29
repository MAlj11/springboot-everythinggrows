package cn.everythinggrows.boot.egboot.forum.controller;


import cn.everythinggrows.boot.egboot.forum.Utils.EgResult;
import cn.everythinggrows.boot.egboot.forum.dubboapi.IUserAccount;
import cn.everythinggrows.boot.egboot.forum.model.egUser;
import cn.everythinggrows.boot.egboot.forum.service.HttpRequestToUser;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Reference
    private IUserAccount iUserAccount;
    @Autowired
    private HttpRequestToUser httpRequestToUser;

    @RequestMapping("/dubbotest")
    public String test(@RequestParam(value = "str") String str){
        iUserAccount.dubbotest(str);
        return "ok";
    }

    @RequestMapping(value = "/httpclienttest")
    public EgResult httptest(@RequestParam(value = "uid") long uid){
        egUser user = httpRequestToUser.getUser(uid);
        return EgResult.ok(user);
    }
}
