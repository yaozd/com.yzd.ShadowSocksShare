package com.example.ShadowSocksShare.service.impl;

import com.example.ShadowSocksShare.domain.ShadowSocksDetailsEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.nio.charset.StandardCharsets;
import java.util.Set;

/**
 * Created by zd.yao on 2018/8/25.
 */
@Slf4j
@Service
public class CrawlerServiceImpl_sharefanqiang extends _ShadowSocksCrawlerService {

    // 目标网站URL
    //https://sharefanqiang.herokuapp.com/
    private static final String TARGET_URL = "https://sharefanqiang.herokuapp.com/subscribe";
    private static final String GROUP_NAME = "_sharefanqiang";

    //暂时没有写完-2018-08-26
    @Override
    protected Set<ShadowSocksDetailsEntity> parse(Document document) {
        String bodyBase64=document.body().html().trim();
        String bodyDecodeBase64 = new String(Base64.decodeBase64(bodyBase64.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        String[] itemArray= bodyDecodeBase64.split("ssr://");
        return null;
    }
    /***
     * 此网站需要配置请求头
     * @param url
     * @return
     */
    @Override
    protected Connection getConnection(String url) {
        @SuppressWarnings("deprecation")
        Connection connection = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.108 Safari/537.36")
                .header("Host", "sharefanqiang.herokuapp.com")
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                .timeout(TIME_OUT);
        if (isProxyEnable())
            connection.proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(getProxyHost(), getProxyPort())));
        return connection;
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
