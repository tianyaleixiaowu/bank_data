package com.tianyalei.bank.manager;

import com.tianyalei.bank.dao.CompanyRepository;
import com.tianyalei.bank.model.Company;
import com.xiaoleilu.hutool.util.CollectionUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wuweifeng wrote on 2018/5/16.
 */
@Service
public class CompanyManager {
    @Resource
    private CompanyRepository companyRepository;

    /**
     * 根据公司名模糊查询
     *
     * @param name
     *         公司名
     * @return 公司集合
     */
    public List<Company> findByName(String name) {
        return companyRepository.findByNameLike("%" + name + "%");
    }

    public Company add(Company company) {
        List<Company> companyList = companyRepository.findByName(company.getName());
        if (CollectionUtil.isNotEmpty(companyList)) {
            return companyList.get(0);
        } else {
            return companyRepository.save(company);
        }

    }
}
