package com.lagou.config;

import com.lagou.service.MyService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration   //标明该类是一个配置类
public class MyConfig {

    private MyService myService;

    @Bean(name="iService")  //将返回值对象作为组件添加到spring容器中  name:指定id标识，如果没有指定，默认是方法名。
    public MyService getMyService(){
        return new MyService();
    }
}
