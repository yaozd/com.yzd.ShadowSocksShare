package com.example.ShadowSocksShare.service.impl;

import com.example.ShadowSocksShare.common.constantExt.PublicCon;
import com.example.ShadowSocksShare.common.utils.ChromeWebDriverUtil;
import com.example.ShadowSocksShare.common.utils.WebDriverUtil;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

/**
 * Created by zd.yao on 2018/8/26.
 */
public class WebClient_UnitTest {
    @Test
    public void getChromeWebDriver() {
        WebDriver webDriver = null;
        try {
            //webDriver = WebDriverUtil.createChromeWebDriver("E:\\webdrvier\\chromedriver.exe");//修改路径
            webDriver = WebDriverUtil.createChromeWebDriver(PublicCon.ChromeWebDriverPath);//修改路径
            //webDriver.get("https://www.baidu.com/");
            webDriver.get("https://free.yitianjianss.com/");
            System.out.println(webDriver.getTitle());
            String body= webDriver.getPageSource();
            System.out.println(body);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (webDriver != null) {
                webDriver.close();
            }
        }
    }
    @Test
    public void getImgByWebDriver(){
        String url="https://free.yitianjianss.com/img/qrcode_image/295/a2e4fcdf87e368cd9eacd8dbd9b001bc.png";
        String imgStr= ChromeWebDriverUtil.getHtml(url);
        System.out.println(imgStr);
    }
}
