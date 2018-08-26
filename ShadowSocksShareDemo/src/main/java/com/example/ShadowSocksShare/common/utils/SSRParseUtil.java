package com.example.ShadowSocksShare.common.utils;

import com.example.ShadowSocksShare.domain.ShadowSocksDetailsEntity;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;

/**
 * Created by zd.yao on 2018/8/26.
 */
public class SSRParseUtil {

    /**
     * 连接解析
     */
    public static ShadowSocksDetailsEntity parseLink(String linkTxt) throws UnsupportedEncodingException {
        String debugLinkTxt=linkTxt;
        linkTxt= StringUtils.removeStartIgnoreCase(linkTxt, "ssr://");
        ShadowSocksDetailsEntity entity=null;
        try{
            //解析后的数据：hk7.xjay.xyz:15132:origin:aes-256-cfb:plain:OTIxMg/?obfsparam=&remarks=QElSQU5TU1IgLSAyMEF1ZyAtIGhrNy54amF5Lnh5eiBTU1I&group=Q2hhcmxlcyBYdQ
            String linkTxtDecodeBase64= null;
            linkTxtDecodeBase64 = new String(org.apache.commons.codec.binary.Base64.decodeBase64(linkTxt),"utf-8");
            String[] ssrInfoArray = StringUtils.split(linkTxtDecodeBase64, ":");
            if(ssrInfoArray.length==0){return null;}
            String server=ssrInfoArray[0];
            Integer serverPort=Integer.parseInt(ssrInfoArray[1]);
            String protocol=ssrInfoArray[2];
            String method=ssrInfoArray[3];
            String obfs=ssrInfoArray[4];
            //密码包含在最后的ssrInfoArray[5]中。
            String paramInfo=ssrInfoArray[5];
            String otherInfo=paramInfo;
            int end=otherInfo.indexOf("/?")==-1?otherInfo.length()-1:otherInfo.indexOf("/");
            otherInfo=otherInfo.substring(0,end);
            String otherInfoDecodeBase64=decodeBase64(otherInfo);
            String password=otherInfoDecodeBase64;
            entity=new ShadowSocksDetailsEntity(server,serverPort,password,method,protocol,obfs);
            //
            // 协议参数
            String protoparam=CrawlerUtil.getTextByRegex(paramInfo,"protoparam=([\\w]*)");
            entity.setProtoparam(decodeBase64(protoparam));
            // 混淆参数
            String obfsparam=CrawlerUtil.getTextByRegex(paramInfo,"obfsparam=([\\w]*)");
            entity.setObfsparam(decodeBase64(obfsparam));
        } catch (Exception e) {
            throw new RuntimeException("SSR 连接[" + linkTxt + "]解析异常：" + e.getMessage(), e);
        }
        return entity;
    }
    private static String decodeBase64(String txt){
        if(txt==null||txt.isEmpty()){return "";}
        try {
            return new String(org.apache.commons.codec.binary.Base64.decodeBase64(txt),"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
