package cn.everythinggrows.boot.egboot.user.service;

import cn.everythinggrows.boot.egboot.user.dao.UserDao;
import cn.everythinggrows.boot.egboot.user.dubboapi.IUserAccount;
import cn.everythinggrows.boot.egboot.user.model.egUser;
import cn.everythinggrows.boot.egboot.user.utils.EgResult;
import cn.everythinggrows.boot.egboot.user.utils.UserUtils;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.*;

@Service(version = "1.0.0")
@Component
public class UserAccountImpl implements IUserAccount {
    private static Logger log = LoggerFactory.getLogger(UserAccountImpl.class);

    public static final String EMAIL_VERIFY = "eg/email/verify/";
    public static final String UID_TOKEN = "eg/uid/token/";
    public static final String USER_DETAIL = "eg/user/detail/uid/";
    @Autowired
    private RedisClientTemplate redisClientTemplate;
    @Autowired
    private UserDao userDao;

    @Value("${portraitList}")
    String portraitList;
    @Value("${portrait_dns}")
    String portrait_dns;

    @Override
    public String getMailVerifyAndSend(String toMail) {
        Properties props = new Properties();
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.transport.protocol", "smtp");
        props.put("mail.smtp.host","smtp.163.com");
        // smtp服务器地址

        Session session = Session.getInstance(props);
        session.setDebug(true);
        Message msg = new MimeMessage(session);
        String str="AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz0123456789";
        StringBuilder sb=new StringBuilder(6);
        for(int i=0;i<6;i++)
        {
            char ch=str.charAt(new Random().nextInt(str.length()));
            sb.append(ch);
        }
        String verify = sb.toString();
        try {
            msg.setSubject("<枝丫>注册验证");

        msg.setText("欢迎注册<枝丫>，这是你的验证码：" + verify + "，输入时请区分大小写，有效时间为5分钟，请妥善保存。");
        msg.setFrom(new InternetAddress("everythinggrows@163.com"));
        //发件人邮箱
        msg.setRecipient(Message.RecipientType.TO,
                new InternetAddress(toMail));
        //收件人邮箱
        msg.saveChanges();

        Transport transport = session.getTransport();
        transport.connect("everythinggrows@163.com","egofficialmu123");
        //发件人邮箱,授权码

        transport.sendMessage(msg, msg.getAllRecipients());

        System.out.println("邮件发送成功");
        log.info("{}邮件发送成功============================",toMail);
        transport.close();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        String key = EMAIL_VERIFY + toMail;
        redisClientTemplate.setex(key,5*60,verify);
        return verify;
    }

    @Override
    public EgResult ICreateUser(egUser user, String verfity) {
        long uid = redisClientTemplate.incrUid();
        if(UserUtils.isOffcialUid(uid)){
            uid = redisClientTemplate.incrUid();
        }
        user.setUid(uid);
        String portrait = getRandomPortrait();
        user.setPortrait(portrait);
        String redisVerify = (String) redisClientTemplate.getRedis(EMAIL_VERIFY+user.getEmail());
        if(redisVerify == null){
            redisVerify = "";
        }
        if(!redisVerify.equals(verfity)){
            return EgResult.error(10002,"vertify is error");
        }
        int time = (int)(System.currentTimeMillis()/1000);
        user.setCreateAt(String.valueOf(time));
        int i = userDao.insertUser(user);
        Map<String,String> redisData = new HashMap<>();
        redisData.put("password",user.getPassword());
        redisData.put("uid",String.valueOf(uid));
        redisClientTemplate.hmset(user.getEmail(),redisData);
        if(i > 0){
            String loRet = login(user);
            Map<String,Object> data = new HashMap<>();
            data.put("token",loRet);
            return EgResult.ok(data);
        }else {
            return EgResult.systemError();
        }
    }

    @Override
    public String login(egUser user) {
        String email = user.getEmail();
        Map<String,String> dataMap = redisClientTemplate.hgetAll(email);
        String redisPassword = dataMap.get("password");
        long uid = Long.parseLong(dataMap.get("uid"));
        if(!user.getPassword().equals(redisPassword)){
            return "100004";
        }
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String token = String.valueOf(uid) + ";" + uuid;
        String key = UID_TOKEN + email;
        String uKey = UID_TOKEN + String.valueOf(uid);
        redisClientTemplate.setex(uKey,7*24*60*60,token);
        redisClientTemplate.setex(key,7*24*60*60,token);
        return token;
    }

    public String getRandomPortrait(){
        List<String> list = JSONObject.parseArray(portraitList, String.class);
        int random = new Random().nextInt(list.size()-1);
        String portrait = list.get(random);
        return portrait;
    }

    @Override
    public egUser getUser(long uid){
        String key = USER_DETAIL + String.valueOf(uid);
        Map<String,String> value = redisClientTemplate.hgetAll(key);
        log.info("value:{}..............................................................",value);
        egUser user = new egUser();
        if(value == null || value.isEmpty()) {
           user = userDao.selectEgUser(uid);
           if(user != null) {
               if(user.getPortrait() != null) {
                   String pordns = portrait_dns + user.getPortrait();
                   user.setPortrait(pordns);
               }
               log.info("user:{}",user);
               Map<String, String> redis = Maps.newHashMap();
               redis.put("uid", String.valueOf(user.getUid()));
               redis.put("username", user.getUsername()==null?"":user.getUsername());
               redis.put("password", user.getPassword()==null?"":user.getPassword());
               redis.put("email", user.getEmail()==null?"":user.getEmail());
               redis.put("portrait", user.getPortrait()==null?"":user.getPortrait());
               redis.put("createAt", user.getCreateAt()==null?"":user.getCreateAt());
               redis.put("extend", user.getExtend()==null?"":user.getExtend());
               redisClientTemplate.hmset(key, redis);
               redisClientTemplate.expire(key, 5 * 60);
           }
           return user;
        }
        user.setUid(uid);
        user.setUsername(value.get("username"));
        user.setPassword(value.get("password"));
        user.setEmail(value.get("email"));
        user.setPortrait(value.get("portrait"));
        user.setCreateAt(value.get("createAt"));
        user.setExtend(value.get("extend"));
        return user;
    }

    @Override
    public String dubbotest(String str) {
        log.info("dubboTest:{}=======================================================================",str);
        return "ok";
    }

    public EgResult tokenVerti(String token){
        long uid = getUid(token);
        String uKey = UID_TOKEN + String.valueOf(uid);
        String tokenRet = (String) redisClientTemplate.getRedis(uKey);
        if(!tokenRet.equals(token) || tokenRet == null){
            return EgResult.error(1005,"token is error");
        }
        return EgResult.ok();
    }


    public EgResult userLogout(long uid){
        String uKey = UID_TOKEN + String.valueOf(uid);
        redisClientTemplate.del(uKey);
        return EgResult.ok();
    }


    public long getUid(String session){
        if(session == null || session.length() == 0){
            return 0;
        }
        String[] line = session.split(";");
        long uid = Long.parseLong(line[0]);
        return uid;
    }

}
