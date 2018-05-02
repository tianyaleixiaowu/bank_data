package com.tianyalei.bank.dao;

import com.tianyalei.bank.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author wuweifeng wrote on 2018/5/2.
 */
public interface BillRepository extends JpaRepository<Bill, Long> {
}
