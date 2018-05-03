package com.tianyalei.bank.wash;

import com.tianyalei.bank.manager.BillManager;
import com.tianyalei.bank.model.Bill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 对每一行清洗
 *
 * @author wuweifeng wrote on 2018/5/2.
 */
@Component
public class LineWasher {
    @Resource
    private BillManager billManager;
    private Logger logger = LoggerFactory.getLogger(getClass());

    public void lineWash(Long contactId, String line, Date createTime) {
        Bill bill = new Bill();
        //保存一下原文
        bill.setContent(line.replaceAll(" +", " "));
        bill.setContactId(contactId);
        bill.setType(washType(line));
        bill.setBank(washBank(line));
        bill.setPrice(washPrice(line));
        bill.setBillPrice(billPrice(line));
        bill.setCreateTime(createTime);
        bill.setUpdateTime(createTime);

        billManager.save(bill);
        logger.info(bill.toString());
    }

    /**
     * 销售价格
     */
    private Integer washPrice(String line) {
        //大于100小于10000的一个数字
        String regex = "\\d*";
        Pattern p = Pattern.compile(regex);

        Matcher m = p.matcher(line);
        while (m.find()) {
            String s = m.group();
            if (!"".equals(s)) {
                int price = Integer.valueOf(s);
                if (price > 100 && price < 2017 || price > 2021 && price < 10000) {
                    return price;
                }
            }
        }
        return 0;
    }

    /**
     * 票面价格
     *
     * @param line
     *         line
     * @return 价格
     */
    private Double billPrice(String line) {
        if (line.contains("万") || line.contains("w") || line.contains("*")) {
            //找到"万"前面的数字
            int index = line.toLowerCase().indexOf("万");
            int index1 = line.toLowerCase().indexOf("w");
            int index2 = line.toLowerCase().indexOf("*");
            if (index == -1 && index1 == -1 && index2 == -1) {
                return 0.0;
            }

            if (index == -1) {
                index = index1;
            }
            if (index == -1) {
                index = index2;
            }
            char[] chars = line.toCharArray();
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < index; i++) {
                char c = chars[i];
                if (Character.isDigit(c) || '.' == c) {
                    stringBuilder.append(chars[i]);
                } else {
                    stringBuilder.delete(0, stringBuilder.toString().length());
                }

            }
            if (stringBuilder.toString().length() > 0) {
                return Double.valueOf(stringBuilder.toString());
            }
        } else if (line.contains("出") && !"".equals(washBank(line))) {
            String bank = washBank(line);
            //大于1小于250的一个数字
            String regex = "\\d*";
            Pattern p = Pattern.compile(regex);

            Matcher m = p.matcher(line);
            while (m.find()) {
                String s = m.group();
                if (!"".equals(s)) {
                    double price = Double.valueOf(s);
                    if (price > 1 && price < 800) {
                        return price;
                    }
                }
            }
        }
        return 0.0;
    }

    /**
     * 不足、短期1，半年2，一年3，超期4
     * 不足、短期，半年，一年，1年，超期，到期
     */
    private int washType(String line) {
        if (line.contains("不足") || line.contains("短期")) {
            return 1;
        } else if (line.contains("半年")) {
            return 2;
        } else if (line.contains("一年") || line.contains("1年")) {
            return 3;
        } else if (line.contains("超期") || line.contains("到期")) {
            return 4;
        }
        return 0;
    }

    /**
     * 取银行名字
     */
    private String washBank(String line) {
        String[] banks = new String[]{"中国工商银行", "工行", "招商银行", "招商", "招行", "中国农业银行", "农行", "中国建设银行", "建行",
                "中国银行", "中国民生银行", "中国光大银行", "光大", "宁波银行", "莱商", "江苏", "中信银行", "中信", "浙商", "北京银行", "北京农村商业银行", "北京农商",
                "交通银行", "交通", "交行", "兴业银行", "兴业", "上海浦东发展银行", "海尔", "邯郸", "九江", "鞍山", "泰安", "宁波", "徽商", "湖州", "浦发",
                "中国人民银行", "恒丰", "中原", "中国",
                "农商", "村镇", "阳泉", "美的", "平安",
                "华夏银行", "华夏", "深圳发展银行", "广东发展银行", "广发", "国家开发银行", "城商", "民泰", "苏州", "台州", "郑州", "齐商",
                "中国邮政储蓄银行", "农信", "营口", "富邦华一", "中国进出口银行", "中国农业发展银行", "股份", "国股"};
        for (String s : banks) {
            if (line.contains(s)) {
                return s;
            }
        }
        return "";
    }
}
