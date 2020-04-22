package com.lagou.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController   //= @ResponseBody + @Controller
public class HelloController {

    @RequestMapping("/demo")
    public String demo(){
        return "hello,Spring Boot";
    }
}
