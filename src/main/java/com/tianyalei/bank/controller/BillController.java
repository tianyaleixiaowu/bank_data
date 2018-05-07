package com.tianyalei.bank.controller;

import com.tianyalei.bank.bean.BaseData;
import com.tianyalei.bank.bean.ResultGenerator;
import com.tianyalei.bank.bean.SimplePage;
import com.tianyalei.bank.manager.BillManager;
import com.tianyalei.bank.wash.BillVO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping
    public BaseData find(@PageableDefault(size = 15, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        SimplePage<BillVO> billSimplePage = billManager.find(pageable);
        return ResultGenerator.genSuccessResult(billSimplePage.getList(), billSimplePage.getTotalCount());
    }
}
