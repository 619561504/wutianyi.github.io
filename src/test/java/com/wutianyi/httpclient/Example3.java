package com.wutianyi.httpclient;

import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.junit.Test;

/**
 * Created by hanjiewu on 2016/4/8.
 */
public class Example3 {

    @Test
    public void example() {
        BasicClientCookie cookie = new BasicClientCookie("name", "value");
        cookie.setVersion(1);
        cookie.setDomain(".yeetrack.com");
        cookie.setSecure(true);
    }

    @Test
    public void example2() {
        RequestConfig globalConfig = RequestConfig.custom()
                .setCookieSpec(CookieSpecs.BEST_MATCH).build();
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(globalConfig).build();

        RequestConfig localConfig = RequestConfig.copy(globalConfig)
                .setCookieSpec(CookieSpecs.DEFAULT).build();
        HttpGet httpGet = new HttpGet("http://baidu.com");
        httpGet.setConfig(localConfig);
    }

    @Test
    public void example3() {
        CookieStore cookieStore = new BasicCookieStore();
        BasicClientCookie cookie = new BasicClientCookie("name", "value");
        cookieStore.addCookie(cookie);

        CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
    }
}
