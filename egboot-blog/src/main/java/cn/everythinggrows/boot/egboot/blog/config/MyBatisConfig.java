package cn.everythinggrows.boot.egboot.blog.config;

import cn.everythinggrows.boot.egboot.blog.datasource.DatabaseType;
import cn.everythinggrows.boot.egboot.blog.datasource.DynamicDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


@Configuration
public class MyBatisConfig {
    private static Logger log = LoggerFactory.getLogger(MyBatisConfig.class);
     @Autowired
     private Environment env;

        /**
          * 创建数据源(数据源的名称：方法名可以取为XXXDataSource(),XXX为数据库名称,该名称也就是数据源的名称)
         */
      @Bean
     public DataSource everythinggrow0DataSource() throws Exception {
                Properties props = new Properties();
                props.put("driverClassName", env.getProperty("jdbc0.driverClassName"));
                props.put("url", env.getProperty("jdbc0.url"));
                props.put("username", env.getProperty("jdbc0.username"));
                props.put("password", env.getProperty("jdbc0.password"));
                return DruidDataSourceFactory.createDataSource(props);
           }

      @Bean
      public DataSource everythinggrow1DataSource() throws Exception {
                Properties props = new Properties();
                props.put("driverClassName", env.getProperty("jdbc1.driverClassName"));
                props.put("url", env.getProperty("jdbc1.url"));
                props.put("username", env.getProperty("jdbc1.username"));
                props.put("password", env.getProperty("jdbc1.password"));
                return DruidDataSourceFactory.createDataSource(props);
            }

    @Bean
    public DataSource everythinggrow2DataSource() throws Exception {
        Properties props = new Properties();
        props.put("driverClassName", env.getProperty("jdbc2.driverClassName"));
        props.put("url", env.getProperty("jdbc2.url"));
        props.put("username", env.getProperty("jdbc2.username"));
        props.put("password", env.getProperty("jdbc2.password"));
        return DruidDataSourceFactory.createDataSource(props);
    }

    @Bean
    public DataSource everythinggrow3DataSource() throws Exception {
        Properties props = new Properties();
        props.put("driverClassName", env.getProperty("jdbc3.driverClassName"));
        props.put("url", env.getProperty("jdbc3.url"));
        props.put("username", env.getProperty("jdbc3.username"));
        props.put("password", env.getProperty("jdbc3.password"));
        return DruidDataSourceFactory.createDataSource(props);
    }

    @Bean
    public DataSource everythinggrow4DataSource() throws Exception {
        Properties props = new Properties();
        props.put("driverClassName", env.getProperty("jdbc4.driverClassName"));
        props.put("url", env.getProperty("jdbc4.url"));
        props.put("username", env.getProperty("jdbc4.username"));
        props.put("password", env.getProperty("jdbc4.password"));
        return DruidDataSourceFactory.createDataSource(props);
    }

    @Bean
    public DataSource everythinggrow5DataSource() throws Exception {
        Properties props = new Properties();
        props.put("driverClassName", env.getProperty("jdbc5.driverClassName"));
        props.put("url", env.getProperty("jdbc5.url"));
        props.put("username", env.getProperty("jdbc5.username"));
        props.put("password", env.getProperty("jdbc5.password"));
        return DruidDataSourceFactory.createDataSource(props);
    }

    @Bean
    public DataSource everythinggrow6DataSource() throws Exception {
        Properties props = new Properties();
        props.put("driverClassName", env.getProperty("jdbc6.driverClassName"));
        props.put("url", env.getProperty("jdbc6.url"));
        props.put("username", env.getProperty("jdbc6.username"));
        props.put("password", env.getProperty("jdbc6.password"));
        return DruidDataSourceFactory.createDataSource(props);
    }

    @Bean
    public DataSource everythinggrow7DataSource() throws Exception {
        Properties props = new Properties();
        props.put("driverClassName", env.getProperty("jdbc7.driverClassName"));
        props.put("url", env.getProperty("jdbc7.url"));
        props.put("username", env.getProperty("jdbc7.username"));
        props.put("password", env.getProperty("jdbc7.password"));
        return DruidDataSourceFactory.createDataSource(props);
    }



