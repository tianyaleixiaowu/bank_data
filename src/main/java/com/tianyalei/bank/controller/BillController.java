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
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;

/**
 * @author wuweifeng wrote on 2018/5/3.
 */
@RestController
@RequestMapping("")
public class BillController {
    @Resource
    private BillManager billManager;

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 删除
     */
    @PostMapping("/{id}")
    public BaseData delete(@PathVariable Long id) {
        logger.info("delete id " + id);
        billManager.delete(id);
        return ResultGenerator.genSuccessResult("delete");
    }

    @GetMapping
    public BaseData find(SearchDto searchDto) {
        logger.info(searchDto.toString());
        SimplePage<BillVO> billSimplePage = billManager.find(searchDto);
        return ResultGenerator.genSuccessResult(billSimplePage.getList(), billSimplePage.getTotalCount());
    }

    @PostMapping
    public BaseData add(BillDto billDto) throws ParseException {
        logger.info(billDto.toString());
        return ResultGenerator.genSuccessResult(billManager.save(billDto));
    }


}
