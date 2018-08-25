package com.example.ShadowSocksShare.service.impl;

import com.example.ShadowSocksShare.service.crawler.ShadowSocksService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by zd.yao on 2018/8/25.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CrawlerServiceImp_UnitTest {

    @Autowired
    ShadowSocksService shadowSocksService;
    @Autowired
    CrawlerServiceImpl_iShadow crawlerServiceImplIShadow;
    @Test
    public void CrawlerServiceImpl_iShadow_Test() throws Exception {
        shadowSocksService.crawlerAndSave(crawlerServiceImplIShadow);
    }
}
