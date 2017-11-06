package com.wutianyi.stanford;

import com.google.api.client.util.Sets;
import com.google.common.base.Joiner;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 * Created by hanjiewu on 2017/7/12.
 */
public class Usage3 {

	public static void main(String[] args) throws IOException {
		Properties props = new Properties();
		props.load(Usage1.class.getClassLoader().getResourceAsStream("StanfordCoreNLP-chinese.properties"));
		props.setProperty("annotators", "segment,ssplit,pos,ner");
		props.setProperty("segment.verbose", "false");
		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

		String[] texts = new String[]{
				"刘柳先生，您个人信用卡临时额度后天失效， 2015年11月25日15点前回#YLEY可重新申请临额至 {5} 元，短信回复为准。实时查询额度，巧用额度理财，猛戳  。[招商银行]",
				"倪先生您个人信用卡最高可申请临时额度至17000元，为方便用卡2015年08月16日9点前回#TLEY即可，以短信回复为准。实时查询额度，巧用额度理财，猛戳 t.cn/Rw7gdZs 。[招商银行]"};
		//PrintWriter pw = new PrintWriter("F:\\work\\需求\\广点通\\spa文本词库样本\\remove");
		PrintWriter pw2 = new PrintWriter("F:\\work\\需求\\广点通\\spa文本词库样本\\keyword_sample_fix_2");
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("F:\\work\\需求\\广点通\\spa文本词库样本\\keyword_sample_fix")));
		String line = br.readLine();
		long start = System.currentTimeMillis();
		int lines = 0;
		while (null != line) {
			String[] vs = line.split("\t");
			Annotation document = new Annotation(vs[1]);
			pipeline.annotate(document);
			List<CoreLabel> coreLabel = document.get(CoreAnnotations.TokensAnnotation.class);

			boolean isOutput = false;
			Set<String> words = Sets.newHashSet();
			for (CoreLabel token : coreLabel) {
				String word = token.get(CoreAnnotations.TextAnnotation.class);
				String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
				String ne = token.getString(CoreAnnotations.NamedEntityTagAnnotation.class);
				//System.out.println(word + "\t" + pos + "\t" + ne);
				if (StringUtils.equals(pos, "NR") && StringUtils.equals(ne, "O")) {
					words.add(word);
				}
			}
			if (isOutput) {
				//pw.println(line);
			} else {

			}
			String w = vs[0];
			String l = vs[1];
			if (words.size() > 0) {

				for (String word : words) {
					if (StringUtils.contains(w, word) || StringUtils.contains(word, w)) {

					} else {
						try {
							l = l.replaceAll(word, "[*n*]");
						} catch (Exception e) {

						}
					}

				}
			}
			pw2.println(Joiner.on('\t').join(w, l, vs[2]));
			//System.out.println("Use Time: " + (System.currentTimeMillis() - start));
			//System.out.println("请输入短信样本:");
			++lines;
			if (lines % 1000 == 0) {
				System.out.printf("Lines: %d, Use Time: %d\n", lines, (System.currentTimeMillis() - start));
			}
			line = br.readLine();
		}
		//pw.close();
		pw2.close();
	}
}
