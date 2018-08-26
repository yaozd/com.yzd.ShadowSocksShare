package com.example.ShadowSocksShare.service.impl;

import com.example.ShadowSocksShare.Application;
import com.example.ShadowSocksShare.domain.ShadowSocksDetailsEntity;
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
    //逗比根据地的ssr的数据解析
    //https://doub.io/sszhfx/
    @Test
      public void parseLink_DoubIo_Test() throws Exception {
        //正常SSR数据
        //String linkTxt="ssr://aGs3LnhqYXkueHl6OjE1MTMyOm9yaWdpbjphZXMtMjU2LWNmYjpwbGFpbjpPVEl4TWcvP29iZnNwYXJhbT0mcmVtYXJrcz1RRWxTUVU1VFUxSWdMU0F5TUVGMVp5QXRJR2hyTnk1NGFtRjVMbmg1ZWlCVFUxSSZncm91cD1RMmhoY214bGN5QllkUQ";
        //String linkTxt="ssr://NjQuMTM3LjI1MC4xMzY6MzE0MzphdXRoX3NoYTFfdjQ6Y2hhY2hhMjA6dGxzMS4yX3RpY2tldF9hdXRoOlpHOTFZaTVwYnk5emMzcG9abmd2S2pNeE5ETS8_cmVtYXJrcz01cHlzNVlXTjZMUzU2TFNtNVktMzVwMmw2SWVxT21SdmRXSXVhVzh2YzNONmFHWjRMdw";
        //String linkTxt="ssr://MTQ0LjIwMi44My4xNTE6MjMzOmF1dGhfc2hhMV92NDphZXMtMjU2LWNmYjpwbGFpbjpZMkZ5WW5sdVpR";
        String linkTxt="ssr://NDUuMzIuMjI3LjE5OToxMjIxOmF1dGhfc2hhMV92NDpjaGFjaGEyMDpodHRwX3NpbXBsZTpNVFkxTVRZNU56US8_b2Jmc3BhcmFtPSZwcm90b3BhcmFtPSZyZW1hcmtzPVctV0ZqZWkwdWVhMWktaXZsZWlLZ3VlQ3VWMEs2Wm1RNlllUE1qQkg2Wm1RNllDZk1qVXdhMklLNlp5QTZLYUI1THVZNkxTNTZJcUM1NEs1UTA0eTZJcUM1NEs1NVlxZzVMeUI2Ym1GTVRJd09ERTROamt5TncmZ3JvdXA9NUxpTjVhNmE1cHlmNXB1MDVwYXc1WVdONkxTNTZJcUM1NEs1Q3VXbWd1YWVuT2lfbnVhT3BlUzRqZVM0aXVpdnRPYVlqdWExZ2VtSGotVzNzdWlpcS1lVXFPV3VqQXJrdklIcHVZWG52cVEyTkRFNU56RTVPVGM";
        linkTxt=StringUtils.removeStartIgnoreCase(linkTxt,"ssr://");
        //解析后的数据：hk7.xjay.xyz:15132:origin:aes-256-cfb:plain:OTIxMg/?obfsparam=&remarks=QElSQU5TU1IgLSAyMEF1ZyAtIGhrNy54amF5Lnh5eiBTU1I&group=Q2hhcmxlcyBYdQ
        String linkTxtDecodeBase64=new String(Base64.decodeBase64(linkTxt),"utf-8");
        String[] ssrInfoArray = StringUtils.split(linkTxtDecodeBase64, ":");
        if(ssrInfoArray.length==0){return;}
        ShadowSocksDetailsEntity entity=null;
        String server=ssrInfoArray[0];
        Integer serverPort=Integer.parseInt(ssrInfoArray[1]);
        String protocol=ssrInfoArray[2];
        //加密方法
        String method=ssrInfoArray[3];
        String obfs=ssrInfoArray[4];
        //密码包含在最后的ssrInfoArray[5]中。
        String otherInfo=ssrInfoArray[5];
        int end=otherInfo.indexOf("/")==-1?otherInfo.length()-1:otherInfo.indexOf("/");
        otherInfo=otherInfo.substring(0,end);
        String otherInfoDecodeBase64=new String(Base64.decodeBase64(otherInfo),"utf-8");
        String password=otherInfoDecodeBase64;
        entity=new ShadowSocksDetailsEntity(server,serverPort,password,method,protocol,obfs);
        crawlerServiceImplIShadow.parseLink(linkTxt);
    }
    @Test
    public void parseLink2_Test() throws Exception {
        //正常SSR数据
        String linkTxt="ssr://NjQuMTM3LjI1MC4xMzY6MzE0MzphdXRoLK13Onk8Iw8oJLhq2hgsdX3NoYTFfdjQ6Y2hhY2hhMjA6dGxzMS4yX3RpY2tldF9hdXRoOlpHOTFZaTVwYnk5emMzcG9abmd2S2pNeE5ETS8_cmVtYXJrcz01cHlzNVlXTjZMUzU2TFNtNVktMzVwMmw2SWVxT21SdmRXSXVhVzh2YzNONmFHWjRMdw";
        ShadowSocksDetailsEntity entity=crawlerServiceImplIShadow.parseLink(linkTxt);
        System.out.println(entity);
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
