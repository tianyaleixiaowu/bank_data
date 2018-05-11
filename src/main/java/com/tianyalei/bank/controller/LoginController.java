package com.tianyalei.bank.controller;

import com.tianyalei.bank.bean.BaseData;
import com.tianyalei.bank.manager.UserManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author wuweifeng wrote on 2018/5/9.
 */
@RestController
@RequestMapping("/login")
public class LoginController {
    @Resource
    private UserManager userManager;

    @PostMapping
    public BaseData login(String userName, String password) {
        return userManager.login(userName, password);
    }
}
