package cn.everythinggrows.boot.egboot.user.controller;


import cn.everythinggrows.boot.egboot.user.JsonResult;
import cn.everythinggrows.boot.egboot.user.MyException;
import cn.everythinggrows.boot.egboot.user.dao.UserTestDao;
import cn.everythinggrows.boot.egboot.user.model.egUser;
import cn.everythinggrows.boot.egboot.user.service.RedisClientTemplate;
import cn.everythinggrows.boot.egboot.user.service.UserAccountImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.QueryAnnotation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class TestController {
    @Autowired
    private RedisClientTemplate redisClientTemplate;

    @Autowired
    private UserTestDao userTestDao;
    @Autowired
    private UserAccountImpl userAccount;

    @RequestMapping("/logtest")
    public JsonResult test(@RequestParam("extest") int extest) throws MyException {
        if(extest<5){
            throw new MyException("999","ex");
        }
        return new JsonResult(200,"ok",new HashMap<>(0));
    }

    @RequestMapping("/redistest")
        public String redistest(@RequestParam("key") String key,
                                    @RequestParam("value") String value){
        boolean flag = redisClientTemplate.setToRedis(key,value);
        if(flag){
            return JsonResult.success();
        }else{
            return JsonResult.failed();
        }
        }

    @RequestMapping("/usertest")
    public String usertest(@RequestParam("email") String email,
                               @RequestParam("password") String password){
        egUser user = new egUser();
        long uid = redisClientTemplate.incrUid();
        user.setUid(uid);
        user.setEmail(email);
        user.setPassword(password);
        user.setPortrait("111.com/aaaaa");
        user.setUsername(email);
        int i = userTestDao.insertUser(user);
        return JsonResult.success();
    }

    @RequestMapping(value = "/image")
    public String image(){
        String str = userAccount.getRandomPortrait();
        return str;
    }
}
