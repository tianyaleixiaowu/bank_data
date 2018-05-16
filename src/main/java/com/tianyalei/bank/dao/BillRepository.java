package com.tianyalei.bank.dao;

import com.tianyalei.bank.model.Bill;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author wuweifeng wrote on 2018/5/2.
 */
public interface BillRepository extends JpaRepository<Bill, Long>, JpaSpecificationExecutor<Bill> {
    /**
     * 设置young为-1
     */
    @Transactional
    @Modifying
    @Query("update Bill set young = -1 where young <> -1 and contactId = ?1")
    void updateYoungBill(Long contactId);

    List<Bill> findByCreateTimeBetweenAndPriceBetween(Date begin, Date end, int min, int max, Pageable pageable);
}
