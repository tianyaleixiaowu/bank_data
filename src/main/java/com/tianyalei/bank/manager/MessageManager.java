package com.tianyalei.bank.manager;

import com.tianyalei.bank.dao.MessageRepository;
import com.tianyalei.bank.model.MessageEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wuweifeng wrote on 2018/5/1.
 */
@Service
public class MessageManager {
    @Resource
    private MessageRepository messageRepository;

    public List<MessageEntity> findByIdBetween(Long beginId, Long endId) {
        return messageRepository.findByIdBetween(beginId, endId);
    }

    public MessageEntity findLastOne() {
        return messageRepository.findFirstByOrderByIdDesc();
    }
}