    /**
        * @Primary 该注解表示在同一个接口有多个实现类可以注入的时候，默认选择哪一个，而不是让@autowire注解报错
        * @Qualifier 根据名称进行注入，通常是在具有相同的多个类型的实例的一个注入（例如有多个DataSource类型的实例）
        */
      @Bean
     @Primary
//      public DynamicDataSource dataSource()  throws Exception {
//
//          Map<Object, Object> targetDataSources = new HashMap<>();
//          String jdbcNames=env.getProperty("jdbc.names");
//          for (String s:jdbcNames.split(",")) {
//              log.info("进行数据源配置：{}============================",s);
//              Properties props = new Properties();
//              props.put("driverClassName", env.getProperty("jdbc"+s+".driverClassName"));
//              props.put("url", env.getProperty("jdbc"+s+".url"));
//              props.put("username", env.getProperty("jdbc"+s+".username"));
//              props.put("password", env.getProperty("jdbc"+s+".password"));
//              targetDataSources.put(s,DruidDataSourceFactory.createDataSource(props) );
//          }
//          DynamicDataSource dataSource = new DynamicDataSource();
//          dataSource.setTargetDataSources(targetDataSources);
//          // 该方法是AbstractRoutingDataSource的方法
//          dataSource.setDefaultTargetDataSource(targetDataSources.get("1"));
//          // 默认的datasource设置为myTestDbDataSource
//          return  dataSource;
//      }

      public DynamicDataSource dataSource(@Qualifier("everythinggrow0DataSource") DataSource everythinggrow0DataSource,
                                          @Qualifier("everythinggrow1DataSource") DataSource everythinggrow1DataSource,
                                          @Qualifier("everythinggrow2DataSource") DataSource everythinggrow2DataSource,
                                          @Qualifier("everythinggrow3DataSource") DataSource everythinggrow3DataSource,
                                          @Qualifier("everythinggrow4DataSource") DataSource everythinggrow4DataSource,
                                          @Qualifier("everythinggrow5DataSource") DataSource everythinggrow5DataSource,
                                          @Qualifier("everythinggrow6DataSource") DataSource everythinggrow6DataSource,
                                          @Qualifier("everythinggrow7DataSource") DataSource everythinggrow7DataSource) {
                Map<Object, Object> targetDataSources = new HashMap<>();
                targetDataSources.put(DatabaseType.everythinggrows_0, everythinggrow0DataSource);
                targetDataSources.put(DatabaseType.everythinggrows_1, everythinggrow1DataSource);
                targetDataSources.put(DatabaseType.everythinggrows_2, everythinggrow2DataSource);
                targetDataSources.put(DatabaseType.everythinggrows_3, everythinggrow3DataSource);
                targetDataSources.put(DatabaseType.everythinggrows_4, everythinggrow4DataSource);
                targetDataSources.put(DatabaseType.everythinggrows_5, everythinggrow5DataSource);
                targetDataSources.put(DatabaseType.everythinggrows_6, everythinggrow6DataSource);
                targetDataSources.put(DatabaseType.everythinggrows_7, everythinggrow7DataSource);

                DynamicDataSource dataSource = new DynamicDataSource();
                dataSource.setTargetDataSources(targetDataSources);
                // 该方法是AbstractRoutingDataSource的方法
                dataSource.setDefaultTargetDataSource(everythinggrow0DataSource);
                // 默认的datasource设置为myTestDbDataSource
                return dataSource;
             }

      /**
       * 根据数据源创建SqlSessionFactory
       */
       @Bean
      public SqlSessionFactory sqlSessionFactory(DynamicDataSource ds) throws Exception {
                 SqlSessionFactoryBean fb = new SqlSessionFactoryBean();
                 fb.setDataSource(ds);
                 // 指定数据源(这个必须有，否则报错)
                 // 下边两句仅仅用于*.xml文件，如果整个持久层操作不需要使用到xml文件的话（只用注解就可以搞定），则不加
                 fb.setTypeAliasesPackage(env.getProperty("mybatis.typeAliasesPackage"));
                 // 指定基包
                 fb.setMapperLocations(
                 new PathMatchingResourcePatternResolver().getResources(env.getProperty("mybatis.mapperLocations")));
                 return fb.getObject();
             }

       /**
        * 配置事务管理器
        */
       @Bean
      public DataSourceTransactionManager transactionManager(DynamicDataSource dataSource) throws Exception {
                 return new DataSourceTransactionManager(dataSource);
             }

//    @Bean
//    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
//        return new SqlSessionTemplate(sqlSessionFactory);
//    }
}
