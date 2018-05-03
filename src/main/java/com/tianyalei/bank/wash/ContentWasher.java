package com.tianyalei.bank.wash;

import com.tianyalei.bank.util.Common;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正文content清洗
 *
 * @author wuweifeng wrote on 2018/5/2.
 */
@Component
public class ContentWasher {
    @Resource
    private LineWasher lineWasher;

    /**
     * 干掉那些明显不行的行
     *
     * @param contactId
     *         contactId
     * @param lines
     *         lines
     */
    public void contentWash(Long contactId, String[] lines, Date createTime) {
        List<String> okLines = new ArrayList<>();
        for (String line : lines) {
            if (!containKey(line)) {
                continue;
            }
            if (hasPhone(line)) {
                continue;
            }
            if (!checkHasNumber(line)) {
                continue;
            }
            //if (hasEmoji(line)) {
            //    continue;
            //}
            okLines.add(line);
        }
        for (String line : okLines) {
            //每行的入库
            lineWasher.lineWash(contactId, line, createTime);
        }
    }

    /**
     * 判断是否有两个及以上数字
     *
     * @param line
     *         line
     * @return true or false
     */
    private boolean checkHasNumber(String line) {
        //截取数字
        String regex = "\\d+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            return true;
        }
        return false;
    }

    /**
     * 里面有手机号的行肯定不行
     *
     * @param line
     *         line
     * @return true or false
     */
    private boolean hasPhone(String line) {
        String phone = Common.getPhone(line);
        if (StringUtils.isEmpty(phone)) {
            return false;
        }
        return true;
    }

    private boolean hasEmoji(String line) {
        return line.contains("emoji");
    }

    /**
     * 不足、短期，半年，一年，1年，超期，到期
     *
     * @param line
     *         line
     * @return boolean
     */
    private boolean containKey(String line) {
        return line.contains("不足") || line.contains("短期") || line.contains("半年")
                || line.contains("一年") || line.contains("1年") || line.contains("超期")
                || line.contains("到期");
    }
}
