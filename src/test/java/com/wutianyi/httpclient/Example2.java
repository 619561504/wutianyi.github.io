package com.wutianyi.httpclient;

import org.apache.http.HttpClientConnection;
import org.apache.http.HttpHost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ConnectionRequest;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by hanjiewu on 2016/4/8.
 */
public class Example2 {

    @Test
    public void example() throws InterruptedException, ExecutionException, IOException {
        HttpClientContext context = HttpClientContext.create();
        HttpClientConnectionManager connMrg = new BasicHttpClientConnectionManager();
        HttpRoute route = new HttpRoute(new HttpHost("www.baidu.com", 80));

        ConnectionRequest connRequest = connMrg.requestConnection(route, null);
        HttpClientConnection conn = connRequest.get(10, TimeUnit.SECONDS);
        if (conn.isOpen()) {
            connMrg.connect(conn, route, 1000, context);
            connMrg.routeComplete(conn, route, context);
        }
        connMrg.releaseConnection(conn, null, 1, TimeUnit.SECONDS);
    }

    @Test
    public void example2() {
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(200);
        cm.setDefaultMaxPerRoute(20);
        HttpHost httpHost = new HttpHost("www.baidu.com", 80);
        cm.setMaxPerRoute(new HttpRoute(httpHost), 50);


        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();
    }

    @Test
    /**
     * 设置代理服务器
     */
    public void example3() {
        HttpHost proxy = new HttpHost("", 8080);
        DefaultProxyRoutePlanner planner = new DefaultProxyRoutePlanner(proxy);

        CloseableHttpClient httpClient = HttpClients.custom().setRoutePlanner(planner).build();
    }
}
