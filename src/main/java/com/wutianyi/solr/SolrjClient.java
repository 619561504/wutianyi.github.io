package com.wutianyi.solr;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

import java.io.IOException;
import java.util.Map;

/**
 * Created by hanjiewu on 2016/7/26.
 */
public class SolrjClient {
    public static void main(String[] args) throws IOException, SolrServerException {
        String urlString = "http://localhost:8983/solr/sms_core";
        SolrClient solrClient = new HttpSolrClient.Builder(urlString).build();

        SolrQuery query = new SolrQuery();
        query.setQuery("phoneName:腾讯科技");
        query.setSort("count", SolrQuery.ORDER.desc);
//        query.setFilterQueries()
        QueryResponse response = solrClient.query(query);
        SolrDocumentList list = response.getResults();
        System.out.printf("Found: %d\n", list.getNumFound());
        for (SolrDocument solrDocument : list) {
            for (Map.Entry<String, Object> entry : solrDocument) {
//                System.out.printf("%s:%s\n", entry.getKey(), entry.getValue());
            }
        }
    }
}
