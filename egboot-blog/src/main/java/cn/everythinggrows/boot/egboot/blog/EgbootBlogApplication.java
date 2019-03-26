package cn.everythinggrows.boot.egboot.blog;

import cn.everythinggrows.boot.egboot.blog.config.MyBatisConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Import;


@Import({MyBatisConfig.class})
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class EgbootBlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(EgbootBlogApplication.class, args);
    }

}
