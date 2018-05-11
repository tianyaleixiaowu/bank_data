package com.tianyalei.bank.dao;

import com.tianyalei.bank.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author wuweifeng wrote on 2018/3/7.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserName(String account);
}
