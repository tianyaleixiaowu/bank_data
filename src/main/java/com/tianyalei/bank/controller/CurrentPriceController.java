package com.tianyalei.bank.controller;

import com.tianyalei.bank.bean.BaseData;
import com.tianyalei.bank.bean.ResultGenerator;
import com.tianyalei.bank.manager.CurrentPriceManager;
import com.tianyalei.bank.tuple.TupleTwo;
import com.tianyalei.bank.vo.CurrentPriceVO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 实时报价
 * @author wuweifeng wrote on 2018/5/15.
 */
@RestController
@RequestMapping("/current")
public class CurrentPriceController {

    @Resource
    private CurrentPriceManager currentPriceManager;

    /**
     * 获取某段时间价格趋势，不传则默认是今天
     * @param begin   begin
     * @param end   end
     */
    @RequestMapping
    public BaseData current(String begin, String end, int page, int size) {
        TupleTwo<List<CurrentPriceVO>, Integer> tupleTwo = currentPriceManager.find(begin, end, page, size);
        System.out.println(tupleTwo);
        return ResultGenerator.genSuccessResult(tupleTwo.first, tupleTwo.second);
    }

}
