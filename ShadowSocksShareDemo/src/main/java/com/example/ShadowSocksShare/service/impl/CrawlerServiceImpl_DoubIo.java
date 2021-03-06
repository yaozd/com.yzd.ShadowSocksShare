package com.example.ShadowSocksShare.service.impl;

import com.example.ShadowSocksShare.common.constantExt.PublicCon;
import com.example.ShadowSocksShare.common.utils.ChromeWebDriverUtil;
import com.example.ShadowSocksShare.common.utils.CrawlerUtil;
import com.example.ShadowSocksShare.common.utils.SSRParseUtil;
import com.example.ShadowSocksShare.domain.ShadowSocksDetailsEntity;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zd.yao on 2018/8/26.
 */
@Slf4j
@Service
public class CrawlerServiceImpl_DoubIo extends _ShadowSocksCrawlerService {
    //免费账号分享 | 逗比根据地-目前最好的分享网站-byArvin
    // 目标网站URL
    private static final String TARGET_URL = "https://doub.io/sszhfx/";
    private static final String GROUP_NAME = "_DoubIo";
    @Override
    protected Set<ShadowSocksDetailsEntity> parse(Document document){
        String bodyHtml=document.body().html();
        Set<ShadowSocksDetailsEntity> entityHashSet = new HashSet<>();
        entityHashSet.addAll(parseFun1(bodyHtml));
        entityHashSet.addAll(parseFun2(bodyHtml));
        return entityHashSet;
    }
    //匹配模式一
    protected Set<ShadowSocksDetailsEntity> parseFun1(String bodyHtml){
        Set<ShadowSocksDetailsEntity> entityHashSet = new HashSet<>();
        //String reg="ssr://[\\w]+";
        String reg="ssr</span><span class=\"pun\">:</span><span class=\"com\">//([\\w]+)";
        List<String> itemList=getMatchers(reg,bodyHtml);
        if(itemList.isEmpty()){return new HashSet<>();}

        for(String item:itemList ){
            String linkData= CrawlerUtil.getTextByRegex(item, reg);
            String linkTxt="ssr://"+linkData;
            ShadowSocksDetailsEntity entity=itemToEntity(linkTxt);
            if(entity==null){continue;}
            entityHashSet.add(entity);
        }
        return entityHashSet;
    }
    //匹配模式二
    protected Set<ShadowSocksDetailsEntity> parseFun2(String bodyHtml){
        Set<ShadowSocksDetailsEntity> entityHashSet = new HashSet<>();
        String reg="ssr://[\\w]+";
        //String reg="ssr</span><span class=\"pun\">:</span><span class=\"com\">//([\\w]+)";
        List<String> itemList=getMatchers(reg,bodyHtml);
        if(itemList.isEmpty()){return new HashSet<>();}

        for(String item:itemList ){
            ShadowSocksDetailsEntity entity=itemToEntity(item);
            if(entity==null){continue;}
            entityHashSet.add(entity);
        }
        return entityHashSet;
    }
    private ShadowSocksDetailsEntity itemToEntity(String item){
        ShadowSocksDetailsEntity entity = null;
        try {
            entity = parseLink(item);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        //=====
        entity.setValid(false);
        entity.setValidTime(new Date());
        entity.setTitle(GROUP_NAME);
        entity.setGroup(GROUP_NAME);
        entity.setRemarks(TARGET_URL);
        //======
        // 测试网络
        if (isReachable(entity)){
            entity.setValid(true);
        }
        return entity;
    }
    /**
     * 连接解析
     */
    @Override
    protected ShadowSocksDetailsEntity parseLink(String linkTxt) throws UnsupportedEncodingException {
        return SSRParseUtil.parseLink(linkTxt);
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
        return true;
    }

    @Override
    protected String getProxyHost() {
        return PublicCon.ProxyHost;
    }

    @Override
    protected int getProxyPort() {
        return PublicCon.ProxyPort;
    }
}

