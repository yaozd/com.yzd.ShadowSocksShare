package com.example.ShadowSocksShare;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

/**
 * Created by zd.yao on 2018/8/25.
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Application.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
        openHttp();
    }

    private static void openHttp() {
        String path = "http://localhost:28080/";
        Runtime run = Runtime.getRuntime();
        try {
            Process process = run.exec("cmd.exe /k start " + path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置默认时区
     */
    @PostConstruct
    void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
    }
}
