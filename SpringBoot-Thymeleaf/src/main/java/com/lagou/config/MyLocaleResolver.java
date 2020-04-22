package com.lagou.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;


//自定义区域解析器
@Configuration
public class MyLocaleResolver implements LocaleResolver {


    //自定义区域解析方式
    @Override
    public Locale resolveLocale(HttpServletRequest httpServletRequest) {
//        获取页面手动传递的语言参数值：l
//                1.zh_CN
//                2.en_US
//                3.空字符
        String l = httpServletRequest.getParameter("l");
        Locale locale = null;
        if(!StringUtils.isEmpty(l)){
            //如果参数不为空，就根据参数值进行手动语言切换
            String[] s = l.split("_");
            locale = new Locale(s[0],s[1]);
        }else{
            //从请求头中获取参数Accept-Language：zh-CN,zh;q=0.9
            String header = httpServletRequest.getHeader("Accept-Language");
            //第一次按照,切割获取到zh-CN
            String[] split = header.split(",");
            //第二次切割按照-获得  [zh,CN]
            String[] split1 = split[0].split("-");
            locale = new Locale(split1[0],split1[1]);
        }
        return locale;
    }

    @Override
    public void setLocale(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Locale locale) {

    }


    //将自定义的MyLocaleResolver重新注册成一个类型为LocaleResolver的bean组件
    @Bean
    public LocaleResolver localeResolver(){
        return new MyLocaleResolver();
    }
}
