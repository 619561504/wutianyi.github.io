/**
 * hanjiewu 
 * 2015年12月15日:下午2:54:46
 */
package com.wutianyi.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;

/**
 * @author hanjiewu
 *
 */
public class BaiJiaXing {

	public static void main(String[] args) throws IOException {
		Document doc = Jsoup.parse(new File("baijiaxing.html"), "utf-8");
		Elements elements = doc.select("#bjx > a");
		for (Element element : elements) {
			System.out.println(element.text());
		}
	}
}
