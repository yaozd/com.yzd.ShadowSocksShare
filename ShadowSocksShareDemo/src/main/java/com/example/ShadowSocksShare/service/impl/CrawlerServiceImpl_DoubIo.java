package com.example.ShadowSocksShare.service.impl;

import com.example.ShadowSocksShare.common.constantExt.PublicCon;
import com.example.ShadowSocksShare.common.utils.ChromeWebDriverUtil;
import com.example.ShadowSocksShare.common.utils.CrawlerUtil;
import com.example.ShadowSocksShare.domain.ShadowSocksDetailsEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
        //String reg="ssr://[\\w]+";
        String reg="ssr</span><span class=\"pun\">:</span><span class=\"com\">//([\\w]+)";
        List<String> itemList=getMatchers(reg,bodyHtml);
        if(itemList.isEmpty()){return new HashSet<>();}
        Set<ShadowSocksDetailsEntity> entityHashSet = new HashSet<>();
        for(String item:itemList ){
            String linkData= CrawlerUtil.getTextByRegex(item, reg);
            String linkTxt="ssr://"+linkData;
            ShadowSocksDetailsEntity entity=itemToEntity(linkTxt);
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
        String debugLinkTxt=linkTxt;
        linkTxt= StringUtils.removeStartIgnoreCase(linkTxt, "ssr://");
        ShadowSocksDetailsEntity entity=null;
        try{
        //解析后的数据：hk7.xjay.xyz:15132:origin:aes-256-cfb:plain:OTIxMg/?obfsparam=&remarks=QElSQU5TU1IgLSAyMEF1ZyAtIGhrNy54amF5Lnh5eiBTU1I&group=Q2hhcmxlcyBYdQ
        String linkTxtDecodeBase64= null;
        linkTxtDecodeBase64 = new String(org.apache.commons.codec.binary.Base64.decodeBase64(linkTxt),"utf-8");
        String[] ssrInfoArray = StringUtils.split(linkTxtDecodeBase64, ":");
        if(ssrInfoArray.length==0){return null;}
        String server=ssrInfoArray[0];
        Integer serverPort=Integer.parseInt(ssrInfoArray[1]);
        String protocol=ssrInfoArray[2];
        String method=ssrInfoArray[3];
        String obfs=ssrInfoArray[4];
        //密码包含在最后的ssrInfoArray[5]中。
        String otherInfo=ssrInfoArray[5];
        int end=otherInfo.indexOf("/")==-1?otherInfo.length()-1:otherInfo.indexOf("/");
        otherInfo=otherInfo.substring(0,end);
        String otherInfoDecodeBase64=new String(org.apache.commons.codec.binary.Base64.decodeBase64(otherInfo),"utf-8");
        String password=otherInfoDecodeBase64;
        entity=new ShadowSocksDetailsEntity(server,serverPort,password,method,protocol,obfs);
        } catch (Exception e) {
            throw new RuntimeException("SSR 连接[" + linkTxt + "]解析异常：" + e.getMessage(), e);
        }
        return entity;
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

