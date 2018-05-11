package com.tianyalei.bank.cache;

import com.tianyalei.bank.util.Common;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author wuweifeng wrote on 2018/4/17.
 */
@Component
public class UserCache {
    @Resource
    private Cache cache;

    public String saveToken(Long userId) {
        String token = Common.token();
        cache.put(token, userId);
        return token;
    }

    public Long findUserByToken(String token) {
        return cache.get(token, Long.class);
    }
}
