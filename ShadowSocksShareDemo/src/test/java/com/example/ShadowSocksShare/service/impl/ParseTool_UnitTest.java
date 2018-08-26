package com.example.ShadowSocksShare.service.impl;

import com.example.ShadowSocksShare.Application;
import com.google.zxing.NotFoundException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Created by zd.yao on 2018/8/25.
 */

@RunWith(SpringRunner.class)
//@SpringBootTest
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//单元测试如果当前测试方法，是非web测试，则可以关闭web环境：SpringBootTest.WebEnvironment.NONE
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.NONE, classes = Application.class)
public class ParseTool_UnitTest {

    @Autowired
    CrawlerServiceImpl_iShadow crawlerServiceImplIShadow;

    @Test
    public void parseHtml_Test() throws Exception {
        //
        //在Java代码中解析html,获得其中的值
        //https://blog.csdn.net/zhanglei500038/article/details/74858395
        String html = "htmltest";
        Document doc = Jsoup.parse(html);
        Elements rows = doc.select("table[class=list]").get(0).select("tr");

    }
    @Test
      public void parseLink_Test() throws Exception {
        //正常SSR数据
        String linkTxt="ssr://aGs3LnhqYXkueHl6OjE1MTMyOm9yaWdpbjphZXMtMjU2LWNmYjpwbGFpbjpPVEl4TWcvP29iZnNwYXJhbT0mcmVtYXJrcz1RRWxTUVU1VFUxSWdMU0F5TUVGMVp5QXRJR2hyTnk1NGFtRjVMbmg1ZWlCVFUxSSZncm91cD1RMmhoY214bGN5QllkUQ";
        crawlerServiceImplIShadow.parseLink(linkTxt);
    }
    @Test
    public void parseLinkForSubscribe_Test() throws Exception {
        //例如：https://sharefanqiang.herokuapp.com/subscribe
        //如果是SSR订阅的数据转为本地数据，需要进行两次decodeBase64的解码
        String linkTxt="xxx";
        String ssrInfoStr = new String(Base64.decodeBase64(StringUtils.remove(linkTxt, "ssr://").getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        crawlerServiceImplIShadow.parseLink(ssrInfoStr);
    }
    @Test
    public void parseURL() throws IOException, NotFoundException {
        String url = "https://free.yitianjianss.com/img/qrcode_image/293/c797e96ed6969ab4bc24726104fe12ea.png";
        crawlerServiceImplIShadow.parseURL(url);
    }
}
