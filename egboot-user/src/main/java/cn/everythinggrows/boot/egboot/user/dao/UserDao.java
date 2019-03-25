package cn.everythinggrows.boot.egboot.user.dao;


import cn.everythinggrows.boot.egboot.user.datasource.DatabaseContextHolder;
import cn.everythinggrows.boot.egboot.user.datasource.DatabaseType;
import cn.everythinggrows.boot.egboot.user.model.egUser;
import cn.everythinggrows.boot.egboot.user.utils.DBUtils;
import cn.everythinggrows.boot.egboot.user.utils.beanUtils;
import com.google.common.collect.Maps;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class UserDao {
    @Autowired
    private SqlSessionTemplate userSqlSession;


    public int insertUser(egUser user){
        Map<String,Object> dataMap = beanUtils.bean2map(user);
        dataMap.put("tableName","eg_user_" + DBUtils.getTableKey(user.getUid()));
        int dbKey = DBUtils.getDBKey(user.getUid());
        DatabaseType type = DatabaseType.getType(dbKey);
        DatabaseContextHolder.setDatabaseType(type);
        int i = userSqlSession.insert("userDao.insertUser",dataMap);
        DatabaseContextHolder.clearDatabaseType();
        return i;
        }

    public egUser selectEgUser(long uid){
        Map<String,Object> dataMap = Maps.newHashMap();
        dataMap.put("tableName","eg_user_" + DBUtils.getTableKey(uid));
        dataMap.put("uid",uid);
        int dbKey = DBUtils.getDBKey(uid);
        DatabaseType type = DatabaseType.getType(dbKey);
        DatabaseContextHolder.setDatabaseType(type);
        egUser user = userSqlSession.selectOne("userDao.selectUser",dataMap);
        DatabaseContextHolder.clearDatabaseType();
        return user;
    }

}
