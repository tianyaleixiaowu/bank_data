package com.tianyalei.bank.wash;

/**
 * @author wuweifeng wrote on 2018/8/3.
 */
public class Test {
    public static void main(String[] args) {
        System.out.println(wash("<span class=emoji emoji26a1></span>半年 城商 100W 1900"));
    }

    public static Double wash(String line) {
        if (line.toLowerCase().contains("万") || line.contains("w") || line.contains("*")) {
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
        }
        return null;
    }


}
