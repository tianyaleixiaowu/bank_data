package com.tianyalei.bank.util;

import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wuweifeng wrote on 2018/5/2.
 */
public class Common {
    /**
     * 生成uuid
     */
    public static String token() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 只接受这样的格式2017-09-3，不能带时分秒
     *
     * @param date
     *         年月日
     * @return 该天的开始
     */
    public static Date beginOfDay(String date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return dateFormat.parse(date + " 00:00:00");
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 只接受这样的格式2017-09-3，不能带时分秒
     *
     * @param date
     *         年月日
     * @return 该天的结束
     */
    public static Date endOfDay(String date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return dateFormat.parse(date + " 23:59:59");
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getPhone(String content) {
        String regex = "1[35789]\\d{9}";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(content);
        StringBuilder phone = new StringBuilder();
        //一定需要先查找再调用group获取电话号码
        while (m.find()) {
            phone.append(m.group()).append(",");
        }
        String s = phone.toString();
        if (!StringUtils.isEmpty(s)) {
            return s.substring(0, s.length() - 1);
        }
        return phone.toString();
    }

    /**
     * 不足、短期1，半年2，一年3，超期4
     */
    public static int type(String line) {
        if (line.contains("短期") || line.contains("不足")) {
            return 1;
        } else if (line.contains("半年")) {
            return 2;
        } else if (line.contains("一年") || line.contains("1年")) {
            return 3;
        } else if (line.contains("超期") || line.contains("超")) {
            return 4;
        }
        return 0;
    }

    public static boolean containsEmoji(String source) {
        int len = source.length();
        boolean isEmoji = false;
        for (int i = 0; i < len; i++) {
            char hs = source.charAt(i);
            if (0xd800 <= hs && hs <= 0xdbff) {
                if (source.length() > 1) {
                    char ls = source.charAt(i + 1);
                    int uc = ((hs - 0xd800) * 0x400) + (ls - 0xdc00) + 0x10000;
                    if (0x1d000 <= uc && uc <= 0x1f77f) {
                        return true;
                    }
                }
            } else {
                // non surrogate
                if (0x2100 <= hs && hs <= 0x27ff && hs != 0x263b) {
                    return true;
                } else if (0x2B05 <= hs && hs <= 0x2b07) {
                    return true;
                } else if (0x2934 <= hs && hs <= 0x2935) {
                    return true;
                } else if (0x3297 <= hs && hs <= 0x3299) {
                    return true;
                } else if (hs == 0xa9 || hs == 0xae || hs == 0x303d
                        || hs == 0x3030 || hs == 0x2b55 || hs == 0x2b1c
                        || hs == 0x2b1b || hs == 0x2b50 || hs == 0x231a) {
                    return true;
                }
                if (!isEmoji && source.length() > 1 && i < source.length() - 1) {
                    char ls = source.charAt(i + 1);
                    if (ls == 0x20e3) {
                        return true;
                    }
                }
            }
        }
        return isEmoji;
    }

    private static boolean isEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA)
                || (codePoint == 0xD)
                || ((codePoint >= 0x20) && (codePoint <= 0xD7FF))
                || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD))
                || ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
    }

    /**
     * 过滤emoji 或者 其他非文字类型的字符
     *
     * @param source
     * @return
     */
    public static String filterEmoji(String source) {
        if (StringUtils.isEmpty(source)) {
            return source;
        }
        StringBuilder buf = null;
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (isEmojiCharacter(codePoint)) {
                if (buf == null) {
                    buf = new StringBuilder(source.length());
                }
                buf.append(codePoint);
            }
        }
        if (buf == null) {
            return source;
        } else {
            if (buf.length() == len) {
                buf = null;
                return source;
            } else {
                return buf.toString();
            }
        }
    }

    public static void main(String[] args) {
        //billPrice("出18.5万海尔半年                     2450");
    }
}
