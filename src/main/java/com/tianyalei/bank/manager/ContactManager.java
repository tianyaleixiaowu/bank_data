package com.tianyalei.bank.manager;

import com.tianyalei.bank.dao.ContactRepository;
import com.tianyalei.bank.model.Contact;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * @author wuweifeng wrote on 2018/5/2.
 */
@Service
public class ContactManager {
    @Resource
    private ContactRepository contactRepository;

    /**
     * 保存联系人
     */
    public Contact save(String nickName, String mobile, String company) {
        Contact contact = contactRepository.findFirstByMobileAndNickName(mobile, nickName);
        if (contact != null) {
            return contact;
        } else {
            contact = new Contact();
        }

        contact.setCompany(company);
        contact.setNickName(nickName);
        contact.setMobile(mobile);
        return contactRepository.save(contact);
    }

    public String findContact(Long id) {
        Contact contact = contactRepository.findById(id).get();
        if (!StringUtils.isEmpty(contact.getMobile())) {
            return contact.getMobile();
        } else {
            return contact.getNickName();
        }
    }
}
