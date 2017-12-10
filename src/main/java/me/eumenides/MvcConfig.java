package me.eumenides;

import me.eumenides.interceptor.UserInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by Eumenides on 2017/11/30.
 */
@Configuration
@ComponentScan(basePackages = "me.eumenides")
@PropertySource(value = "classpath:application.properties",
        ignoreResourceNotFound = true,encoding = "UTF-8")
public class MvcConfig extends WebMvcConfigurerAdapter {
    @Autowired
    UserInterceptor userInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册监控拦截器
        registry.addInterceptor(userInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/login","/js","/css");

    }
}
