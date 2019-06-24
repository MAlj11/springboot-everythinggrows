package cn.everythinggrows.boot.egboot.admin;

import cn.everythinggrows.boot.egboot.admin.config.MyBatisConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Import;

@Import({MyBatisConfig.class})
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class EgbootAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(EgbootAdminApplication.class, args);
    }

}
