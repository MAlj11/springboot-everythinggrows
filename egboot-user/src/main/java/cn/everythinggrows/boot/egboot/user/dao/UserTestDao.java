package cn.everythinggrows.boot.egboot.user.dao;


import cn.everythinggrows.boot.egboot.user.datasource.DatabaseContextHolder;
import cn.everythinggrows.boot.egboot.user.datasource.DatabaseType;
import cn.everythinggrows.boot.egboot.user.model.egUser;
import cn.everythinggrows.boot.egboot.user.utils.DBUtils;
import cn.everythinggrows.boot.egboot.user.utils.beanUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;


@Repository
public class UserTestDao {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(UserTestDao.class);
    @Autowired
    private SqlSessionFactory sqlSessionFactory;
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    public int insertUser(egUser user) {
        Map<String, Object> dataMap = beanUtils.bean2map(user);
        log.info("userdataMap ------------------- {}", dataMap);
        dataMap.put("tableName", "eg_user_" + DBUtils.getTableKey(user.getUid()));
        int dbKey = DBUtils.getDBKey(user.getUid());
        DatabaseType type = DatabaseType.getType(dbKey);
        DatabaseContextHolder.setDatabaseType(type);
        int i = sqlSessionTemplate.insert("userTestDao.insertUser", dataMap);
        DatabaseContextHolder.clearDatabaseType();
        return i;
    }
}
