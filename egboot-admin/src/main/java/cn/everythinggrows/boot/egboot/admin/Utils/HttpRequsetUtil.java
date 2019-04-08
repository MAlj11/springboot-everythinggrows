package cn.everythinggrows.boot.egboot.admin.Utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.Map;

public class HttpRequsetUtil {

    public static JSONObject requestGet(String dataName,String url,Map<String, String> param){
        JSONObject retStr = null;
        String ret = HttpClientUtil.doGet(url,param);
        JSONObject json = JSON.parseObject(ret);
        Map dataMap = JSONObject.toJavaObject(json, Map.class);
        if((Integer)dataMap.get("status")==200){
            JSONObject dataStr = (JSONObject) dataMap.get("data");
            Map map = JSONObject.toJavaObject(dataStr,Map.class);
            retStr = (JSONObject) map.get(dataName);
        }
        return retStr;
    }

    public static JSONObject requestGet(String url,Map<String, String> param){
        JSONObject retStr = null;
        String ret = HttpClientUtil.doGet(url,param);
        JSONObject json = JSON.parseObject(ret);
        Map dataMap = JSONObject.toJavaObject(json, Map.class);
        if((Integer)dataMap.get("status")==200){
            retStr = (JSONObject) dataMap.get("data");
        }
        return retStr;

    }



    public static JSONObject requestPost(String dataName,String url,Map<String,String> param){
        JSONObject retStr = null;
        String ret = HttpClientUtil.doPost(url,param);
        JSONObject json = JSON.parseObject(ret);
        Map dataMap = JSONObject.toJavaObject(json, Map.class);
        if((Integer)dataMap.get("status")==200){
            JSONObject dataStr = (JSONObject) dataMap.get("data");
            Map map = JSONObject.toJavaObject(dataStr,Map.class);
            retStr = (JSONObject) map.get(dataName);
        }
        return retStr;
    }

    public static JSONObject requestPost(String url,Map<String,String> param){
        JSONObject retStr = null;
        String ret = HttpClientUtil.doPost(url,param);
        JSONObject json = JSON.parseObject(ret);
        Map dataMap = JSONObject.toJavaObject(json, Map.class);
        if((Integer)dataMap.get("status")==200){
           retStr = (JSONObject) dataMap.get("data");
        }
        return retStr;
    }
}
