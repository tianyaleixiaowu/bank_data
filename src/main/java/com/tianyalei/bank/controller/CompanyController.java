package com.tianyalei.bank.controller;

import com.tianyalei.bank.bean.BaseData;
import com.tianyalei.bank.bean.ResultGenerator;
import com.tianyalei.bank.manager.CompanyManager;
import com.tianyalei.bank.model.Company;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wuweifeng wrote on 2018/5/16.
 */
@RestController
@RequestMapping("company")
public class CompanyController {
    @Resource
    private CompanyManager companyManager;

    /**
     * 模糊查询公司，根据公司名
     */
    @GetMapping
    public BaseData find(String companyName) {
        List<Company> companies = companyManager.findByName(companyName);
        return ResultGenerator.genSuccessResult(companies);
    }

    @PostMapping
    public BaseData add(Company company) {
        return ResultGenerator.genSuccessResult(companyManager.add(company));
    }

}
