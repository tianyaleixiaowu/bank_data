package com.tianyalei.bank.dao;

import com.tianyalei.bank.model.DoneMsgId;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author wuweifeng wrote on 2018/5/2.
 */
public interface DoneMsgIdRepository extends JpaRepository<DoneMsgId, Long> {
    /**
     * 找第一个
     * @return DoneMsgId
     */
    DoneMsgId findFirstByOrderById();
}
