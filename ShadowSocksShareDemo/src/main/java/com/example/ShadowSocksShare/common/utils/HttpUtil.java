package com.example.ShadowSocksShare.common.utils;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zd.yao on 2018/8/25.
 */
public class HttpUtil {
    public static String sendGet2(String url, Map<String, String> params,Map<String, String> headMap) {
        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>(params.size());
        for (String key : params.keySet()) {
            urlParameters.add(new BasicNameValuePair(key, params.get(key)));
        }
        //
        String response = null;
        try {
            URI uri = new URIBuilder(url).addParameters(urlParameters).build();
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(uri);
            httpGet.addHeader("User-Agent", "Mozilla/5.0");
            for (String key : headMap.keySet()) {
                httpGet.addHeader(key, headMap.get(key));
            }
            httpGet.addHeader("Host", "sharefanqiang.herokuapp.com");
            CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();
            response = EntityUtils.toString(httpEntity, StandardCharsets.UTF_8);
            httpClient.close();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        return response.toString();
    }
}
