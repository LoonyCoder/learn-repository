package com.lagou.edu.controller;

import com.lagou.edu.pojo.Account;
import com.lagou.edu.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/account")
public class AccountController {

    /**
     * spring容器和springmvc容器是有层次的（父子容器）
     * spring容器：管理serive对象和dao对象
     * springmvc容器：管理controller对象，可以引用到spring容器中的对象
     */

    @Autowired
    private AccountService accountService;


    @RequestMapping("/queryAll")
    @ResponseBody
    public List<Account> queryAll() throws Exception {
       return accountService.queryAccountList();
    }
}
