package com.wutianyi.httpclient;

import com.google.api.client.util.Lists;
import com.google.common.base.Charsets;
import org.apache.http.*;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by hanjiewu on 2016/4/7.
 */
public class Example {
	@Test
	public void example10() {
		System.out.println(Integer.toString(100000000));
	}

    @Test
    public void example() {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://qq.com");
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void example2() throws URISyntaxException {
        URI uri = new URIBuilder().setScheme("http").setHost("www.google.com").setParameter("q", "httpclient").build();
        HttpGet httpGet = new HttpGet(uri);
        System.out.println(httpGet.getURI());
    }

    @Test
    public void exmaple3() {
        HttpResponse httpResponse = new BasicHttpResponse(HttpVersion.HTTP_1_1, HttpStatus.SC_OK, "OK");
        System.out.println(httpResponse.getProtocolVersion());
        System.out.println(httpResponse.getStatusLine().getStatusCode());
        System.out.println(httpResponse.getStatusLine().getReasonPhrase());
        System.out.println(httpResponse.getStatusLine().toString());
        httpResponse.setHeader("Set-Cookie", "");
        httpResponse.getHeaders("Set-Cookie");
        httpResponse.headerIterator();
    }

    @Test
    public void example4() throws IOException {
        StringEntity stringEntity = new StringEntity("import message", ContentType.create("text/html", Charsets.UTF_8));
        System.out.println(stringEntity.getContentType());
        System.out.println(stringEntity.getContentLength());
        System.out.println(EntityUtils.toByteArray(stringEntity).length);
    }

    /**
     *
     */
    @Test
    public void example5() {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://baidu.com");
        CloseableHttpResponse httpResponse = null;
        try {
            httpResponse = httpClient.execute(httpGet);
            HttpEntity entity = httpResponse.getEntity();
            if (null != entity) {
                InputStream inputStream = entity.getContent();
                try {

                } finally {
                    inputStream.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpResponse.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 提交文件信息
     */
    @Test
    public void example6() {
        File file = new File("somefile.txt");
        FileEntity fileEntity = new FileEntity(file, ContentType.TEXT_PLAIN);
        HttpPost httpPost = new HttpPost("");
        httpPost.setEntity(fileEntity);
    }

    /**
     * 提交表单信息
     */
    @Test
    public void example7() throws UnsupportedEncodingException {
        List<NameValuePair> formparams = Lists.newArrayList();
        formparams.add(new BasicNameValuePair("param1", "value1"));
        formparams.add(new BasicNameValuePair("param2", "value2"));
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams);

        HttpPost httpPost = new HttpPost("");
        httpPost.setEntity(entity);
    }

    @Test
    public void example8() {
        ConnectionKeepAliveStrategy connectionKeepAliveStrategy = new DefaultConnectionKeepAliveStrategy() {
            @Override
            public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
                long keepAlive = super.getKeepAliveDuration(response, context);
                if (keepAlive == -1) {
                    keepAlive = 5000;
                }
                return keepAlive;
            }
        };
        CloseableHttpClient httpClient = HttpClients.custom().setKeepAliveStrategy(connectionKeepAliveStrategy).build();
    }

    @Test
    public void example9() {
        CloseableHttpClient httpClient = HttpClients.custom().addInterceptorLast(new HttpRequestInterceptor() {
            @Override
            public void process(HttpRequest httpRequest, HttpContext httpContext) throws HttpException, IOException {
                AtomicInteger count = (AtomicInteger) httpContext.getAttribute("count");
                httpRequest.addHeader("Count", Integer.toString(count.getAndIncrement()));
            }
        }).build();

        AtomicInteger count = new AtomicInteger(1);
        HttpClientContext localContext = HttpClientContext.create();
        localContext.setAttribute("count", count);

        HttpGet httpGet = new HttpGet("http://baidu.com");
        for (int i = 0; i < 10; i++) {
            CloseableHttpResponse response = null;
            try {
                response = httpClient.execute(httpGet, localContext);
                HttpEntity entity = response.getEntity();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    /**
     * 获取重定向的url
     */
    @Test
    public void example10() {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpClientContext context = HttpClientContext.create();

        HttpGet httpGet = new HttpGet("");
        CloseableHttpResponse httpResponse = null;
        try {
            httpResponse = httpClient.execute(httpGet, context);
            HttpHost target = context.getTargetHost();
            List<URI> redirectLocations = context.getRedirectLocations();
            URI location = URIUtils.resolve(httpGet.getURI(), target, redirectLocations);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } finally {
            try {
                httpResponse.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
