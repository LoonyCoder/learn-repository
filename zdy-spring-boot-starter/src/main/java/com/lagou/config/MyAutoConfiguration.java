package com.lagou.config;


import com.lagou.pojo.SimpleBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass  //注解：当类路径classpath下存在指定类时，就会进行自动配置，如果不指定类，那么springboot一定会自动配置被该注解修饰的类
public class MyAutoConfiguration {

    static {
        System.out.println("MyAutoConfiguration init...");

    }

    @Bean
    public SimpleBean simpleBean(){
        return new SimpleBean();
    }
}
