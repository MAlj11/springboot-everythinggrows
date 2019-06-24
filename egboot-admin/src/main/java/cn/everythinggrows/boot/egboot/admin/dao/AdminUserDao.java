package cn.everythinggrows.boot.egboot.admin.dao;


import cn.everythinggrows.boot.egboot.admin.Utils.DBUtils;
import cn.everythinggrows.boot.egboot.admin.datasource.DatabaseContextHolder;
import cn.everythinggrows.boot.egboot.admin.datasource.DatabaseType;
import cn.everythinggrows.boot.egboot.admin.model.User;
import cn.everythinggrows.boot.egboot.admin.model.egUser;
import com.google.common.collect.Maps;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class AdminUserDao {
    @Autowired
    private SqlSessionTemplate adminSqlSession;

    public List<egUser> selectEgUser(int dbKey,String tableName) {
        Map<String, Object> dataMap = Maps.newHashMap();
        dataMap.put("tableName", tableName);
        DatabaseType type = DatabaseType.getType(dbKey);
        DatabaseContextHolder.setDatabaseType(type);
        List<egUser> user = adminSqlSession.selectList("AdminUserDao.selectAll", dataMap);
        DatabaseContextHolder.clearDatabaseType();
        return user;
    }

    public int deleteUser(long uid){
        Map<String, Object> dataMap = Maps.newHashMap();
        dataMap.put("tableName", "eg_user_"+ DBUtils.getTableKey(uid));
        dataMap.put("uid",uid);
        int dbKey = DBUtils.getDBKey(uid);
        DatabaseType type = DatabaseType.getType(dbKey);
        DatabaseContextHolder.setDatabaseType(type);
        int i  = adminSqlSession.delete("AdminUserDao.deleteUser", dataMap);
        DatabaseContextHolder.clearDatabaseType();
        return i;
    }

    public egUser getUser(long uid){
        Map<String, Object> dataMap = Maps.newHashMap();
        dataMap.put("tableName", "eg_user_"+ DBUtils.getTableKey(uid));
        dataMap.put("uid",uid);
        int dbKey = DBUtils.getDBKey(uid);
        DatabaseType type = DatabaseType.getType(dbKey);
        DatabaseContextHolder.setDatabaseType(type);
        egUser user  = adminSqlSession.selectOne("AdminUserDao.selectUser", dataMap);
        DatabaseContextHolder.clearDatabaseType();
        return user;
    }



    public List<User> selectAdmin(){
        Map<String, Object> dataMap = Maps.newHashMap();
        dataMap.put("tableName", "eg_admin_user");
        DatabaseType type = DatabaseType.getType(1);
        DatabaseContextHolder.setDatabaseType(type);
        List<User> admin = adminSqlSession.selectList("AdminDao.selectAll", dataMap);
        DatabaseContextHolder.clearDatabaseType();
        return admin;
    }
}
