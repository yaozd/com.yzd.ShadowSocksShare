package com.example.ShadowSocksShare;

import com.example.ShadowSocksShare.common.utils.DosUtil;
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
        DosUtil.openHttp("http://localhost:28080/");
        DosUtil.openHttp("http://localhost:28080/run/all");
    }
    /**
     * 设置默认时区
     */
    @PostConstruct
    void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
    }
}
