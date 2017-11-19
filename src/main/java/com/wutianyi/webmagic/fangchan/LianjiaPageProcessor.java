package com.wutianyi.webmagic.fangchan;

import com.google.api.client.repackaged.com.google.common.base.Joiner;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.FileCacheQueueScheduler;
import us.codecraft.webmagic.selector.Html;

/**
 * Created by hanjiewu on 2016/7/20.
 */
public class LianjiaPageProcessor implements PageProcessor {
    public static final String HTTP_GZ_LIANJIA_COM_ERSHOUFANG_PG3CO32 = "http://gz.lianjia.com/ershoufang/pg3co32/";
    private Site site;

    public LianjiaPageProcessor() {
        site = Site.me().setRetryTimes(3).setSleepTime(100).setUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.1 (KHTML, like Gecko) Chrome/21.0.1180.89 Safari/537.1 QIHU 360SE");
    }

    @Override
    public void process(Page page) {
        if (page.getUrl().regex("http://gz.lianjia.com/ershoufang/.*co32/").match()) {
            page.addTargetRequests(page.getHtml().links().regex("http://gz.lianjia.com/ershoufang/GZ[0-9]*.html").all());
            return;
        }
        if (page.getUrl().regex("http://gz.lianjia.com/ershoufang/GZ[0-9]*.html").match()) {
            Html html = page.getHtml();
            String info = html.css("div.aroundInfo").xpath("//a[@class='info']/text()").toString();
            System.out.println(info);
//            String communityName = html.xpath("//div[@class='communityName']/a[@class='info']/text()").toString();//小区名称
//            String areaName = Joiner.on('-').join(html.xpath("//div[@class='areaName']/span[@class='info']/a/text()").all());//区域
//            String totalPrice = html.xpath("//div[@class='price']/div/span[@class='total']/text()").toString();//总价
//            String unitPrice = html.xpath("//div[@class='unitPrice']/span/text()").toString();//单价
//            String roomMainInfo = html.xpath("//div[@class='room']/div[@class='mainInfo']/text()").toString();//户型
//            String roomSubInfo = html.xpath("//div[@class='room]/div[@class='subInfo']/text()").toString();//楼层
//            String areaMainInfo = html.xpath("//div[@class='area']/div[@class='mainInfo']/text()").toString();//大小
//            String areaSubInfo = html.xpath("//div[@class='area']/div[@class='subInfo']/text()").toString();//建造年代
//            String typeMainInfo = html.xpath("//div[@class='type]/div[@class='mainInfo']/text()").toString();//朝向
//
//            String zhx = html.xpath("//div[@class='base']/div[@class='content']/ul/li/text()").all().get(8);//装修
//            String use = html.xpath("//div[@class='transaction']/div[@class='content']/ul/li/text()").all().get(7);//房屋用途
//            String houseYear = html.xpath("//div[@class='transaction']/div[@class='content']/ul/li/text()").all().get(4);//房屋年限
//            String unique = html.xpath("//div[@class='transaction']/div[@class='content']/ul/li/text()").all().get(6);//是否唯一
//            String phone = html.xpath("//div[@class='brokerInfoText']/div[@class='phone']/text()").toString();//中介电话
//            String publishTime = html.xpath("//div[@class='transaction']/div[@class='content']/ul/li/text()").all().get(0);//挂牌时间

        }

    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new LianjiaPageProcessor()).test("http://gz.lianjia.com/ershoufang/GZ0001812350.html");
//        Spider.create(new LianjiaPageProcessor()).addUrl(new String[]{"http://gz.lianjia.com/ershoufang/co32/",
//                "http://gz.lianjia.com/ershoufang/pg2co32/", HTTP_GZ_LIANJIA_COM_ERSHOUFANG_PG3CO32,
//                "http://gz.lianjia.com/ershoufang/pg4co32/", "http://gz.lianjia.com/ershoufang/pg5co32/"}).setScheduler(new FileCacheQueueScheduler("data/lianjia/scheduler"))
//                .addPipeline(new JsonFilePipeline()).thread(5).run();
    }
}
