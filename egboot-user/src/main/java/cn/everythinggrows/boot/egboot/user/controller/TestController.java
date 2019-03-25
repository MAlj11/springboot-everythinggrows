package cn.everythinggrows.boot.egboot.user.controller;


import cn.everythinggrows.boot.egboot.user.JsonResult;
import cn.everythinggrows.boot.egboot.user.MyException;
import cn.everythinggrows.boot.egboot.user.service.RedisClientTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class TestController {
    @Autowired
    private RedisClientTemplate redisClientTemplate;

    @RequestMapping("/logtest")
    public JsonResult test(@RequestParam("extest") int extest) throws MyException {
        if(extest<5){
            throw new MyException("999","ex");
        }
        return new JsonResult(200,"ok",new HashMap<>());
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
}
