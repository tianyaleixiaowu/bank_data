package com.tianyalei.bank.manager;

import com.tianyalei.bank.dao.ContactRepository;
import com.tianyalei.bank.model.Contact;
import org.springframework.stereotype.Service;

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
}
