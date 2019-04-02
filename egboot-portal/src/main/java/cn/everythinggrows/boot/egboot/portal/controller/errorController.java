package cn.everythinggrows.boot.egboot.portal.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class errorController {

    @RequestMapping(value = "/nosession")
    public String getSessionError(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.setAttribute("error","please login first");
        return "error";
    }
}
