package com.wutianyi.solr;

import com.google.api.client.util.Lists;
import org.apache.commons.math3.util.Pair;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by hanjiewu on 2016/7/29.
 */
public class Hightlight {

    public static String hightlight(String source, String[] keywords, String prefix, String suffix) {
        List<Pair<Integer, Integer>> positions = Lists.newArrayList();
        for (String keyword : keywords) {
            int index = source.indexOf(keyword);
            if (-1 != index) {
                positions.add(new Pair<Integer, Integer>(index, index + keyword.length()));
            }
        }
        Collections.sort(positions, new Comparator<Pair<Integer, Integer>>() {
            @Override
            public int compare(Pair<Integer, Integer> o1, Pair<Integer, Integer> o2) {
                return o1.getFirst().compareTo(o2.getFirst());
            }
        });
        List<Pair<Integer, Integer>> hightlights = Lists.newArrayList();
        Pair<Integer, Integer> lastPosition = null;
        for (Pair<Integer, Integer> position : positions) {
            if (null == lastPosition) {
                lastPosition = position;
            } else {
                if (position.getFirst() > lastPosition.getSecond()) {
                    hightlights.add(lastPosition);
                    lastPosition = position;
                } else {
                    lastPosition = new Pair<Integer, Integer>(lastPosition.getFirst(), position.getSecond());
                }
            }
        }
        hightlights.add(lastPosition);
        StringBuilder builder = new StringBuilder();
        int s = 0;
        for (Pair<Integer, Integer> position : hightlights) {
            builder.append(source.substring(s, position.getFirst()));
            builder.append(prefix);
            builder.append(source.substring(position.getFirst(), position.getSecond()));
            builder.append(suffix);
            s = position.getSecond();
        }
        if (s != source.length()) {
            builder.append(source.substring(s));
        }
        return builder.toString();
    }

    public static void main(String[] args) {

        String source = "广州12345”微信公众号已运营三个月啦，感谢各位市民的支持与厚爱！拿起手机，通过微信关注“广州12345”微信公众号，就可以查阅相关政府服务信息、向微信客服咨询相关业务信息和反映其诉求啦！【广州政务】";
        System.out.println(hightlight(source, new String[]{"广州","微信公众号", "已运营", "支持", "持与厚爱", "手机"}, "<font>", "</font>"));
        source = "温馨提示，截止07月10日24时，您当月累计使用共享流量17.4MB。其中国内流量月包已使用17.4MB，剩余1.981GB； 登录联通网上营业厅www.10010.com，查询、交费、充值、方便又实惠!";
        System.out.println(hightlight(source, new String[]{"温馨提示"}, "<font>", "</font>"));
    }
}
