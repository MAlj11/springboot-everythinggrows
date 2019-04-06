package cn.everythinggrows.boot.egboot.blog.service;

import cn.everythinggrows.boot.egboot.blog.Utils.HttpClientUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class HttpRequestToSearch {

    @Value("${SEARCH_BASE_URL}")
    String SEARCH_BASE_URL;

    public int saveEs(long id,String title,String auther){
        String url = SEARCH_BASE_URL + "/jest/save";
        Map<String,String> param = new HashMap<>();
        param.put("id",String.valueOf(id));
        param.put("title",title);
        param.put("auther",auther);

        String ret = HttpClientUtil.doGet(url,param);
        JSONObject json = JSON.parseObject(ret);
        Map dataMap = JSONObject.toJavaObject(json, Map.class);
        if((Integer)dataMap.get("status")==200){
            return 200;
        }else{
            return 10004;
        }
    }
}
