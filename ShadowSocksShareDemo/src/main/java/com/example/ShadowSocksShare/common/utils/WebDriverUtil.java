package com.example.ShadowSocksShare.common.utils;

/**
 * Created by zd.yao on 2018/8/26.
 */
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

/**
 * @Description:crawler
 * @Author: old
 * @CreateTime:2017-11-15 :15:16:16
 */
public class WebDriverUtil {


    /**
     * 创建Chrome
     *
     * @param path 路径
     * @return
     * @throws Exception
     */
    public static WebDriver createChromeWebDriver(String path) throws Exception {
        if (path == null || "".equals(path)) {
            throw new Exception("配置错误, 没有配置:chrome path");
        }
        System.getProperties().setProperty("webdriver.chrome.driver", path);
        WebDriver webDriver = new ChromeDriver();
        webDriver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        webDriver.manage().window().setSize(new Dimension(1024, 768));
        return webDriver;
    }


    /**
     * 创建Firefox
     *
     * @param path 路径
     * @return
     * @throws Exception
     */
    public static WebDriver createFirefoxWebDriver(String path) throws Exception {
        if (path == null || "".equals(path)) {
            throw new Exception("配置错误, 没有配置 gecko path");
        }
        System.setProperty("webdriver.gecko.driver", path);
        WebDriver webDriver = new FirefoxDriver();
        return webDriver;
    }

}

