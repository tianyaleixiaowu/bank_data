package com.tianyalei.bank.manager;

import com.tianyalei.bank.dao.DoneMsgIdRepository;
import com.tianyalei.bank.model.DoneMsgId;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author wuweifeng wrote on 2018/5/2.
 */
@Service
public class DoneMsgIdManager {
    @Resource
    private DoneMsgIdRepository doneMsgIdRepository;

    public DoneMsgId find() {
        return doneMsgIdRepository.findFirstByOrderById();
    }

    public void update(Long begin, Long end) {
        DoneMsgId doneMsgId = find();
        if (doneMsgId == null) {
            doneMsgId = new DoneMsgId();
        }
        doneMsgId.setBegin(begin);
        doneMsgId.setEnd(end);
        doneMsgIdRepository.save(doneMsgId);
    }
}
