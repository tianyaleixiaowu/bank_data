package com.tianyalei.bank.manager;

import com.tianyalei.bank.dao.BillRepository;
import com.tianyalei.bank.model.Bill;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author wuweifeng wrote on 2018/5/2.
 */
@Service
public class BillManager {
    @Resource
    private BillRepository billRepository;

    public void save(Bill bill) {
        billRepository.save(bill);
    }
}
