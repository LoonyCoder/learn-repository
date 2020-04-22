package com.lagou.demo.controller;

import com.lagou.demo.service.IDemoService;
import com.lagou.edu.mvcframework.annotations.LgAutowired;
import com.lagou.edu.mvcframework.annotations.LgController;
import com.lagou.edu.mvcframework.annotations.LgRequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@LgController
@LgRequestMapping("/demo")
public class DemoController {

    @LgAutowired
    private IDemoService demoService;


    /**
     * url:/demo/query
     * @param request
     * @param response
     * @param name
     * @return
     */
    @LgRequestMapping("/query")
    public String query(HttpServletRequest request, HttpServletResponse response,String name){
        return  demoService.get(name);
    }

}
