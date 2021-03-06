package com.example.ShadowSocksShare.web;

import com.example.ShadowSocksShare.common.utils.DosUtil;
import com.example.ShadowSocksShare.domain.ShadowSocksDetailsEntity;
import com.example.ShadowSocksShare.domain.ShadowSocksEntity;
import com.example.ShadowSocksShare.service.crawler.ShadowSocksService;
import com.example.ShadowSocksShare.service.impl.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.zxing.WriterException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by zd.yao on 2018/8/25.
 */
@Slf4j
@Controller
public class MainController {
    @Autowired
    ShadowSocksService shadowSocksService;
    /**
     * 首页
     */
    @RequestMapping("/")
    public String index(@PageableDefault(page = 0, size = 50, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        List<ShadowSocksEntity> ssrList = shadowSocksService.findAll(pageable);
        List<ShadowSocksDetailsEntity> ssrdList = new ArrayList<>();
        for (ShadowSocksEntity ssr : ssrList) {
            ssrdList.addAll(ssr.getShadowSocksSet());
        }
        // ssr 信息
        model.addAttribute("ssrList", ssrList);
        // ssr 明细信息，随机排序
        Collections.shuffle(ssrdList);
        model.addAttribute("ssrdList", ssrdList);
        return "index";
    }
    /**
     * SSR 订阅地址
     */
    @RequestMapping("/subscribe")
    @ResponseBody
    public ResponseEntity<String> subscribe(boolean valid, @PageableDefault(page = 0, size = 1000, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        List<ShadowSocksEntity> ssrList = shadowSocksService.findAll(pageable);
        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body(shadowSocksService.toSSLink(ssrList, valid));
    }
    /**
     * 订阅 一条 有效的 Json
     */
    @RequestMapping("/subscribeJson")
    @ResponseBody
    public ResponseEntity<String> subscribeJson() throws JsonProcessingException {
        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body(shadowSocksService.findFirstByRandom().getJsonStr());
    }

    /**
     * 二维码
     */
    @RequestMapping(value = "/createQRCode")
    @ResponseBody
    public ResponseEntity<byte[]> createQRCode(long id, String text, int width, int height, WebRequest request) throws IOException, WriterException {
        // 缓存未失效时直接返回
        if (request.checkNotModified(id))
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(MediaType.IMAGE_PNG_VALUE))
                    .cacheControl(CacheControl.maxAge(1, TimeUnit.DAYS))
                    .eTag(String.valueOf(id))
                    .body(null);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(MediaType.IMAGE_PNG_VALUE))
                .cacheControl(CacheControl.maxAge(1, TimeUnit.DAYS))
                .eTag(String.valueOf(id))
                .body(shadowSocksService.createQRCodeImage(text, width, height));
    }
    //======================================================================
    /***
     * 数据加载
     * @param name
     * @return
     */
    @RequestMapping(value = "/run/all")
    @ResponseBody
    public ResponseEntity<String> run_all(String name) {
        DosUtil.openHttp("http://localhost:28080/run/google3");
        sleep15S();
        DosUtil.openHttp("http://localhost:28080/run/google2");
        sleep15S();
        DosUtil.openHttp("http://localhost:28080/run/google1");
        sleep15S();
        DosUtil.openHttp("http://localhost:28080/run/other");
        return ResponseEntity.ok().body("SSR数据加载打开全部/run/all-OK...");
    }
    private void sleep15S(){
        try {
            TimeUnit.SECONDS.sleep(15);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    //======================================================================

    @Autowired
    CrawlerServiceImpl_iShadow crawlerServiceImplIShadow;
    @Autowired
    CrawlerServiceImpl_FreeYitianjianss crawlerServiceImplFreeYitianjianss;
    @Autowired
    CrawlerServiceImpl_DoubIo crawlerServiceImplDoubIo;
    /***
     * 数据加载
     * @param name
     * @return
     */
    @RequestMapping(value = "/run/other")
    @ResponseBody
    public ResponseEntity<String> run_other(String name) {
        shadowSocksService.crawlerAndSave(crawlerServiceImplDoubIo);
        shadowSocksService.crawlerAndSave(crawlerServiceImplIShadow);
        shadowSocksService.crawlerAndSave(crawlerServiceImplFreeYitianjianss);
        return ResponseEntity.ok().body("数据加载-OK...");
    }

    @Autowired
    CrawlerServiceImpl_Google crawlerServiceImplGoogle;
    /***
     * 数据加载
     * @param name
     * @return
     */
    @RequestMapping(value = "/run/google1")
    @ResponseBody
    public ResponseEntity<String> run_google1(String name) {
        return ResponseEntity.ok().body("数据加载-OK...");
    }
    @Autowired
    CrawlerServiceImpl_Google2 crawlerServiceImplGoogle2;
    @RequestMapping(value = "/run/google2")
    @ResponseBody
    public ResponseEntity<String> run_google2(String name) {
        shadowSocksService.crawlerAndSave(crawlerServiceImplGoogle2);
        return ResponseEntity.ok().body("数据加载(google2)-OK...");
    }
    @Autowired
    CrawlerServiceImpl_Google3 crawlerServiceImplGoogle3;
    @RequestMapping(value = "/run/google3")
    @ResponseBody
    public ResponseEntity<String> run_google3(String name) {
        shadowSocksService.crawlerAndSave(crawlerServiceImplGoogle3);
        return ResponseEntity.ok().body("数据加载(google3)-OK...");
    }
    //======================================================================
}
