package com.tianyalei.bank.schedule;

import com.tianyalei.bank.manager.DoneMsgIdManager;
import com.tianyalei.bank.manager.MessageManager;
import com.tianyalei.bank.model.DoneMsgId;
import com.tianyalei.bank.model.MessageEntity;
import com.tianyalei.bank.tuple.TupleTwo;
import com.tianyalei.bank.wash.ContactWasher;
import com.tianyalei.bank.wash.ContentWasher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 每5秒去message表读一次数据
     */
    @Scheduled(fixedRate = 5000)
    public void pickTask() {
        logger.info("开始读取message表");
        DoneMsgId doneMsgId = doneMsgIdManager.find();
        long begin = 0L, end = 0L;
        MessageEntity last = messageManager.findLastOne();
        if (last != null) {
            end = messageManager.findLastOne().getId();
            logger.info("最后一个的id是" + end);
        }
        //说明一个都没有处理，那就开始处理全部
        if (doneMsgId != null) {
            begin = doneMsgId.getEnd() + 1;
            logger.info("第一个的id是" + end);
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
