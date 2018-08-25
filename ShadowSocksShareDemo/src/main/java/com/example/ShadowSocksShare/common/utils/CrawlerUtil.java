package com.example.ShadowSocksShare.common.utils;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.regex.Matcher;

/**
 * Created by zd.yao on 2018/8/25.
 */
public class CrawlerUtil {
    /***
     *通过正则获得text
     */
    public static String getTextByRegex(Element item,String regEx){
        String str=item.html();
        Matcher mat = RegExUtil.getMatcher(regEx, str);
        //使用group时一定要使用mat.find()方法才可以读取到
        if(mat.find()){
            return mat.group(1).trim();
        }
        return null;
    }
    /***
     * 通过元素获得text
     * @param item
     * @param cssQuery
     * @return
     */
    public static String getTextByElement(Element item,String cssQuery){
        Elements elements2 = item.select(cssQuery);
        if(elements2.isEmpty()){return null;}
        if(elements2.size()>1){return null;}
        String value=elements2.get(0).text();
        return value.trim();
    }
}
