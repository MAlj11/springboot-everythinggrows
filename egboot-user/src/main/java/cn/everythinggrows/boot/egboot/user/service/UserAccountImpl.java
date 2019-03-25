//package cn.everythinggrows.boot.egboot.user.service;
//
//import cn.everythinggrows.boot.egboot.user.model.egUser;
//import cn.everythinggrows.boot.egboot.user.model.emailUid;
//import com.alibaba.fastjson.JSONObject;
//import com.google.common.collect.Maps;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import redis.clients.jedis.JedisCluster;
//
//import javax.mail.Message;
//import javax.mail.MessagingException;
//import javax.mail.Session;
//import javax.mail.Transport;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;
//import java.util.*;
//
//@Service
//public class UserAccountImpl implements IUserAccount {
//    public static final String EMAIL_VERIFY = "eg/email/verify/";
//    public static final String UID_TOKEN = "eg/uid/token/";
//    public static final String USER_DETAIL = "eg/user/detail/uid/";
//    @Autowired
//    private JedisCluster jedisCluster;
//    @Autowired
//    private userDao userDao;
//    @Autowired
//    private createAtDao createAtDao;
//    @Autowired
//    private emailToUidDao emailToUidDao;
//
//    @Value("${portraitList}")
//    String portraitList;
//
//    @Override
//    public String getMailVerifyAndSend(String toMail) {
//        Properties props = new Properties();
//        props.setProperty("mail.smtp.auth", "true");
//        props.setProperty("mail.transport.protocol", "smtp");
//        props.put("mail.smtp.host","smtp.163.com");
//        // smtp服务器地址
//
//        Session session = Session.getInstance(props);
//        session.setDebug(true);
//        Message msg = new MimeMessage(session);
//        String str="AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz0123456789";
//        StringBuilder sb=new StringBuilder(6);
//        for(int i=0;i<6;i++)
//        {
//            char ch=str.charAt(new Random().nextInt(str.length()));
//            sb.append(ch);
//        }
//        String verify = sb.toString();
//        try {
//            msg.setSubject("<枝丫>注册验证");
//
//        msg.setText("欢迎注册<枝丫>，这是你的验证码：" + verify + "，输入时请区分大小写，有效时间为5分钟，请妥善保存。");
//        msg.setFrom(new InternetAddress("everythinggrows@163.com"));
//        //发件人邮箱
//        msg.setRecipient(Message.RecipientType.TO,
//                new InternetAddress(toMail));
//        //收件人邮箱
//        msg.saveChanges();
//
//        Transport transport = session.getTransport();
//        transport.connect("everythinggrows@163.com","egofficialmu123");
//        //发件人邮箱,授权码
//
//        transport.sendMessage(msg, msg.getAllRecipients());
//
//        System.out.println("邮件发送成功");
//        transport.close();
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        }
//        String key = EMAIL_VERIFY + toMail;
//        jedisCluster.setex(key,5*60,verify);
//        return verify;
//    }
//
//    @Override
//    public String ICreateUser(egUser user, String verfity) {
//        long uid = idGeneration.uidGeneration();
//        if(UserUtils.isOffcialUid(uid)){
//            uid = idGeneration.uidGeneration();
//        }
//        user.setUid(uid);
//        String portrait = getRandomPortrait();
//        user.setPortrait(portrait);
//        String redisVerify = jedisCluster.get(EMAIL_VERIFY+user.getEmail());
//        if(redisVerify == null){
//            redisVerify = "";
//        }
//        if(!redisVerify.equals(verfity)){
//            return "10002";
//        }
//        int hashEmail = user.getEmail().hashCode();
//        emailUid emailUid = new emailUid();
//        emailUid.setHashid((long) hashEmail);
//        emailUid.setEmail(user.getEmail());
//        emailUid.setUid(uid);
//        int time = (int)System.currentTimeMillis()/1000;
//        int i = userDao.insertUser(user);
//        int j = createAtDao.insertCreateAt(user.getUid(),time);
//        int m = emailToUidDao.insertEmailUid(emailUid);
//        if(i > 0 && j > 0 && m > 0){
//            String loRet = login(user);
//            return loRet;
//        }else {
//            return "10003";
//        }
//    }
//
//    @Override
//    public String login(egUser user) {
//        Long uid = user.getUid();
//        if(uid == null || uid.equals(0L)){
//            String email = user.getEmail();
//            long hashEmail = (long)email.hashCode();
//            emailUid emailUid = emailToUidDao.selectEmailUid(hashEmail);
//            uid = emailUid.getUid();
//        }
//        egUser user1 = userDao.selectEgUser(uid);
//        String password = user1.getPassword();
//        if(!password.equals(user.getPassword())){
//            return "100001";
//        }
//        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
//        String token = String.valueOf(uid) + ";" + uuid;
//        String key = UID_TOKEN + user1.getEmail();
//        jedisCluster.setex(key,7*24*60*60,token);
//        return token;
//    }
//
//    public String getRandomPortrait(){
//        List<String> list = JSONObject.parseArray(portraitList, String.class);
//        int random = new Random().nextInt(list.size()-1);
//        String portrait = list.get(random);
//        return portrait;
//    }
//
//    @Override
//    public egUser getUser(long uid){
//        String key = USER_DETAIL + String.valueOf(uid);
//        Map<String,String> value = jedisCluster.hgetAll(key);
//        egUser user = null;
//        if(value == null) {
//           user = userDao.selectEgUser(uid);
//           Map<String,String> redis = Maps.newHashMap();
//           redis.put("uid",String.valueOf(user.getUid()));
//           redis.put("username",user.getUsername());
//           redis.put("password",user.getPassword());
//           redis.put("email",user.getEmail());
//           redis.put("portrait",user.getPortrait());
//           jedisCluster.hmset(key,redis);
//           jedisCluster.expire(key,5*60);
//           return user;
//        }
//        user.setUid(uid);
//        user.setUsername(value.get("uid"));
//        user.setUsername(value.get("username"));
//        user.setPassword(value.get("password"));
//        user.setEmail(value.get("email"));
//        user.setPortrait(value.get("portrait"));
//        return user;
//    }
//}
