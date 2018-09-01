package com.example.ShadowSocksShare.common.utils;

/**
 * Created by zd.yao on 2018/9/1.
 */
public class DosUtil {
    public static void openHttp(String path) {
        //String path = "http://localhost:28080/";
        Runtime run = Runtime.getRuntime();
        try {
            Process process = run.exec("cmd.exe /k start " + path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
