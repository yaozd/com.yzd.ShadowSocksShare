package com.example.ShadowSocksShare.service.impl;

import com.example.ShadowSocksShare.common.utils.ChromeWebDriverUtil;
import com.example.ShadowSocksShare.common.utils.RegExUtil;
import com.example.ShadowSocksShare.common.utils.SSRParseUtil;
import com.example.ShadowSocksShare.domain.ShadowSocksDetailsEntity;
import com.google.zxing.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by zd.yao on 2018/8/26.
 */
@Slf4j
@Service
public class CrawlerServiceImpl_Google extends _ShadowSocksCrawlerService {
    // 目标网站URL
    private static final String TARGET_URL = "https://plus.google.com/communities/106442142549456855872/stream/62937124-015f-4f9c-81c9-914444653b8a";
    private static final String GROUP_NAME = "A1Google";

    @Override
    protected Set<ShadowSocksDetailsEntity> parse(Document document) throws IOException, NotFoundException {
        String bodyHtml=document.body().html();
        Set<ShadowSocksDetailsEntity> entityHashSet = new HashSet<>();
        entityHashSet.addAll(parseFun2(bodyHtml));
        System.out.println(GROUP_NAME+"执行完成。");
        return entityHashSet;
    }
    //匹配模式二
    protected Set<ShadowSocksDetailsEntity> parseFun2(String bodyHtml){
        Set<ShadowSocksDetailsEntity> entityHashSet = new HashSet<>();
        int count=0;
        String reg="ssr://[\\w]+";
        List<String> itemList= RegExUtil.getMatchers(reg,bodyHtml);
        if(itemList.isEmpty()){return new HashSet<>();}

        for(String item:itemList ){
            ShadowSocksDetailsEntity entity=itemToEntity(item);
            if(entity==null){continue;}
            entityHashSet.add(entity);
            count++;
            if(count>5){
                break;
            }
            System.out.println(GROUP_NAME+"总数6，当前计数="+count);
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
        //entity.setRemarks(TARGET_URL);
        entity.setRemarks(entity.getServer()+":"+entity.getServer_port());
        //======
        // 测试网络
        if (isReachable(entity)){
            entity.setValid(true);
            return entity;
        }
        //return entity;
        return null;
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
