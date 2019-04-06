package cn.everythinggrows.boot.egboot.portal.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;


@Configuration
public class DefaultView extends WebMvcConfigurationSupport {

    @Override
    protected void addViewControllers(ViewControllerRegistry registry) {
        // login页面在 templates 文件夹下
        registry.addViewController("/").setViewName("sindex");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
        super.addViewControllers(registry);

    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/images/**").addResourceLocations("classpath:/static/images/");
        registry.addResourceHandler("/type/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/type").addResourceLocations("classpath:/");
        registry.addResourceHandler("/index/article/detail/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/blog/article/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/article/static/**").addResourceLocations("classpath:/static/");
    }

}
