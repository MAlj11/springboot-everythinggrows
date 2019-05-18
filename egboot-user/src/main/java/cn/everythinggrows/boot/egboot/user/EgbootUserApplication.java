package cn.everythinggrows.boot.egboot.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
//@ImportResource(value = {"classpath:dubbo-provider.xml"})
@EnableDiscoveryClient
public class EgbootUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(EgbootUserApplication.class, args);
    }

}
