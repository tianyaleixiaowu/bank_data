package com.tianyalei.bank.wash;

import com.tianyalei.bank.manager.ContactManager;
import com.tianyalei.bank.model.Contact;
import com.tianyalei.bank.model.MessageEntity;
import com.tianyalei.bank.tuple.TupleTwo;
import com.tianyalei.bank.util.Common;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * 洗出contact和String[]
 *
 * @author wuweifeng wrote on 2018/5/2.
 */
@Component
public class ContactWasher {
    @Resource
    private ContactManager contactManager;

    public TupleTwo<Long, String[]> contactWash(MessageEntity messageEntity) {
        String nickName = messageEntity.getNickName();
        String content = messageEntity.getContent();
        //判断nickName中有没有手机号
        String phone = Common.getPhone(nickName);
        //如果昵称没手机号，从content里找找手机号
        if (StringUtils.isEmpty(phone)) {
            phone = Common.getPhone(content);
        }
        Contact contact = contactManager.save(nickName, phone, nickName.replace(phone, ""));

        return new TupleTwo<>(contact.getId(), content.split("\n"));
    }
}
