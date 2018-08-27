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
    @Autowired
    CrawlerServiceImpl_FreeYitianjianss crawlerServiceImplFreeYitianjianss;
    @Test
    public void CrawlerServiceImpl_iShadow_Test() throws Exception {
        shadowSocksService.crawlerAndSave(crawlerServiceImplIShadow);
    }
    @Test
    public void CrawlerServiceImpl_sharefanqiang_Test() throws Exception {
        shadowSocksService.crawlerAndSave(crawlerServiceImplSharefanqiang);
    }
    @Test
    public void CrawlerServiceImpl_FreeYitianjianss_Test() throws Exception {
        shadowSocksService.crawlerAndSave(crawlerServiceImplFreeYitianjianss);
    }
    //免费账号分享 | 逗比根据地-目前最好的分享网站-byArvin
    @Autowired
    CrawlerServiceImpl_DoubIo crawlerServiceImplDoubIo;

    @Test
    public void CrawlerServiceImpl_DoubIo_Test() throws Exception {
        shadowSocksService.crawlerAndSave(crawlerServiceImplDoubIo);
    }
    //===================================================
    @Autowired
    CrawlerServiceImpl_Google crawlerServiceImplGoogle;
    @Test
    public void CrawlerServiceImpl_Google_Test() throws Exception {
        shadowSocksService.crawlerAndSave(crawlerServiceImplGoogle);
    }
    //
    @Autowired
    CrawlerServiceImpl_Google2 crawlerServiceImplGoogle2;
    @Test
    public void CrawlerServiceImpl_Google2_Test() throws Exception {
        shadowSocksService.crawlerAndSave(crawlerServiceImplGoogle2);
    }
    //
    @Autowired
    CrawlerServiceImpl_Google3 crawlerServiceImplGoogle3;
    @Test
    public void CrawlerServiceImpl_Google3_Test() throws Exception {
        shadowSocksService.crawlerAndSave(crawlerServiceImplGoogle3);
    }
}
