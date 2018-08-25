package com.example.ShadowSocksShare.service.impl;

import com.example.ShadowSocksShare.common.constantExt.ObfsCon;
import com.example.ShadowSocksShare.common.constantExt.ProtocolCon;
import com.example.ShadowSocksShare.common.utils.CrawlerUtil;
import com.example.ShadowSocksShare.domain.ShadowSocksDetailsEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by zd.yao on 2018/8/25.
 */
@Slf4j
@Service
public class CrawlerServiceImpl_iShadow extends _ShadowSocksCrawlerService {
    // 目标网站URL
    private static final String TARGET_URL = "https://us.ishadowx.net/";
    private static final String GROUP_NAME = "_iShadow";
    @Override
    protected Set<ShadowSocksDetailsEntity> parse(Document document) {
        // 获取目标HTML代码
        Elements itemList = document.select("[class=portfolio-item]");
        if(itemList.isEmpty()){return new HashSet<>();}
        Set<ShadowSocksDetailsEntity> entityHashSet = new HashSet<>();
        for(Element item:itemList ){
            ShadowSocksDetailsEntity entity=itemToEntity(item);
            if(entity==null){continue;}
            entityHashSet.add(entity);
        }
        return entityHashSet;
    }

    private ShadowSocksDetailsEntity itemToEntity(Element item) {
        ShadowSocksDetailsEntity entity=null;
        //public ShadowSocksDetailsEntity(String server, int server_port, String password, String method, String protocol, String obfs)
        String server=CrawlerUtil.getTextByRegex(item, ">IP Address:<span[^>]+>([^\\<]+)<");
        String serverPortStr=CrawlerUtil.getTextByRegex(item, ">Port:<span[^>]+>([^\\<]+)<");
        if(serverPortStr==null){return null;}
        if(serverPortStr.trim().isEmpty()){return null;}
        Integer serverPort=Integer.parseInt(serverPortStr.trim());
        String password=CrawlerUtil.getTextByRegex(item, ">Password:<span[^>]+>([^\\<]+)<");
        String method= CrawlerUtil.getTextByRegex(item, ">Method:([^\\<]+)<");
        String protocol=CrawlerUtil.getTextByRegex(item, ">auth_sha1_v4 ([^\\<]+)<");
        protocol=  ObjectUtils.defaultIfNull(protocol, ProtocolCon.origin);
        String obfs= ObfsCon.plain;
        entity=new ShadowSocksDetailsEntity(server,serverPort,password,method,protocol,obfs);
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
