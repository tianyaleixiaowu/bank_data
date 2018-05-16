package com.tianyalei.bank.manager;

import com.tianyalei.bank.bean.PageData;
import com.tianyalei.bank.dao.CurrentPriceRepository;
import com.tianyalei.bank.model.CurrentPrice;
import com.tianyalei.bank.tuple.TupleTwo;
import com.tianyalei.bank.util.Common;
import com.tianyalei.bank.vo.CurrentPriceVO;
import com.xiaoleilu.hutool.date.DateUnit;
import com.xiaoleilu.hutool.date.DateUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author wuweifeng wrote on 2018/5/15.
 */
@Service
public class CurrentPriceManager {
    @Resource
    private CurrentPriceRepository currentPriceRepository;
    @Resource
    private BillManager billManager;

    public static int oneDayCount = 12;

    /**
     * 删今天的数据
     */
    public void deleteTodayData() {
        Date beginTime = DateUtil.beginOfDay(new Date());
        Date endTime = DateUtil.endOfDay(new Date());
        currentPriceRepository.deleteByBuildTimeBetween(beginTime, endTime);
    }

    /**
     * 查询某段时间内的统计数据
     */
    public TupleTwo<List<CurrentPriceVO>, Integer> find(String begin, String end, Integer page, Integer size) {
        //网页page从1开始传的
        page = page - 1;

        PageData pageData = new PageData();
        pageData.setCode(0);

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date endTime = null, beginTime = null;
        if (StringUtils.isEmpty(end)) {
            endTime = Common.endOfDay(dateFormat.format(new Date()));
        } else {
            endTime = Common.endOfDay(end);
        }
        if (StringUtils.isEmpty(begin)) {
            beginTime = Common.beginOfDay(dateFormat.format(new Date()));
        } else {
            beginTime = Common.beginOfDay(begin);
        }
        //查看间隔多少天
        long betweenDay = DateUtil.between(beginTime, endTime, DateUnit.DAY);

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "buildTime");

        //获取表中该时间段的统计数据集合
        Long count = currentPriceRepository.countByBuildTimeBetween(beginTime, endTime);
        //间隔数量应该=数据库数量-1，譬如开始和结束都是今天，数据会有一条数据
        //1天是有3*4共12条统计数据
        if (count == (betweenDay + 1) * oneDayCount) {
            //数量正确，说明每天都有值，那么就返回数据库数据
            List<CurrentPrice> list = currentPriceRepository.findByBuildTimeBetween
                    (beginTime, endTime, pageable);

            return new TupleTwo<>(parse(list), count.intValue());
        }
        //如果是强制获取或者是数量对不上，就要去看哪天缺失，并且补上
        for (; beginTime.before(endTime); beginTime = DateUtil.offsetDay(beginTime, 1)) {
            Date oneDayEnd = DateUtil.endOfDay(beginTime);
            //每一天的数量
            Long tempCount = currentPriceRepository.countByBuildTimeBetween(beginTime, oneDayEnd);
            if (tempCount == 0) {
                //处理一天的统计值
                billManager.countOneDayPrice(beginTime, oneDayEnd);
            }
        }

        List<CurrentPrice> currentPrices = currentPriceRepository.findByBuildTimeBetween
                (beginTime, endTime, pageable);
        return new TupleTwo<>(parse(currentPrices), currentPrices.size());
    }

    private List<CurrentPriceVO> parse(List<CurrentPrice> list) {
        List<CurrentPriceVO> vos = new ArrayList<>(list.size());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (CurrentPrice currentPrice : list) {
            CurrentPriceVO vo = new CurrentPriceVO();
            BeanUtils.copyProperties(currentPrice, vo);
            vo.setBuildTime(dateFormat.format(currentPrice.getBuildTime()));
            vos.add(vo);
        }
        return vos;
    }

    public CurrentPrice save(byte bankType, int price, int billPrice, Date buildTime) {
        CurrentPrice currentPrice = new CurrentPrice();
        currentPrice.setBankType(bankType);
        currentPrice.setPrice(price);
        currentPrice.setBillPrice(billPrice);
        currentPrice.setBuildTime(buildTime);
        return currentPriceRepository.save(currentPrice);
    }

}
