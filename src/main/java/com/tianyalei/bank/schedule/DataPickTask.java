package com.tianyalei.bank.schedule;

import com.tianyalei.bank.manager.DoneMsgIdManager;
import com.tianyalei.bank.manager.MessageManager;
import com.tianyalei.bank.model.DoneMsgId;
import com.tianyalei.bank.model.MessageEntity;
import com.tianyalei.bank.tuple.TupleTwo;
import com.tianyalei.bank.wash.ContactWasher;
import com.tianyalei.bank.wash.ContentWasher;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wuweifeng wrote on 2018/5/2.
 */
@Component
@EnableScheduling
public class DataPickTask {
    @Resource
    private DoneMsgIdManager doneMsgIdManager;
    @Resource
    private MessageManager messageManager;
    @Resource
    private ContactWasher contactWasher;
    @Resource
    private ContentWasher contentWasher;


    /**
     * 每5秒去message表读一次数据
     */
    @Scheduled(fixedRate = 5000)
    public void pickTask() {
        DoneMsgId doneMsgId = doneMsgIdManager.find();
        long begin = 0L, end = 0L;
        MessageEntity last = messageManager.findLastOne();
        if (last != null) {
            end = messageManager.findLastOne().getId();
        }
        //说明一个都没有处理，那就开始处理全部
        if (doneMsgId != null) {
            begin = doneMsgId.getEnd() + 1;
        }
        if (end < begin) {
            return;
        }
        List<MessageEntity> entities = messageManager.findByIdBetween(begin, end);
        for (MessageEntity messageEntity : entities) {
            try {
                TupleTwo<Long, String[]> tupleTwo = contactWasher.contactWash(messageEntity);
                contentWasher.contentWash(tupleTwo.first, tupleTwo.second, messageEntity.getCreateTime());
            } catch (Exception e) {
                //出现异常，continue即可
                e.printStackTrace();
            }
        }

        doneMsgIdManager.update(begin, end);
    }
}
