/**
 * 2015年11月3日hanjiewu
 */
package com.wutianyi.stanford;

import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import org.apache.commons.lang.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Properties;

/**
 * @author hanjiewu
 */
public class Usage1 {

	public static void main(String[] args) throws IOException {
		Properties props = new Properties();
		props.load(Usage1.class.getClassLoader().getResourceAsStream("StanfordCoreNLP-chinese.properties"));
		props.setProperty("annotators", "segment,ssplit,pos,ner");
		props.setProperty("segment.verbose", "false");
		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

		String[] texts = new String[] {
				"刘柳先生，您个人信用卡临时额度后天失效， 2015年11月25日15点前回#YLEY可重新申请临额至 {5} 元，短信回复为准。实时查询额度，巧用额度理财，猛戳  。[招商银行]",
				"倪先生您个人信用卡最高可申请临时额度至17000元，为方便用卡2015年08月16日9点前回#TLEY即可，以短信回复为准。实时查询额度，巧用额度理财，猛戳 t.cn/Rw7gdZs 。[招商银行]" };

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String line = br.readLine();
		while (!StringUtils.equals(line, "bye")) {
			long start = System.currentTimeMillis();
			Annotation document = new Annotation(line);
			pipeline.annotate(document);
			List<CoreLabel> coreLabel = document.get(TokensAnnotation.class);
			for (CoreLabel token : coreLabel) {
				String word = token.get(TextAnnotation.class);
				String pos = token.get(PartOfSpeechAnnotation.class);
				String ne = token.getString(NamedEntityTagAnnotation.class);
				System.out.println(word + "\t" + pos + "\t" + ne);
			}
			System.out.println("Use Time: " + (System.currentTimeMillis() - start));
			System.out.println("请输入短信样本:");
			line = br.readLine();
		}

		// Tree tree = sentence.get(TreeAnnotation.class);
		// SemanticGraph dependencies =
		// sentence.get(CollapsedCCProcessedDependenciesAnnotation.class);

		// Map<Integer, CorefChain> graph =
		// document.get(CorefChainAnnotation.class);
	}
}
