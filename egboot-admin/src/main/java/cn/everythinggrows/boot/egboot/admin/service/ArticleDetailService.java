package cn.everythinggrows.boot.egboot.admin.service;//package cn.everythinggrows.boot.egboot.portal.service;
//
//
//import cn.everythinggrows.boot.egboot.portal.Utils.HttpClientUtil;
//import cn.everythinggrows.boot.egboot.portal.model.Comment;
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Map;
//
//@Service
//public class ArticleDetailService {
//    @Value("${BASE_URL_BLOG}")
//    String BASE_URL_BLOG;
//
//    public List<Comment> getCommentDetail(long aid){
//        String url = BASE_URL_BLOG + "/comment/" + String.valueOf(aid);
//        String httpRet = HttpClientUtil.doGet(url);
//        JSONObject json = JSON.parseObject(httpRet);
//        Map<String,Object> articleMap = JSONObject.toJavaObject(json, Map.class);
//        List<Comment> comments = null;
//        if(articleMap.get("errorCode").equals("0")){
//            comments = (List<Comment>) articleMap.get("articleList");
//        }
////        String ret =  JSONObjectSON.toJSONString(comments);
//        return comments;
//    }
//}
