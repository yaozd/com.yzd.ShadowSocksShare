package com.example.ShadowSocksShare.common.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * 这是个正则表达式应用类，用来匹配和替换字串用的
 * @author
 * @version
 */

public class RegExUtil {

    /**
     * 要求大小写都匹配正则表达式
     * @param pattern 正则表达式模式
     * @param str 要匹配的字串
     * @return boolean值
     * @since  1.0
     */
    public static final boolean ereg(String pattern, String str)
    {
        try
        {
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(str);
            return m.find();
        }
        catch (PatternSyntaxException e)
        {
            throw e;
        }
    }

    /**
     * 匹配且替换字串
     * @param pattern 正则表达式模式
     * @param newstr 要替换匹配到的新字串
     * @param str 原始字串
     * @return 匹配后的字符串
     * @since  1.0
     */

    public static final String ereg_replace(String pattern, String newstr, String str)
    {
        try
        {
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(str);
            return m.replaceAll(newstr);
        }
        catch (PatternSyntaxException e)
        {
            throw e;
        }
    }

    /**
     * 主要用于模板中模块标记分析函数 把查找到的元素加到list中
     * @param pattern为正则表达式模式
     * @param str 原始字串
     * @return list
     * @since  1.0
     */
    public static final List<String> splitTags2List(String pattern, String str)
    {
        List<String> list = new ArrayList<>();
        try {
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(str);
            while (m.find())
            {
                list.add(ereg_replace("(\\[\\#)|(\\#\\])", "", m.group()));
            }
            return list;
        }
        catch (PatternSyntaxException e) {
            throw e;
        }
    }
    /**
     * 模块标记分析函数
     * 功能主要是把查找到的元素加到list中
     * @param pattern为正则表达式模式
     * @param str 原始字串
     * @since  1.0
     */
    public static final String[] splitTags(String pattern, String str)
    {
        try {
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(str);
            String[] array = new String[m.groupCount()];
            int i = 0;
            while (m.find())
            {
                array[i] = ereg_replace("(\\[\\#)|(\\#\\])", "", m.group());
                i++;
            }
            return array;
        }
        catch (PatternSyntaxException e) {
            throw e;
        }
    }


    /**
     * 匹配所有符合模式要求的字串并加到矢量list数组中
     * @param pattern为正则表达式模式
     * @param str 原始字串
     * @return list
     * @since  1.0
     */
    public static final List regMatchAll2list(String pattern, String str)
    {
        List<String> list = new ArrayList<>();
        try
        {
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(str);
            while (m.find())
            {
                list.add(m.group());
            }
            return list;
        }
        catch (PatternSyntaxException e)
        {
            throw e;
        }
    }

    /**
     * 匹配所有符合模式要求的字串并加到字符串数组中
     * @param str 原始字串
     * @return array
     * @since  1.0
     */
    public static final String[] regMatchAll2Array(String pattern, String str) throws PatternSyntaxException
    {
        try
        {
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(str);
            String[] array = new String[m.groupCount()];
            int i = 0;
            while (m.find())
            {
                array[i] = m.group();
                i++;
            }
            return array;
        }
        catch (PatternSyntaxException e)
        {
            throw e;
        }
    }
    /**
     * 转义正则表达式字符(之所以需要将\和$字符用escapeDollarBackslash方法的方式是因为用repalceAll是不行的，简单的试试"$".repalceAll("\\$","\\\\$")你会发现这个调用会导致数组越界错误)
     * @return array
     * @since  1.0
     */
    public static String escapeDollarBackslash(String original) {
        StringBuffer buffer=new StringBuffer(original.length());
        for (int i=0;i<original.length();i++) {
            char c=original.charAt(i);
            if (c=='\\'||c=='$') {
                buffer.append("\\").append(c);
            } else{
                buffer.append(c);
            }
        }
        return buffer.toString();
    }

    /**
     * 提取指定字串的函数
     * 功能主要是把查找到的元素
     * @param str 原始字串
     * @since  1.0
     */
    public static final String fetchStr(String pattern, String str) {
        String returnValue = null;
        try {
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(str);
            while (m.find()) {
                returnValue = m.group();
            }
            return returnValue;
        } catch (PatternSyntaxException e) {
            return returnValue;
        }
    }
    public static Matcher getMatcher(String regEx, String str) {
        Pattern pat = Pattern.compile(regEx);
        return pat.matcher(str);
    }
}

