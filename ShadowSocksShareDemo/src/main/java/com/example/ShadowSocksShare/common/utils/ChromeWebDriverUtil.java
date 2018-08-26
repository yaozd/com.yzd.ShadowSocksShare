package com.example.ShadowSocksShare.common.utils;

import com.example.ShadowSocksShare.common.constantExt.PublicCon;
import org.openqa.selenium.WebDriver;

/**
 * Created by zd.yao on 2018/8/26.
 */
public class ChromeWebDriverUtil {

    public static String getHtml(String url){
        String bodyHtml=null;
        WebDriver webDriver = null;
        try {
            //webDriver = WebDriverUtil.createChromeWebDriver("E:\\webdrvier\\chromedriver.exe");//修改路径
            webDriver = WebDriverUtil.createChromeWebDriver(PublicCon.ChromeWebDriverPath);//修改路径
            //webDriver.get("https://www.baidu.com/");
            webDriver.get(url);
            //System.out.println(webDriver.getTitle());
            bodyHtml= webDriver.getPageSource();
            //System.out.println(body);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (webDriver != null) {
                webDriver.close();
            }
        }
        return bodyHtml;
    }
}
