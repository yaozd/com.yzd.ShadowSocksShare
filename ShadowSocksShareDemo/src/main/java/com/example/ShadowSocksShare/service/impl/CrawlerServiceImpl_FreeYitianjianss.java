package com.example.ShadowSocksShare.service.impl;

import com.example.ShadowSocksShare.common.utils.ChromeWebDriverUtil;
import com.example.ShadowSocksShare.common.utils.CrawlerUtil;
import com.example.ShadowSocksShare.domain.ShadowSocksDetailsEntity;
import com.google.zxing.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zd.yao on 2018/8/26.
 */
@Slf4j
@Service
public class CrawlerServiceImpl_FreeYitianjianss extends _ShadowSocksCrawlerService {
    // 目标网站URL
    private static final String TARGET_URL = "https://free.yitianjianss.com/";
    private static final String GROUP_NAME = "_Yitianjianss";
    @Override
    protected Set<ShadowSocksDetailsEntity> parse(Document document){
        String bodyHtml=document.body().html();
        String reg="src=\"(/img/qrcode_image[^\"]+)\">";
        List<String> itemList=getMatchers(reg,bodyHtml);
        for(String item:itemList){
            String imgPath= CrawlerUtil.getTextByRegex(item,reg);
            String imgUrl=TARGET_URL+imgPath;
            ShadowSocksDetailsEntity ss = null;
            try {
                ss = parseURL(imgUrl);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NotFoundException e) {
                e.printStackTrace();
            }
            ss.setValid(false);
            ss.setValidTime(new Date());
            ss.setTitle(document.title());
            ss.setRemarks(TARGET_URL);
            ss.setGroup("ShadowSocks-Share");

        }
        return null;
    }
    @Override
    protected Document getDocument(){
        String html= ChromeWebDriverUtil.getHtml(getTargetURL());
        return  Jsoup.parse(html);
    }
   private List<String> getMatchers(String regex, String source){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(source);
        List<String> list = new ArrayList<>();
        while (matcher.find()) {
            list.add(matcher.group());
        }
        return list;
    }


    @Override
    protected String getTargetURL() {
        return TARGET_URL;
    }

    @Override
    protected boolean isProxyEnable() {
        return false;
    }

    @Override
    protected String getProxyHost() {
        return null;
    }

    @Override
    protected int getProxyPort() {
        return 0;
    }
}
