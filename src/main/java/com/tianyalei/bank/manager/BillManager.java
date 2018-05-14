package com.tianyalei.bank.manager;

import com.tianyalei.bank.bean.SimplePage;
import com.tianyalei.bank.dao.BillRepository;
import com.tianyalei.bank.dto.BillDto;
import com.tianyalei.bank.dto.BillMoDto;
import com.tianyalei.bank.dto.SearchDto;
import com.tianyalei.bank.model.Bill;
import com.tianyalei.bank.model.Contact;
import com.tianyalei.bank.util.specify.Criteria;
import com.tianyalei.bank.util.specify.Restrictions;
import com.tianyalei.bank.vo.BillVO;
import com.tianyalei.bank.wash.ContentWasher;
import com.tianyalei.bank.wash.LineWasher;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
    @Resource
    private ContentWasher contentWasher;

    public void save(Bill bill) {
        billRepository.save(bill);
    }

    public void saveMohu(BillMoDto billDto) {
        Contact contact = contactManager.save(billDto.getNickName(), billDto.getMobile(), billDto.getCompany());
        contentWasher.contentWash(contact.getId(), billDto.getContent().split("\n"), new Date());
    }

    public void updateBillYoung(Long contactId) {
        billRepository.updateYoungBill(contactId);
    }

    public static void main(String[] args) {
        String s = "半年期：\n" +
                "2.9046  光大银行*1张   1980\n" +
                "5万       齐鲁银行*2张    2080\n" +
                "50万     农商银行*多张   2230\n" +
                "120万  浙商银行*1张     2230\n" +
                "注：半年农商50，三张以上➕20";
        String[] ss = s.split("\n");
        System.out.println(s.split("\n"));
    }

    public Bill save(BillDto billDto) throws ParseException {
        Contact contact = contactManager.save(billDto.getNickName(), billDto.getMobile(), billDto.getCompany());
        Bill bill;
        if (billDto.getId() != null) {
            bill = billRepository.findById(billDto.getId()).get();
        } else {
            bill = new Bill();
        }
        BeanUtils.copyProperties(billDto, bill);
        if (!StringUtils.isEmpty(billDto.getEndTime())) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = formatter.parse(billDto.getEndTime());
            bill.setEndTime(date);
        }

        byte bankType = LineWasher.washBank(billDto.getBank()).first;
        bill.setBankType(bankType);
        bill.setContactId(contact.getId());
        return billRepository.save(bill);
    }

    public void delete(Long id) {
        billRepository.deleteById(id);
    }

    /**
     * 条件查询
     *
     * @param searchDto
     *         searchDto
     * @return 分页
     */
    public SimplePage<BillVO> find(SearchDto searchDto) {
        Criteria<Bill> criteria = new Criteria<>();
        //只取某个人发的最新的
        criteria.add(Restrictions.eq("young", 0, true));
        //半年期
        if (searchDto.getType() != null && -1 != searchDto.getType()) {
            criteria.add(Restrictions.eq("type", searchDto.getType(), true));
        }
        //银行类型
        if (searchDto.getBankType() != null && -1 != searchDto.getBankType()) {
            //4是查城商，包含大城和小城
            if (4 == searchDto.getBankType()) {
                criteria.add(Restrictions.in("bankType", Arrays.asList(3, 4), true));
            } else {
                criteria.add(Restrictions.eq("bankType", searchDto.getBankType(), true));
            }
        }
        Integer billPrice = searchDto.getBillPrice();
        if (billPrice != null && -1 != billPrice) {
            if (billPrice == 1) {
                criteria.add(Restrictions.gte("billPrice", 0, true));
                criteria.add(Restrictions.lt("billPrice", 10, true));
            } else if (billPrice == 2) {
                criteria.add(Restrictions.gte("billPrice", 10, true));
                criteria.add(Restrictions.lt("billPrice", 30, true));
            } else if (billPrice == 3) {
                criteria.add(Restrictions.gte("billPrice", 30, true));
                criteria.add(Restrictions.lt("billPrice", 50, true));
            } else if (billPrice == 4) {
                criteria.add(Restrictions.gte("billPrice", 50, true));
                criteria.add(Restrictions.lt("billPrice", 100, true));
            } else if (billPrice == 5) {
                criteria.add(Restrictions.gte("billPrice", 100, true));
                criteria.add(Restrictions.lt("billPrice", 500, true));
            } else if (billPrice == 6) {
                criteria.add(Restrictions.gte("billPrice", 500, true));
            }
        }
        String keywords = searchDto.getKeywords();
        if (!StringUtils.isEmpty(keywords)) {
            criteria.add(Restrictions.like("content", keywords, true));
        }

        Pageable pageable = PageRequest.of(searchDto.getPage() - 1, searchDto.getSize(), Sort.Direction.DESC, "id");
        Page<Bill> billPage = billRepository.findAll(criteria, pageable);

        List<BillVO> billVOS = new ArrayList<>();
        
        for (Bill bill : billPage.getContent()) {
            BillVO vo = new BillVO();
            BeanUtils.copyProperties(bill, vo);
            vo.setEndTime(bill.getEndTime() == null ? "未知" : bill.getEndTime().toString());
            vo.setType(type(bill.getType()));
            Contact contact = contact(bill.getContactId());
            vo.setCompany(contact.getCompany());
            vo.setNickName(contact.getNickName());
            vo.setMobile(contact.getMobile());
            vo.setCreateTime(bill.getCreateTime().toString());
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

    private Contact contact(Long contactId) {
        return contactManager.findContact(contactId);
    }

}
