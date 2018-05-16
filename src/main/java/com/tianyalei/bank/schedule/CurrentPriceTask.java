package com.tianyalei.bank.schedule;

import com.tianyalei.bank.manager.CurrentPriceManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author wuweifeng wrote on 2018/5/15.
 */
@Component
public class CurrentPriceTask {
    @Resource
    private CurrentPriceManager currentPriceManager;

    @Scheduled(cron = "0 0/20 23 * * ?")
    public void doCount() {
        //每天晚上去执行一次
        currentPriceManager.find(null, null, 1, 10);
    }

    /**
     * 隔5分钟清理今天的数据，直接删掉
     */
    @Scheduled(cron = "0 0/10 1-23 * * ?")
    public void pickTask() {
        currentPriceManager.deleteTodayData();
    }
}
