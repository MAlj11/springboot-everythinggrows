//package cn.everythinggrows.boot.egboot.blog.config;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
//
//import javax.servlet.Filter;
//
//public class WebConfig{
//    private static Logger log = LoggerFactory.getLogger(WebConfig.class);
//
//    /**
//     * 配置过滤器
//     * @return
//     */
//    @Bean
//    public FilterRegistrationBean someFilterRegistration() {
//        log.info("进入到webConfig的过滤器配置--------------------------------------------------------");
//        FilterRegistrationBean registration = new FilterRegistrationBean();
//        registration.setFilter(SessionValidateFilter());
//        registration.addUrlPatterns("/*");
//        registration.addInitParameter("paramName", "paramValue");
//        registration.setName("SessionValidateFilter");
//        return registration;
//    }
//
//    /**
//     * 创建一个bean
//     * @return
//     */
//    @Bean(name = "SessionValidateFilter")
//    public Filter SessionValidateFilter() {
//        return new SessionValidateFilter();
//    }
//
//}
