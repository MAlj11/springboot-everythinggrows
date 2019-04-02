//package cn.everythinggrows.boot.egboot.portal.service;
//
//
//import cn.everythinggrows.boot.egboot.portal.Utils.HttpClientUtil;
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import java.util.Map;
//
//@Service
//public class IndexService {
//    @Value("${BASE_URL_BLOG}")
//    String BASE_URL_BLOG;
//    @Value("${BASE_URL_USER}")
//    String BASE_URL_USER;
//    public String getDetailArticle(long aid){
//        if(aid == 0){
//            return "";
//        }
//        String url = BASE_URL_BLOG + "/detial/article/" + String.valueOf(aid);
//        String ret = HttpClientUtil.doGet(url);
//        JSONObject json = JSON.parseObject(ret);
//        Map<String,Object> ArticleDetail = JSONObject.toJavaObject(json, Map.class);
//        String egArticle = null;
//        if(ArticleDetail.get("errorCode").equals("0")){
//            egArticle = (String) ArticleDetail.get("articleWithTypeList");
//        }
//        return egArticle;
//    }
//
//    public String getUserDetail(long uid){
//       if(uid == 0){
//           return "";
//       }
//       String url = BASE_URL_USER + "/detail/" + String.valueOf(uid);
//       String ret = HttpClientUtil.doGet(url);
//       JSONObject json  =JSON.parseObject(ret);
//       Map<String,Object> UserDetail = JSONObject.toJavaObject(json,Map.class);
//       String user = null;
//       if(UserDetail.get("errorCode").equals("0")){
//           user = (String)UserDetail.get("userDetail");
//       }
//       return user;
//    }
//
//}
