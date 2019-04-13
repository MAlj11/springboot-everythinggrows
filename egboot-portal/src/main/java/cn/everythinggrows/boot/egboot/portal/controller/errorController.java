package cn.everythinggrows.boot.egboot.portal.controller;


import cn.everythinggrows.boot.egboot.portal.Utils.CookieUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;

@RestController
public class errorController {

    @RequestMapping(value = "/nosession")
    public String getSessionError(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("error", "please login first");
        return "error";
    }

    @RequestMapping(value = "/cookie")
    public String getcookie(HttpServletRequest request) {
        String token = CookieUtils.getCookieValue(request, "eg_cookie");
        return token;
    }

    @RequestMapping(value = "/setcook")
    //添加缓存
    public String add_cookie(HttpServletResponse response) throws UnsupportedEncodingException {

        //将用户名存入cookie 并且设置cookie存在时长
        Cookie cookie_username = new Cookie("eg_cookie", "cook_eg");
        cookie_username.setMaxAge(5 * 60);
        response.addCookie(cookie_username);
        //将密码存入cookie 并且设置cookie存在时长
        return "ok";
    }

    @RequestMapping("/del_cookie")
    public String del_cookie(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                // 找到需要删除的Cookie
                if ("username".equals(cookie.getName())) {
                    // 设置生存期为0
                    cookie.setMaxAge(0);
                    // 设回Response中生效
                    response.addCookie(cookie);
                }
                if ("userPassword".equals(cookie.getName())) {
                    // 设置生存期为0
                    cookie.setMaxAge(0);
                    // 设回Response中生效
                    response.addCookie(cookie);
                }
            }
        }
        return null;
    }

}
