package cn.everythinggrows.boot.egboot.forum;

import cn.everythinggrows.boot.egboot.forum.config.MyBatisConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

@Import({MyBatisConfig.class})
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
//@ImportResource(value = {"classpath:dubbo-consumer.xml"})
public class EgbootForumApplication {

    public static void main(String[] args) {
        SpringApplication.run(EgbootForumApplication.class, args);
    }

}
