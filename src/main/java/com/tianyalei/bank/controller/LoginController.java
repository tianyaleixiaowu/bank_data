package com.tianyalei.bank.controller;

import com.tianyalei.bank.bean.BaseData;
import com.tianyalei.bank.manager.UserManager;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping
    public BaseData wechatLogin(String code, String state) {
         //https://open.weixin.qq.com/connect/qrconnect?appid=wxd23291934ad7625e&redirect_uri=http%3A%2F%2Fdp
        // .tianyalei.com/dp/login&response_type=code&scope=snsapi_login&state=STATE#wechat_redirect
        return null;
    }

    @PostMapping
    public BaseData login(String userName, String password) {
        return userManager.login(userName, password);
    }
}
