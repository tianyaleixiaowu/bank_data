package com.tianyalei.bank.manager;

import com.tianyalei.bank.bean.LoginData;
import com.tianyalei.bank.cache.UserCache;
import com.tianyalei.bank.dao.UserRepository;
import com.tianyalei.bank.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author wuweifeng wrote on 2018/4/16.
 */
@Service
public class UserManager {
    @Resource
    private UserRepository userRepository;
    @Resource
    private UserCache userCache;

    private Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * 登录
     */
    public LoginData login(String userName, String password) {
        User temp = userRepository.findByUserName(userName);
        LoginData loginData = new LoginData();

        if (temp == null) {
            loginData.setCode(-1);
            loginData.setMessage("用户不存在");
        } else if (!temp.getPassword().equals(password)) {
            loginData.setCode(-2);
            loginData.setMessage("密码错误");
        } else {
            //登录成功
            loginData.setCode(200);
            String token = userCache.saveToken(temp.getId());
            logger.info("token是" +  token);
            //保存token
            loginData.setToken(token);
        }
        return loginData;
    }


}
