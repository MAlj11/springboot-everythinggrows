package cn.everythinggrows.boot.egboot.user.dao;

import cn.everythinggrows.boot.egboot.user.datasource.DatabaseContextHolder;
import cn.everythinggrows.boot.egboot.user.datasource.DatabaseType;
import cn.everythinggrows.boot.egboot.user.model.emailUid;
import cn.everythinggrows.boot.egboot.user.utils.DBUtils;
import cn.everythinggrows.boot.egboot.user.utils.beanUtils;
import com.google.common.collect.Maps;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class EmailToUidDao {

    @Autowired
    private SqlSessionTemplate userSqlSession;


    public int insertEmailUid(emailUid emailUid){
        Map<String,Object> dataMap = beanUtils.bean2map(emailUid);
        dataMap.put("tableName","eg_user_" + DBUtils.getTableKey(emailUid.getHashid()));
        int dbKey = DBUtils.getDBKey(emailUid.getHashid());
        DatabaseType type = DatabaseType.getType(dbKey);
        DatabaseContextHolder.setDatabaseType(type);
        int i = userSqlSession.insert("emailToUserDao.insertUser",dataMap);
        DatabaseContextHolder.clearDatabaseType();
        return i;
    }

    public emailUid selectEmailUid(long hashid){
        Map<String,Object> dataMap = Maps.newHashMap();
        dataMap.put("tableName","email_uid_" + DBUtils.getTableKey(hashid));
        int dbKey = DBUtils.getDBKey(hashid);
        DatabaseType type = DatabaseType.getType(dbKey);
        DatabaseContextHolder.setDatabaseType(type);
        emailUid emailuid = userSqlSession.selectOne("emailToUserDao.selectUser",dataMap);
        DatabaseContextHolder.clearDatabaseType();
        return emailuid;
    }

}
