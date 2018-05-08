package com.tianyalei.bank.controller;

import com.tianyalei.bank.bean.BaseData;
import com.tianyalei.bank.bean.ResultGenerator;
import com.tianyalei.bank.bean.SimplePage;
import com.tianyalei.bank.dto.BillDto;
import com.tianyalei.bank.dto.SearchDto;
import com.tianyalei.bank.manager.BillManager;
import com.tianyalei.bank.vo.BillVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

    @GetMapping
    public BaseData find(SearchDto searchDto) {
        logger.info(searchDto.toString());
        SimplePage<BillVO> billSimplePage = billManager.find(searchDto);
        return ResultGenerator.genSuccessResult(billSimplePage.getList(), billSimplePage.getTotalCount());
    }

    @PostMapping
    public BaseData add(BillDto billDto) {
        logger.info(billDto.toString());
        //SimplePage<BillVO> billSimplePage = billManager.find(searchDto);
        return null;
    }
}
