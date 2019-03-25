package cn.everythinggrows.boot.egboot.user.dao;

import cn.everythinggrows.boot.egboot.user.datasource.DatabaseContextHolder;
import cn.everythinggrows.boot.egboot.user.datasource.DatabaseType;
import cn.everythinggrows.boot.egboot.user.utils.DBUtils;
import com.google.common.collect.Maps;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class CreateAtDao {

    @Autowired
    private SqlSessionTemplate userSqlSession;

    public int insertCreateAt(long uid,int time){
        Map<String, Object> dataMap = Maps.newHashMap();
        dataMap.put("uid",uid);
        dataMap.put("createAt",time);
        dataMap.put("tableName","eg_user_caeatetime_" + DBUtils.getTableKey(uid));
        int dbKey = DBUtils.getDBKey(uid);
        DatabaseType type = DatabaseType.getType(dbKey);
        DatabaseContextHolder.setDatabaseType(type);
        int i = userSqlSession.insert("userCreateAt.insertUserCreateAt",dataMap);
        DatabaseContextHolder.clearDatabaseType();
        return i;
    }
}
