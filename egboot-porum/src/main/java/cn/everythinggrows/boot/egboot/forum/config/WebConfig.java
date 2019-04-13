package cn.everythinggrows.boot.egboot.forum.config;

import cn.everythinggrows.boot.egboot.forum.aop.SessionValidateFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class WebConfig {
    private static Logger log = LoggerFactory.getLogger(WebConfig.class);

    /**
     * 配置过滤器
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {

        log.info("进入webconfig----------------------------------------------------------------");
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();

        SessionValidateFilter filter = new SessionValidateFilter();
        registrationBean.setFilter(filter);

        //设置过滤器拦截请求
        List<String> urls = new ArrayList<>();
        urls.add("/*");
        registrationBean.setUrlPatterns(urls);

        return registrationBean;
    }
}
