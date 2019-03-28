package cn.everythinggrows.boot.egboot.forum;

import cn.everythinggrows.boot.egboot.forum.config.MyBatisConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Import;

@Import({MyBatisConfig.class})
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class EgbootForumApplication {

    public static void main(String[] args) {
        SpringApplication.run(EgbootForumApplication.class, args);
    }

}
