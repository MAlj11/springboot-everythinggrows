package cn.everythinggrows.boot.egboot.portal.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ForgetPassword {

    @RequestMapping(value = "/forgetPassword.html")
    public String forgetpw() {
        return "forgetpw";
    }
}
