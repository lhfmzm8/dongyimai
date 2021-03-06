package org.example.liuhengfei.utils;

import org.apache.commons.text.StringSubstitutor;

import java.util.Map;

public class StringReplace {

    //替换指定字符内容
    public static String replace(Map<String, Object> params, String context) {
        StringSubstitutor stringSubstitutor = new StringSubstitutor(params, "[", "]");
        return stringSubstitutor.replace(context);
    }

    //把首字母转换成大写
    public static String FistWordLowUp(String str) {
        char[] chars = str.toCharArray();
        if (chars[0] >= 'a' && chars[0] <= 'z') {
            chars[0] = (char) (chars[0] - 32);
        }
        return new String(chars);
    }

    //大驼峰
    //item_cat ItemCat
    public static String removeUp(String str) {
        String[] ss = str.split("_");
        if (ss != null && ss.length == 2) {
            return FistWordLowUp(ss[0]) + FistWordLowUp(ss[1]);
        }
        if (ss != null && ss.length == 3) {
            return FistWordLowUp(ss[0]) + FistWordLowUp(ss[1]) + FistWordLowUp(ss[2]);
        }
        if (ss != null && ss.length == 4) {
            return FistWordLowUp(ss[0]) + FistWordLowUp(ss[1]) + FistWordLowUp(ss[2]) + FistWordLowUp(ss[3]);
        }
        return FistWordLowUp(str);
    }

    //小驼峰
    //item_cat itemCat
    public static String removeUpFromTwo(String str) {
        String[] ss = str.split("_");
        if (ss != null && ss.length == 2) {
            return ss[0] + FistWordLowUp(ss[1]);
        }
        if (ss != null && ss.length == 3) {
            return ss[0] + FistWordLowUp(ss[1]) + FistWordLowUp(ss[2]);
        }
        if (ss != null && ss.length == 4) {
            return ss[0] + FistWordLowUp(ss[1]) + FistWordLowUp(ss[2]) + FistWordLowUp(ss[3]);
        }
        return str;
    }

    //全小写
    //item_cat itemcat
    public static String remove(String str) {
        String[] ss = str.split("_");
        if (ss != null && ss.length == 2) {
            return ss[0] + ss[1];
        }
        if (ss != null && ss.length == 3) {
            return ss[0] + ss[1] + ss[2];
        }
        if (ss != null && ss.length == 4) {
            return ss[0] + ss[1] + ss[2] + ss[3];
        }
        return str;
    }

}
