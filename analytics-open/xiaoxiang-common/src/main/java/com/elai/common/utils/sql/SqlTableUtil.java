package com.elai.common.utils.sql;

import com.github.pagehelper.util.StringUtil;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ck table 工具
 *
 */
public class SqlTableUtil {


    /** 用户分群. */
    private static final String GROUP = "group";

    /** 用户标签. */
    private static final String TAG = "tag";

    /** 用户画像 */
    private static final String DRAW = "draw";

    private static final String DEFAULT = "xx";

    /**
     * 私有的构造方法.
     */
    private SqlTableUtil() {
        super();
    }

    /**
     *
     * @param
     * @return
     */
    public static String getGroupName() {
        return getTableName(GROUP);
    }
    public static String getTagName() {
        return getTableName(TAG);
    }
    public static String getDrawName() {
        return getTableName(DRAW);
    }

    private static String getTableName(String tenantKey) {
        tenantKey = StringUtil.isEmpty(tenantKey)?DEFAULT:tenantKey;
        String tableName = tenantKey+"_"+ System.currentTimeMillis() +"_"+ getRandom(4);
        return tableName;
    }

    /**
     * 得到指定长度的随机值
     * @param strLength
     * @return
     */
    public static String getRandom(int strLength) {
        Random rm = new Random();

        // 获得随机数
        double pross = (1 + rm.nextDouble()) * Math.pow(10, strLength);

        // 将获得的获得随机数转化为字符串
        String fixLenthString = String.valueOf(pross);

        // 返回固定的长度的随机数
        return fixLenthString.substring(1, strLength + 1);
    }


    //把 下划线转换成 首字母大写形式
    public static String xhxToJavaStr(String key) {
        if (StringUtil.isEmpty(key)) {
            return key;
        }
        if (!key.contains("_")) {
            return key;
        }
        String returnStr = "";
        boolean b = false;
        for (char c : key.toCharArray()) {
            if (c == '_') {
                b = true;
                continue;
            }
            String tmpStr = c + "";
            if (b) {
                b = false;
                tmpStr = tmpStr.toUpperCase();
            }
            returnStr += tmpStr;

        }
        return returnStr;
    }

    /**
     * java驼峰字符串转换为数据库_格式字符
     * @param key
     * @return
     */
    public static String javaStrToXhx(String key) {
        if (StringUtil.isEmpty(key)) {
            return key;
        }
        String returnStr = "";
        String tempStr = "";
        for (int i = 0; i < key.length(); i++) {
            tempStr = Character.isUpperCase(key.charAt(i))?("_"+key.charAt(i)).toLowerCase():key.charAt(i)+"";
            returnStr = returnStr + tempStr;
        }
        return returnStr;
    }


}