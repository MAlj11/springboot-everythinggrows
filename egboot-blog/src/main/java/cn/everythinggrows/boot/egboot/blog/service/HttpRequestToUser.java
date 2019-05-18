package cn.everythinggrows.boot.egboot.blog.service;


import cn.everythinggrows.boot.egboot.blog.Utils.HttpClientUtil;
import cn.everythinggrows.boot.egboot.blog.model.egUser;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class HttpRequestToUser {
    private static Logger log = LoggerFactory.getLogger(HttpRequestToUser.class);
    @Value("${USER_BASE_URL}")
    String userUrl;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;// Eureka客户端，可以获取到服务实例信息


    public egUser getUser(long uid) {

        String url = userUrl + "/detail/" + String.valueOf(uid);
        String ret = HttpClientUtil.doGet(url);
        JSONObject json = JSON.parseObject(ret);
        Map dataMap = JSONObject.toJavaObject(json, Map.class);
        egUser user = new egUser();
        if ((Integer) dataMap.get("status") == 200) {
            JSONObject userdetailStr = (JSONObject) dataMap.get("data");
            Map userdetailMap = JSONObject.toJavaObject(userdetailStr, Map.class);
            JSONObject userStr = (JSONObject) userdetailMap.get("userDetail");
            user = JSONObject.toJavaObject(userStr, egUser.class);
        }
        return user;

    }

    public egUser getUserWithCloud(long uid){
//        // String baseUrl = "http://localhost:8081/user/";
//        // 根据服务名称，获取服务实例
//        List<ServiceInstance> instances = discoveryClient.getInstances("egboot_user");
//        // 因为只有一个UserService,因此我们直接get(0)获取
//        ServiceInstance instance = instances.get(0);
//        // 获取ip和端口信息
//        String baseUrl = "http://"+instance.getHost() + ":" + instance.getPort()+"/user/detail/";
//        egUser user = this.restTemplate.getForObject(baseUrl + uid, egUser.class);
//        return user;

        String url = "http://egboot-user/user/detail/{uid}";
        egUser user = this.restTemplate.getForEntity(url,egUser.class,uid).getBody();
        log.info("user:{}",user);
        return user;
    }

}
