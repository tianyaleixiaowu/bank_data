package com.tianyalei.bank.bean;

/**
 * 国股、大商、城商、农商、信用社、村镇、外资、其他
 * @author wuweifeng wrote on 2018/5/4.
 */
public interface BankType {
    //国股银行，工商银行、农业银行、建设银行、中国银行；
    byte GUO_GU = 1;
    //民生银行，华夏银行，中国光大银行，中信实业银行，中国银行，中国建设银行;
    // (烟台1家)恒丰银行;(上海2家)上海浦东发展银行，交通银行;
    // (杭州1家)浙商银行;(福州1家)兴业银行;(深圳2家)深圳发展银行，招商银行;
    // (广州1家)广东发展银行。
    byte GU_SHANG = 2;
    //大型城商，北京银行、渤海、南京
    byte DA_CHENG_SHANG = 3;
    //小城市
    byte CHENG_SHANG = 4;
    //三农商
    byte SAN_NONG = 5;
    //美国花旗银行、英国汇丰银行、英国渣打银行、香港东亚银行、香港南洋商业银行
    byte WAI_ZI = 6;
    //公司财务票
    byte COMPANY_TICKET = 7;
    byte QI_TA = 0;
}
