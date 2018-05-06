package com.tianyalei.bank.manager;

import com.tianyalei.bank.bean.SimplePage;
import com.tianyalei.bank.dao.BillRepository;
import com.tianyalei.bank.model.Bill;
import com.tianyalei.bank.util.specify.Criteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author wuweifeng wrote on 2018/5/2.
 */
@Service
public class BillManager {
    @Resource
    private BillRepository billRepository;

    public void save(Bill bill) {
        billRepository.save(bill);
    }

    public SimplePage<Bill> find() {
        Criteria<Bill> criteria = new Criteria<>();
        //开始时间
        //Date begin = CommonUtil.dateOfStr(infoQuery.getBegin());
        //criteria.add(Restrictions.gt("createTime", begin, true));
        //Date end = CommonUtil.dateOfStr(infoQuery.getEnd());
        //criteria.add(Restrictions.lt("createTime", end, true));
        //if (channels != null && channels.size() > 0) {
        //    LogicalExpression s2 = Restrictions.in("channel", channels, true);
        //    criteria.add(s2);
        //} else {
        //    criteria.add(Restrictions.ne("channel", "test", true));
        //}

        int page = 0;
        //if (infoQuery.getPage() != null) {
        //    page = infoQuery.getPage();
        //}
        int size = 10;
        //if (infoQuery.getSize() != null) {
        //    size = infoQuery.getSize();
        //}
        Sort.Direction direction = Sort.Direction.DESC;
        Sort sort = new Sort(direction, "id");
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Bill> billPage = billRepository.findAll(criteria, pageable);
        SimplePage<Bill> simplePage = new SimplePage<>(billPage.getTotalPages(), billPage.getTotalElements(),
                billPage.getContent());

        return simplePage;
    }
}
