package com.tianyalei.bank.controller;

import com.tianyalei.bank.bean.BaseData;
import com.tianyalei.bank.bean.ResultGenerator;
import com.tianyalei.bank.bean.SimplePage;
import com.tianyalei.bank.dto.BillDto;
import com.tianyalei.bank.manager.BillManager;
import com.tianyalei.bank.vo.BillVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author wuweifeng wrote on 2018/5/3.
 */
@RestController
@RequestMapping("")
public class BillController {
    @Resource
    private BillManager billManager;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping
    public BaseData find(BillDto billDto) {
        logger.info(billDto.toString());
        SimplePage<BillVO> billSimplePage = billManager.find(billDto);
        return ResultGenerator.genSuccessResult(billSimplePage.getList(), billSimplePage.getTotalCount());
    }
}
