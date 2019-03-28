package cn.everythinggrows.boot.egboot.forum.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;


@Controller
public class SessionController {

    @RequestMapping(value = "/nosession")
    public String getSessionError(HttpServletRequest request){
        request.setAttribute("error","please login first");
        return "error";
    }
}
