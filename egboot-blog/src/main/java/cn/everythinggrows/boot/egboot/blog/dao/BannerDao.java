package cn.everythinggrows.boot.egboot.blog.dao;

import cn.everythinggrows.boot.egboot.blog.datasource.DatabaseContextHolder;
import cn.everythinggrows.boot.egboot.blog.datasource.DatabaseType;
import cn.everythinggrows.boot.egboot.blog.model.Banner;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class BannerDao {

    @Autowired
    private SqlSessionTemplate blogSqlSession;

    public List<Banner> getBanner(){
        DatabaseType type = DatabaseType.getType(0);
        DatabaseContextHolder.setDatabaseType(type);
        List<Banner> bannerList = blogSqlSession.selectList("BannerDao.selectBanner");
        DatabaseContextHolder.clearDatabaseType();
        return bannerList;
    }
}
