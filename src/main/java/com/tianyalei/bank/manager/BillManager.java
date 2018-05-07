package com.tianyalei.bank.manager;

import com.tianyalei.bank.bean.SimplePage;
import com.tianyalei.bank.dao.BillRepository;
import com.tianyalei.bank.model.Bill;
import com.tianyalei.bank.util.specify.Criteria;
import com.tianyalei.bank.wash.BillVO;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wuweifeng wrote on 2018/5/2.
 */
@Service
public class BillManager {
    @Resource
    private BillRepository billRepository;
    @Resource
    private ContactManager contactManager;

    public void save(Bill bill) {
        billRepository.save(bill);
    }

    public SimplePage<BillVO> find(Pageable pageable) {
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

        Page<Bill> billPage = billRepository.findAll(criteria, pageable);

        List<BillVO> billVOS = new ArrayList<>();
        for (Bill bill : billPage.getContent()) {
            BillVO vo = new BillVO();
            BeanUtils.copyProperties(bill, vo);
            vo.setEndTime(bill.getEndTime() == null ? "未知" : bill.getEndTime().toString());
            vo.setType(type(bill.getType()));
            vo.setContact(contact(bill.getContactId()));
            billVOS.add(vo);
        }

        SimplePage<BillVO> simplePage = new SimplePage<>(billPage.getTotalPages(), billPage.getTotalElements(),
                billVOS);

        return simplePage;
    }

    private String type(int type) {
        //不足、短期1，半年2，一年3，超期4
        if (type == 1) {
            return "短期";
        } else if (type == 2) {
            return "半年";
        } else if (type == 3) {
            return "一年";
        } else if (type == 4) {
            return "超期";
        } else {
            return "其他";
        }
    }

    private String contact(Long contactId) {
        return contactManager.findContact(contactId);
    }
}
