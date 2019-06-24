package cn.everythinggrows.boot.egboot.portal.controller;


import cn.everythinggrows.boot.egboot.portal.Utils.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
@Controller
public class userController {

    @Value("${USER_BASE_URL}")
    String USER_BASE_URL;

    private Logger logger = LoggerFactory.getLogger(userController.class);

    @RequestMapping(value = "/emailVerify.html", method = RequestMethod.GET)
    public EgResult getEmailVerify(@RequestParam(value = "reEmail") String reEmail,
                                   ModelAndView modelAndView) {
        if (reEmail == null) {
            return EgResult.error(10002, "email is null");
        }
        String url = USER_BASE_URL + "/send/email";
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("email", reEmail);
        JSONObject json = HttpRequsetUtil.requestGet(url, paramMap);

        String vertify = json.getString("vertify");
        logger.info("{}的验证码：{}======================================================", reEmail, vertify);
        modelAndView.addObject("vertify", vertify);
        return EgResult.ok();
    }

    @RequestMapping(value = "/register.html", method = RequestMethod.POST)
    public String Register(HttpServletRequest request,
                           ModelAndView modelAndView,
                           HttpServletResponse response) throws UnsupportedEncodingException {
        HttpSession session = request.getSession();
        String reEmail = request.getParameter("reEmail");
        String rePassword = request.getParameter("rePassword");
        String reVerify = request.getParameter("reVerify");
        String reUsername = request.getParameter("reUsername");

        String password = EncryptHelper.encrypt(rePassword.trim(), EncryptType.SHA1);
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("email", reEmail);
        paramMap.put("username", reUsername);
        paramMap.put("password", password);
        paramMap.put("vertify", reVerify);


        String url = USER_BASE_URL + "/create";
        JSONObject json = HttpRequsetUtil.requestPost(url, paramMap);
        String token = json.getString("token");

        logger.info("userController token:{}", token);

        //将token存入浏览器的cookie中
        Cookie cookie = new Cookie("eg_cookie_token", URLEncoder.encode(token, "utf-8"));
        cookie.setMaxAge(7 * 24 * 60 * 60);
        response.addCookie(cookie);

        session.setAttribute("tokenVertify", true);
        return "lw-index";
    }


    @RequestMapping(value = "/login.html", method = RequestMethod.POST)
    public String Login(HttpServletRequest request,
                        ModelAndView modelAndView,
                        HttpServletResponse response) throws UnsupportedEncodingException {
        HttpSession session = request.getSession();
        String loEmail = request.getParameter("loEmail");
        String loPassword = request.getParameter("passwordLog");
        if(StringUtils.isEmpty(loEmail) || StringUtils.isEmpty(loPassword)){
            session.setAttribute("loginError", "用户名或密码不能为空");
            return "lw-log";
        }
        String password = EncryptHelper.encrypt(loPassword.trim(), EncryptType.SHA1);

        String url = USER_BASE_URL + "/login";
        Map<String, String> param = new HashMap<>();
        param.put("email", loEmail);
        param.put("password", password);
        String ret = HttpClientUtil.doPost(url, param);
        JSONObject json = JSON.parseObject(ret);
        Map dataMap = JSONObject.toJavaObject(json, Map.class);
        if ((Integer) dataMap.get("status") == 200) {
            JSONObject dataStr = (JSONObject) dataMap.get("data");
            String token = dataStr.getString("token");
            logger.info("token:{}", token);

            //将token存入浏览器的cookie中
            Cookie cookie = new Cookie("eg_cookie_token", URLEncoder.encode(token, "utf-8"));
            cookie.setMaxAge(7 * 24 * 60 * 60);
            response.addCookie(cookie);

            //session存入登陆成功验证
            session.setAttribute("tokenVertify", true);
            return "lw-index";
        } else if ((Integer) dataMap.get("status") == 100004) {
            session.setAttribute("loginError", "密码错误");
            return "lw-log";
        } else {
            session.setAttribute("loginError", "用户不存在");
            return "lw-log";
        }
    }


    @RequestMapping(value = "/logout.html")
    public String logout(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
//         String tokenVal = CookieUtils.getCookieValue(request,"eg_cookie_token");
//         tokenVal = URLDecoder.decode(tokenVal,"utf-8");
//         long uid = getUid(tokenVal);
//         String logoutUrl = USER_BASE_URL + "/logout" + "?uid=" + String.valueOf(uid);
//         String ret = HttpClientUtil.doGet(logoutUrl);

        //将cookie从浏览器中删除
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                // 找到需要删除的Cookie
                if ("eg_cookie_token".equals(cookie.getName())) {
                    // 设置生存期为0
                    cookie.setMaxAge(0);
                    // 设回Response中生效
                    response.addCookie(cookie);
                }
            }
        }

        HttpSession session = request.getSession();
        session.setAttribute("tokenVertify", false);
        return "lw-index";
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
