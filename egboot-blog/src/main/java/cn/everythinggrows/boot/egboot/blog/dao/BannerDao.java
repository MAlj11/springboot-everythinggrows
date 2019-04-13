package cn.everythinggrows.boot.egboot.blog.dao;

import cn.everythinggrows.boot.egboot.blog.Utils.beanUtils;
import cn.everythinggrows.boot.egboot.blog.datasource.DatabaseContextHolder;
import cn.everythinggrows.boot.egboot.blog.datasource.DatabaseType;
import cn.everythinggrows.boot.egboot.blog.model.Banner;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public class BannerDao {

    @Autowired
    private SqlSessionTemplate blogSqlSession;

    public List<Banner> getBanner() {
        DatabaseType type = DatabaseType.getType(0);
        DatabaseContextHolder.setDatabaseType(type);
        List<Banner> bannerList = blogSqlSession.selectList("BannerDao.selectBanner");
        DatabaseContextHolder.clearDatabaseType();
        return bannerList;
    }

    public int insertBanner(Banner banner) {
        Map<String, Object> dataMap = beanUtils.bean2map(banner);
        DatabaseType type = DatabaseType.getType(0);
        DatabaseContextHolder.setDatabaseType(type);
        int i = blogSqlSession.insert("BannerDao.insertBanner", dataMap);
        DatabaseContextHolder.clearDatabaseType();
        return i;
    }
}
