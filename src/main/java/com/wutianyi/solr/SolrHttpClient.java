package com.wutianyi.solr;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by hanjiewu on 2016/7/26.
 */
public class SolrHttpClient {
    public static void main(String[] args) throws IOException {
        HttpClient httpClient = HttpClients.custom().build();
        Gson gson = new Gson();
        HttpGet httpGet = new HttpGet("http://localhost:8983/solr/sms_core/select?indent=on&q=phoneName%3A%E8%85%BE%E8%AE%AF%E7%A7%91%E6%8A%80*&wt=json&omitHeader=true");
        String response = EntityUtils.toString(httpClient.execute(httpGet).getEntity());
        Map<String, Object> rets = gson.fromJson(response, new TypeToken<Map<String, Object>>() {
        }.getType());
        List<Map<String, Object>> docs = (List<Map<String, Object>>) ((Map<String, Object>) rets.get("response")).get("docs");
        System.out.println(gson.toJson(docs));
    }
}
