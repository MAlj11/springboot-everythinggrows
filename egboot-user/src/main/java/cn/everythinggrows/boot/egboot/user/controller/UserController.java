package cn.everythinggrows.boot.egboot.user.controller;

import cn.everythinggrows.boot.egboot.user.model.egUser;
import cn.everythinggrows.boot.egboot.user.service.UserAccountImpl;
import cn.everythinggrows.boot.egboot.user.utils.EgResult;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {


    @Autowired
    private UserAccountImpl userAccount;

    @RequestMapping(value = "/user/detail/{uid}", method = RequestMethod.GET)
    public egUser getUserDetail(@PathVariable(value = "uid") long uid) {
        egUser user = userAccount.getUser(uid);
        return user;
    }

    @RequestMapping("/user/send/email")
    public EgResult sentVertify(@RequestParam("email") String email) {
        String vertify = userAccount.getMailVerifyAndSend(email);
        Map<String, Object> data = new HashMap<>();
        data.put("vertify", vertify);
        return EgResult.ok(data);
    }

    @RequestMapping(value = "/user/create", method = RequestMethod.POST)
    public EgResult createUser(@RequestParam(value = "email") String email,
                               @RequestParam(value = "username") String username,
                               @RequestParam(value = "password") String password,
                               @RequestParam(value = "vertify") String vertify) {
        egUser user = new egUser();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        return userAccount.ICreateUser(user, vertify);
    }

    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    public EgResult login(@RequestParam(value = "email") String email,
                          @RequestParam(value = "password") String password) {
        String passwordErrorStatus = "100004";
        egUser user = new egUser();
        user.setEmail(email);
        user.setPassword(password);
        String ret = userAccount.login(user);
        if (passwordErrorStatus.equals(ret)) {
            return EgResult.error(100004, "password is error");
        } else {
            Map<String, String> dataMap = new HashMap<>();
            dataMap.put("token", ret);
            return EgResult.ok(dataMap);
        }
    }

    @RequestMapping(value = "/user/token/vertify", method = RequestMethod.POST)
    public EgResult tokenVer(@RequestParam(value = "token") String token) {
        return userAccount.tokenVerti(token);
    }

    @RequestMapping(value = "/user/logout")
    public EgResult logout(@RequestParam(value = "uid") long uid) {
        return userAccount.userLogout(uid);
    }

    public long getUid(String session) {
        if (session == null || session.length() == 0) {
            return 0;
        }
        String[] line = session.split(";");
        long uid = Long.parseLong(line[0]);
        return uid;
    }


}
