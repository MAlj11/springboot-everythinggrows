package cn.everythinggrows.boot.egboot.admin.service;


import cn.everythinggrows.boot.egboot.admin.Utils.HttpClientUtil;
import cn.everythinggrows.boot.egboot.admin.dao.AdminUserDao;
import cn.everythinggrows.boot.egboot.admin.model.egUser;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserService {
    private Logger logger = LoggerFactory.getLogger(UserService.class);
    @Value("${USER_BASE_URL}")
    String USER_BASE_URL;
    @Autowired
    private AdminUserDao adminUserDao;

    public egUser getUser(long uid) {
        egUser user = adminUserDao.getUser(uid);
        return user;

    }
}
