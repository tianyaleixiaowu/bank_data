package com.tianyalei.bank.controller;

import com.tianyalei.bank.bean.BaseData;
import com.tianyalei.bank.bean.ResultGenerator;
import com.tianyalei.bank.manager.UserManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author wuweifeng wrote on 2018/5/9.
 */
@Controller
@RequestMapping("/login")
public class LoginController {
    @Value("${AppID}")
    private String appId;
    @Resource
    private UserManager userManager;

    @ResponseBody
    @GetMapping("/wechat")
    public BaseData wechatLogin(String code, String state) {
        System.out.println(11111);
         //https://open.weixin.qq.com/connect/qrconnect?appid=wxd23291934ad7625e&redirect_uri=http%3A%2F%2Fdp
        // .tianyalei.com/dp/login&response_type=code&scope=snsapi_login&state=STATE#wechat_redirect

        //return "redirect:https://open.weixin.qq
        // .com/connect/qrconnect?appid=wxd23291934ad7625e&redirect_uri=https%3A%2F%2F" +
        //        "dp.tianyalei.com/dp/login&response_type=code&scope=snsapi_login&state=STATE#wechat_redirect";
        return ResultGenerator.genSuccessResult("https://open.weixin.qq.com/connect/qrconnect?" +
                "appid=" + appId +
                "&redirect_uri=https%3A%2F%2Fdp.tianyalei.com/dp/login/code" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=STATE#wechat_redirect");
    }

    @GetMapping("/code")
    public String wechatCode(String code, String state) {
        //如果没code，就是用户拒绝了授权
        if (code == null) {

        } else {

        }
        return "redirect:http://baidu.com";
    }


    @PostMapping
    public BaseData login(String userName, String password) {
        return userManager.login(userName, password);
    }
}
