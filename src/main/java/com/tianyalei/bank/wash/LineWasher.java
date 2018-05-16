package com.tianyalei.bank.wash;

import com.tianyalei.bank.bean.BankType;
import com.tianyalei.bank.manager.BillManager;
import com.tianyalei.bank.model.Bill;
import com.tianyalei.bank.tuple.TupleTwo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        logger.info("line的内容是：" + line);
        Bill bill = new Bill();
        //保存一下原文
        bill.setContent(line.replaceAll(" +", " ").replace("\r", ""));
        bill.setContactId(contactId);
        bill.setType(washType(line));
        TupleTwo<Byte, String> tupleTwo = washBank(line);
        bill.setBankType(tupleTwo.first);
        bill.setBank(tupleTwo.second);
        bill.setPrice(washPrice(line));
        bill.setBillPrice(billPrice(line));
        bill.setCreateTime(createTime);
        bill.setUpdateTime(createTime);
        bill.setCount(washCount(line));
        bill.setEndTime(washEndTime(line));
        byte young = 0;
        bill.setYoung(young);

        //设置该contact的其他数据的young为-1
        billManager.updateBillYoung(contactId);

        //保存该条数据
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
                if (price > 1000 && price < 2017 || price > 2021 && price < 10000) {
                    return price;
                }
            }
        }
        return 0;
    }

    private Date washEndTime(String line) {
        if (line.contains("号") || line.contains("月")) {
            String pa = "[0-9]{1,2}[.][0-9]{1,2}";
            Pattern pattern = Pattern.compile(pa);
            Matcher matcher = pattern.matcher(line);

            String dateStr = null;
            if (matcher.find()) {
                dateStr = matcher.group(0);
            }
            SimpleDateFormat myFmt = new SimpleDateFormat("yyyy.MM.dd");
            try {
                return myFmt.parse("2018." + dateStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private static Integer washCount(String line) {
        //String line = "一年期农商100*10 5150‼️";
        //String line1 = "一年中原100*4";
        if (line.contains("*") || line.contains("X") || line.contains("x")) {
            int index = line.indexOf("*");
            int index1 = line.indexOf("X");
            int index2 = line.indexOf("x");
            if (index == -1) {
                index = index1;
            }
            if (index == -1) {
                index = index2;
            }
            String result;
            if (line.length() > index + 2) {
                result = line.substring(index + 1, index + 3);
            } else {
                result = line.substring(index + 1, index + 2);
            }

            try {
                return Integer.valueOf(result.trim());
            } catch (Exception e) {
                return 1;
            }
        } else if (line.contains("张")) {
            int index = line.indexOf("张");
            String result;
            try {
                result = line.substring(index - 2, index);
            } catch (Exception e) {
                result = line.substring(index - 1, index);
            }
            if (result.contains("两")) {
                return 2;
            } else if (result.contains("多")) {
                return 10;
            } else {
                //找数字
                String regex = "\\d*";
                Pattern p = Pattern.compile(regex);

                Matcher m = p.matcher(result);
                while (m.find()) {
                    String s = m.group();
                    if (!"".equals(s)) {
                        return Integer.valueOf(s);
                    }
                }
                return 1;
            }
        } else {
            return 1;
        }
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
        } else if (line.contains("出") || !"其他".equals(washBank(line)) || line.contains("收")) {
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
     * 不足", "短期1，半年2，一年3，超期4
     * 不足", "短期，半年，一年，1年，超期，到期
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
    public static TupleTwo<Byte, String> washBank(String line) {
        for (String s : guo_banks) {
            if (line.contains(s)) {
                return new TupleTwo<>(BankType.GUO_GU, s);
            }
        }
        for (String s : gu_shang) {
            if (line.contains(s)) {
                return new TupleTwo<>(BankType.GU_SHANG, s);
            }
        }
        for (String s : san_nong_banks) {
            if (line.contains(s)) {
                return new TupleTwo<>(BankType.SAN_NONG, s);
            }
        }
        for (String s : da_cheng_banks) {
            if (line.contains(s)) {
                return new TupleTwo<>(BankType.DA_CHENG_SHANG, s);
            }
        }
        for (String s : city_banks) {
            if (line.contains(s)) {
                return new TupleTwo<>(BankType.CHENG_SHANG, s);
            }
        }
        for (String s : wai_zi) {
            if (line.contains(s)) {
                return new TupleTwo<>(BankType.WAI_ZI, s);
            }
        }
        for (String s : company_ticket) {
            if (line.contains(s)) {
                return new TupleTwo<>(BankType.COMPANY_TICKET, s);
            }
        }
        return new TupleTwo<>(BankType.QI_TA, "其他");
    }

    private static String[] guo_banks = {"中国人民银行", "中国进出口银行", "中国农业发展银行", "中国工商银行", "国家开发银行", "工行", "工商", "中国农业银行",
            "农行", "农业", "中国建设银行", "建行", "中国银行", "进出口银行", "中银", "国股", "中国", "中行"};
    private static String[] gu_shang = {"民生", "华夏银行", "华夏", "中国光大银行", "光大", "中信",
            "恒丰", "招商银行", "招商", "招行", "中国民生银行", "中信银行",
            "交通银行", "交通", "交行", "兴业银行", "兴业", "上海浦东发展银行",
            "浦发",
            "深圳发展银行", "深发", "广东发展银行", "广发",
            "股份"};
    private static String[] san_nong_banks = {"北京农商", "北京农村商业银行", "农商", "信用社", "中国邮政储蓄银行", "农信", "邮政", "邮储",
            "村镇"};
    //大城商
    private static String[] da_cheng_banks = {"北京银行", "北京", "上海", "南京银行", "南京", "宁波银行", "宁波", "恒丰银行", "恒丰", "浙商银行",
            "浙商",
            "渤海银行",
            "渤海", "莱商", "徽商", "齐商"};
    private static String[] company_ticket = {"富邦华一", "富邦", "美的", "海尔", "平安", "民泰", "中原"};
    private static String[] wai_zi = {"外资", "花旗银行", "汇丰银行", "渣打银行", "香港东亚银行", "香港南洋商业银行"};
    private static String[] city_banks = {"城商", "天津", "重庆", "新疆", "乌鲁木齐", "克拉玛依", "三峡",
            "石河子", "阿拉尔市", "图木舒克", "五家渠", "哈密", "吐鲁番", "阿克苏", "喀什", "和田", "伊宁", "塔城", "阿勒泰", "奎屯", "博乐", "昌吉", "阜康",
            "库尔勒", "阿图什", "乌苏"
            , "西藏", "拉萨", "日喀则", "宁夏", "银川", "石嘴山", "吴忠", "固原", "中卫", "青铜峡市", "灵武市", "呼和浩特", "包头", "乌海", "赤峰", "通辽",
            "鄂尔多斯", "呼伦贝尔", "巴彦淖尔",
            "乌兰察布", "霍林郭勒市", "满洲里市", "牙克石市", "扎兰屯市", "根河市", "额尔古纳市", "丰镇市", "锡林浩特市", "二连浩特市", "乌兰浩特市", "阿尔山市",
            "内蒙古", "广西", "南宁", "柳州", "桂林", "梧州", "北海", "崇左", "来宾", "贺州", "玉林", "百色", "河池", "钦州", "防城港", "贵港",
            "黑龙江", "哈尔滨", "大庆", "齐齐哈尔", "佳木斯", "鸡西", "鹤岗", "双鸭山", "牡丹江", "伊春", "七台河", "黑河", "绥化",
            "吉林", "长春", "吉林", "四平", "辽源", "通化", "白山", "松原", "白城",
            "辽宁", "沈阳", "大连", "鞍山", "抚顺", "本溪", "丹东", "锦州", "营口", "阜新", "辽阳", "盘锦", "铁岭", "朝阳", "葫芦岛",
            "河北", "石家庄", "唐山", "邯郸", "秦皇岛", "保定", "张家口", "承德", "廊坊", "沧州", "衡水", "邢台", "辛集市", "藁城市", "晋州市", "新乐市",
            "鹿泉市", "遵化市", "迁安市", "武安市", "南宫市", "沙河市", "涿州市", "定州市", "安国市", "高碑店市", "泊头市", "任丘市", "黄骅市", "河间市", "霸州市",
            "三河市", "冀州市", "深州市",
            "山东", "济南", "青岛", "淄博", "枣庄", "东营", "烟台", "潍坊", "济宁", "泰安", "威海", "日照", "莱芜", "临沂", "德州", "聊城", "菏泽", "滨州",
            "江苏", "南京", "镇江", "常州", "无锡", "苏州", "徐州", "连云港", "淮安", "盐城", "扬州", "泰州", "南通", "宿迁",
            "安徽", "合肥", "蚌埠", "芜湖", "淮南", "亳州", "阜阳", "淮北", "宿州", "滁州", "安庆", "巢湖", "马鞍山", "宣城", "黄山", "池州", "铜陵",
            "浙江", "杭州", "嘉兴", "湖州", "宁波", "金华", "温州", "丽水", "绍兴", "衢州", "舟山", "台州",
            "福建", "福州", "厦门", "泉州", "三明", "南平", "漳州", "莆田", "宁德", "龙岩",
            "广东", "广州", "深圳", "汕头", "惠州", "珠海", "揭阳", "佛山", "河源", "阳江", "茂名", "湛江", "梅州", "肇庆", "韶关", "潮州", "东莞",
            "中山", "清远", "江门", "汕尾", "云浮",
            "海南", "海口", "三亚",
            "云南", "昆明", "曲靖", "玉溪", "保山", "昭通", "丽江", "普洱", "临沧",
            "贵州", "贵阳", "六盘水", "遵义", "安顺",
            "四川", "成都", "绵阳", "德阳", "广元", "自贡", "攀枝花", "乐山", "南充", "内江", "遂宁", "广安", "泸州", "达州", "眉山", "宜宾", "雅安",
            "资阳",
            "湖南", "长沙", "株洲", "湘潭", "衡阳", "岳阳", "郴州", "永州", "邵阳", "怀化", "常德", "益阳", "张家界", "娄底",
            "湖北", "武汉", "襄樊", "宜昌", "黄石", "鄂州", "随州", "荆州", "荆门", "十堰", "孝感", "黄冈", "咸宁",
            "河南", "郑州", "洛阳", "开封", "漯河", "安阳", "新乡", "周口", "三门峡", "焦作", "平顶山", "信阳", "南阳", "鹤壁", "濮阳", "许昌", "商丘",
            "驻马店",
            "山西", "太原", "大同", "忻州", "阳泉", "长治", "晋城", "朔州", "晋中", "运城", "临汾", "吕梁",
            "陕西", "西安", "咸阳", "铜川", "延安", "宝鸡", "渭南", "汉中", "安康", "商洛", "榆林",
            "甘肃", "兰州", "天水", "平凉", "酒泉", "嘉峪关", "金昌", "白银", "武威", "张掖", "庆阳", "定西", "陇南",
            "青海", "西宁",
            "江西", "南昌", "九江", "赣州", "吉安", "鹰潭", "上饶", "萍乡", "景德镇", "新余", "宜春", "抚州",
            "台湾", "台北", "台中", "基隆", "高雄", "台南", "新竹", "嘉义"

    };
}
