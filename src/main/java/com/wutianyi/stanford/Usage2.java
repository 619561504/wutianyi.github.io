/**
 * 2015年11月3日hanjiewu
 */
package com.wutianyi.stanford;

import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.ChineseSegmenterAnnotator;

import java.io.IOException;
import java.util.Properties;

/**
 * @author hanjiewu
 *
 */
public class Usage2 {
	public static void main(String[] args) throws IOException {
		Properties props = new Properties();
		props.load(Usage1.class.getClassLoader().getResourceAsStream("StanfordCoreNLP-chinese.properties"));
		ChineseSegmenterAnnotator cAnnotator = new ChineseSegmenterAnnotator("segment", props);
		String text = "您尾号 {1} 的[*n*6]卡白金卡信用额度为 {2} 元人民币或等值外币，当前可用额度为 {3} 元人民币或等值外币。 回复“应还”可查当前欠款。微信也可查询账户信息啦，赶快关注“中国光大银行”[光大银行]";
		Annotation document = new Annotation(text);
		cAnnotator.annotate(document);
		for (CoreLabel coreLabel : document.get(TokensAnnotation.class)) {
			String word = coreLabel.get(TextAnnotation.class);
			System.out.println(word);
		}
	}
}
