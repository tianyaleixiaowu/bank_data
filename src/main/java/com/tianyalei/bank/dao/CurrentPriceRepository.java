package com.tianyalei.bank.dao;

import com.tianyalei.bank.model.CurrentPrice;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Date;
import java.util.List;

/**
 * @author wuweifeng wrote on 2018/5/2.
 */
public interface CurrentPriceRepository extends JpaRepository<CurrentPrice, Long>,
        JpaSpecificationExecutor<CurrentPrice> {
    /**
     * 查询一段时间内的数据数量
     * @param begin  begin
     * @param end   end
     * @return 数量
     */
    Long countByBuildTimeBetween(Date begin, Date end);

    /**
     * 查询一段范围内集合
     *
     * @param begin
     *         begin
     * @param end
     *         end
     * @param pageable   pageable
     * @return list
     */
    List<CurrentPrice> findByBuildTimeBetween(Date begin, Date end, Pageable pageable);

    /**
     * 删除某日数据
     * @param begin begin
     * @param end  end
     */
    void deleteByBuildTimeBetween(Date begin, Date end);
}
