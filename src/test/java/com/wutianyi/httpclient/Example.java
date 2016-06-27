package com.wutianyi.httpclient;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.math3.ml.neuralnet.FeatureInitializer;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.*;
import java.security.cert.CertificateException;

/**
 * Created by hanjiewu on 2016/4/7.
 */
public class Example {
	@Test
	public void example10() {
		System.out.println(Integer.toString(100000000));
	}

	@Test
	public void testSSL2() throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException, KeyManagementException, UnrecoverableKeyException {
		HttpClient httpClient = new DefaultHttpClient();
		KeyStore keyStore = KeyStore.getInstance("PKCS12");
		FileInputStream inputStream = new FileInputStream("F:\\work\\黑板\\to_tx\\client.key.p12");
		keyStore.load(inputStream, "GH*pm[qG".toCharArray());
		inputStream.close();
		KeyStore trustStore = KeyStore.getInstance("jks");
		inputStream = new FileInputStream(new File("F:\\work\\黑板\\to_tx\\client.truststore"));
		trustStore.load(inputStream, "123456".toCharArray());
		inputStream.close();
		SSLSocketFactory socketFactory = new SSLSocketFactory(keyStore, "GH*pm[qG", trustStore);
		Scheme sch = new Scheme("https", 8443, socketFactory);
		httpClient.getConnectionManager().getSchemeRegistry().register(sch);
		httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, new HttpHost("web-proxy.oa.com", 8080));
		HttpPost httpPost = new HttpPost("https://api.tya.company:8443/wifi/blacklist");
		String info = "{\n" +
				"  \"version\":\"1.0\",\n" +
				"  \"content\": [\n" +
				"    \"00:00:00:00:00:03\",\n" +
				"    \"00:00:00:00:00:0B\",\n" +
				"\"00:01:09:F9:93:02\",\n" +
				"\"10:C6:1F:2A:3A:D9\",\n" +
				"\"10:C6:1F:C6:77:B5\"\n" +
				"  ]\n" +
				"}\n";
		httpPost.setEntity(new ByteArrayEntity(info.getBytes()));
		HttpResponse response = httpClient.execute(httpPost);
		String ret = EntityUtils.toString(response.getEntity());
		System.out.println(ret);
	}



	@Test
	public void testSSl() throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException, KeyManagementException, UnrecoverableKeyException {

		KeyStore keyStore = KeyStore.getInstance("PKCS12");
		FileInputStream inputStream = new FileInputStream("F:\\work\\黑板\\to_tx\\client.key.p12");
		keyStore.load(inputStream, "GH*pm[qG".toCharArray());
		inputStream.close();

		KeyStore trustStore = KeyStore.getInstance("jks");
		inputStream = new FileInputStream(new File("F:\\work\\黑板\\to_tx\\client.truststore"));
		trustStore.load(inputStream, "123456".toCharArray());
		inputStream.close();
		//System.setProperty("javax.net.ssl.trustStore", "F:\\work\\黑板\\to_tx\\client.truststore");
		SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(trustStore, new TrustSelfSignedStrategy()).loadKeyMaterial(keyStore, "GH*pm[qG".toCharArray()).build();

		SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslContext);

		CloseableHttpClient httpClient = HttpClients.custom().setProxy(new HttpHost("web-proxy.oa.com", 8080)).setSSLSocketFactory(socketFactory).build();

		HttpPost httpPost = new HttpPost("https://api.tya.company:8443/wifi/blacklist");

		httpPost.addHeader("gzip", StringUtils.EMPTY);
		String info = "{\n" +
				"  \"version\":\"1.0\",\n" +
				"  \"content\": [\n" +
				"    \"00:00:00:00:00:03\",\n" +
				"    \"00:00:00:00:00:0B\",\n" +
				"\"00:01:09:F9:93:02\",\n" +
				"\"10:C6:1F:2A:3A:D9\",\n" +
				"\"10:C6:1F:C6:77:B5\"\n" +
				"  ]\n" +
				"}\n";
		httpPost.setEntity(new ByteArrayEntity(info.getBytes()));
		HttpResponse response = httpClient.execute(httpPost);
		String ret = EntityUtils.toString(response.getEntity());
		System.out.println(ret);
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
