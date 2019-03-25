package cn.everythinggrows.boot.egboot.user.controller;

import cn.everythinggrows.boot.egboot.user.JsonResult;
import cn.everythinggrows.boot.egboot.user.model.egUser;
import cn.everythinggrows.boot.egboot.user.service.UserAccountImpl;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserController {


    @Autowired
    private UserAccountImpl userAccount;

    @RequestMapping(value = "/user/detail/{uid}")
    public String getUserDetail(@PathVariable(value = "0") long uid){
         egUser user = userAccount.getUser(uid);
         Map<String,Object> ret = Maps.newHashMap();
         ret.put("userDetail",user);
         return JsonResult.success(ret);
    }

    @RequestMapping("/user/send/email")
    public String sentVertify(@RequestParam("email") String email){
        String vertify = userAccount.getMailVerifyAndSend(email);
        return vertify;
    }
}
