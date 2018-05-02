package com.tianyalei.bank.dao;

import com.tianyalei.bank.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author wuweifeng wrote on 2018/5/2.
 */
public interface ContactRepository extends JpaRepository<Contact, Long> {
    Contact findFirstByMobileAndNickName(String mobile, String nickName);
}
