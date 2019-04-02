package cn.everythinggrows.boot.egboot.portal.controller;



import cn.everythinggrows.boot.egboot.portal.Utils.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
                                   ModelAndView modelAndView){
        if(reEmail == null){
            return EgResult.error(10002,"email is null");
        }
        String url = USER_BASE_URL + "/send/email";
        Map<String,String> paramMap = new HashMap<>();
        paramMap.put("email",reEmail);
        JSONObject json = HttpRequsetUtil.requestGet(url,paramMap);

        String vertify = json.getString("vertify");
        logger.info("{}的验证码：{}======================================================",reEmail,vertify);
        modelAndView.addObject("vertify",vertify);
        return EgResult.ok();
    }

    @RequestMapping(value = "/register.html", method = RequestMethod.POST)
    public String Register(HttpServletRequest request,
                           ModelAndView modelAndView){

        String reEmail = request.getParameter("reEmail");
        String rePassword = request.getParameter("rePassword");
        String reVerify = request.getParameter( "reVerify" );
        String reUsername = request.getParameter("reUsername");

        String password = EncryptHelper.encrypt( rePassword.trim() , EncryptType.SHA1 );
        Map<String,String> paramMap = new HashMap<>();
        paramMap.put("email",reEmail);
        paramMap.put("username",reUsername);
        paramMap.put("password",password);
        paramMap.put("vertify",reVerify);


        String url = USER_BASE_URL + "/create";
        JSONObject json = HttpRequsetUtil.requestPost(url,paramMap);
        String token = json.getString("token");
        modelAndView.addObject("token",token);
        return "lw-index";
    }


    @RequestMapping(value = "/login.html", method = RequestMethod.POST)
    public  String Login(HttpServletRequest request,
                         ModelAndView modelAndView){
        String loEmail = request.getParameter("loEmail");
        String loPassword = request.getParameter("passwordLog");
        String password = EncryptHelper.encrypt( loPassword.trim() , EncryptType.SHA1 );

        String url = USER_BASE_URL + "/login";
        Map<String,String> param = new HashMap<>();
        param.put("email",loEmail);
        param.put("password",password);
        String ret = HttpClientUtil.doPost(url,param);
        JSONObject json = JSON.parseObject(ret);
        Map dataMap = JSONObject.toJavaObject(json, Map.class);
        if((Integer)dataMap.get("status")==200){
            JSONObject dataStr  = (JSONObject) dataMap.get("data");
            String token = dataStr.getString("token");
            modelAndView.addObject("token",token);
            return "lw-index";
        }else if ((Integer)dataMap.get("status")==100004){
             modelAndView.addObject("loginError","password is error");
             return "lw-log";
        }else{
            modelAndView.addObject("loginError","other error");
            return "lw-log";
        }

    }



}
