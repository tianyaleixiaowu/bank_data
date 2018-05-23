package com.tianyalei.bank.dao;

import com.tianyalei.bank.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author wuweifeng wrote on 2018/5/2.
 */
public interface CompanyRepository extends JpaRepository<Company, Long> {
    List<Company> findByNameLike(String name);

    List<Company> findByName(String name);
}
