package com.tianyalei.bank.dao;

import com.tianyalei.bank.model.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author wuweifeng wrote on 2018/5/1.
 */
public interface MessageRepository extends JpaRepository<MessageEntity, Long> {
    /**
     * 查询id范围内的
     *
     * @param begin
     *         begin
     * @param end
     *         end
     * @return 集合
     */
    List<MessageEntity> findByIdBetween(Long begin, Long end);

    /**
     * 查询最新的一条
     *
     * @return MessageEntity
     */
    MessageEntity findFirstByOrderByIdDesc();
}
