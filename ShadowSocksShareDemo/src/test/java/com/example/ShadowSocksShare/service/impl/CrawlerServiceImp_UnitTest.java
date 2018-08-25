package com.example.ShadowSocksShare.service.impl;

import com.example.ShadowSocksShare.Application;
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
//@SpringBootTest
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//单元测试如果当前测试方法，是非web测试，则可以关闭web环境：SpringBootTest.WebEnvironment.NONE
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.NONE, classes = Application.class)
public class CrawlerServiceImp_UnitTest {

    @Autowired
    ShadowSocksService shadowSocksService;
    @Autowired
    CrawlerServiceImpl_iShadow crawlerServiceImplIShadow;
    @Autowired
    CrawlerServiceImpl_sharefanqiang crawlerServiceImplSharefanqiang;
    @Test
    public void CrawlerServiceImpl_iShadow_Test() throws Exception {
        shadowSocksService.crawlerAndSave(crawlerServiceImplIShadow);
    }
    @Test
    public void CrawlerServiceImpl_sharefanqiang_Test() throws Exception {
        shadowSocksService.crawlerAndSave(crawlerServiceImplSharefanqiang);
    }
}
