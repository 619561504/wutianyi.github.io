/**
 * 2015年10月23日hanjiewu
 */
package com.wutianyi.hanlp;

import java.util.List;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.Dijkstra.DijkstraSegment;
import com.hankcs.hanlp.seg.NShort.NShortSegment;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.IndexTokenizer;
import com.hankcs.hanlp.tokenizer.NLPTokenizer;
import com.hankcs.hanlp.tokenizer.StandardTokenizer;

/**
 * @author hanjiewu
 *
 */
public class Example {
	public static void main(String[] args) {
		System.out.println(HanLP.segment("你好，欢迎使用HanLP汉语处理包！"));
		// 标准粉刺
		List<Term> terms = StandardTokenizer.segment("商品和服务");
		System.out.println(terms);
		// 会执行全部的命名实体识别
		terms = NLPTokenizer.segment("中国科学院计算技术研究所的宗成庆教授正在教授自然语言处理课程");
		System.out.println(terms);

		// 索引分词，是面向搜索引擎的分词器，能够对长词全切分
		terms = IndexTokenizer.segment("主副食品");
		System.out.println(terms);

		// n最短路径和最短路径分词
		Segment nShortSegment = new NShortSegment().enableCustomDictionary(false).enablePlaceRecognize(true)
				.enableOrganizationRecognize(true);
		Segment shortestSegment = new DijkstraSegment().enableCustomDictionary(false).enablePlaceRecognize(true)
				.enableOrganizationRecognize(true);

		String[] testCase = new String[] { "今天，刘志军案的关键人物,山西女商人丁书苗在市二中院出庭受审。", "刘喜杰石国祥会见吴亚琴先进事迹报告团成员", };

		for (String sentence : testCase) {
			System.out.println("N-最短分词：" + nShortSegment.seg(sentence) + "\n最短分词：" + shortestSegment.seg(sentence));
		}
	}
}
