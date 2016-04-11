package com.wutianyi.httpclient;

import org.apache.commons.math3.ml.neuralnet.FeatureInitializer;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHttpResponse;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

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
}
