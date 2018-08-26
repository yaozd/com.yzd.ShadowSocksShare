package com.example.ShadowSocksShare.common.constantExt;

/**
 * Created by zd.yao on 2018/8/26.
 */
public class PublicCon {
    public static final String ChromeWebDriverPath="E:\\webdrvier\\chromedriver.exe";
    /***
     ### 使用代理的思考(为什么浏览器可打开但代码不可以打开的原因)
     ```
     为什么浏览器可打开但代码不可以打开的原因
     1.WebDriver是打开浏览器是自动的走代理
     2.httpClient或jspon是通过代码，必须人为的设置代理
     ```
     */
    public static final String ProxyHost="127.0.0.1";
    public static final int ProxyPort=1080;
}
