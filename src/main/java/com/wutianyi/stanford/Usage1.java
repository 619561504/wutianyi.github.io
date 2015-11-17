/**
 * 2015年11月3日hanjiewu
 */
package com.wutianyi.stanford;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import edu.stanford.nlp.dcoref.CorefChain;
import edu.stanford.nlp.dcoref.CorefCoreAnnotations.CorefChainAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.SentenceAnnotator;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation;
import edu.stanford.nlp.util.CoreMap;

/**
 * @author hanjiewu
 */
public class Usage1 {

    public static void main(String[] args) throws IOException {
        Properties props = new Properties();
        props.load(Usage1.class.getClassLoader().getResourceAsStream("StanfordCoreNLP-chinese.properties"));
        props.setProperty("annotators", "segment,ssplit,pos, ner");
        props.setProperty("segment.verbose", "false");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        String text = "刘柳先生，您个人信用卡临时额度后天失效， 2015年11月25日15点前回#YLEY可重新申请临额至 {5} 元，短信回复为准。实时查询额度，巧用额度理财，猛戳  。[招商银行]";
        Annotation document = new Annotation(text);
        long start = System.currentTimeMillis();
        pipeline.annotate(document);
        System.out.println("Finish: " + (System.currentTimeMillis() - start));
        start = System.currentTimeMillis();
        List<CoreLabel> coreLabel = document.get(TokensAnnotation.class);
        for (CoreLabel token : coreLabel) {
            String word = token.get(TextAnnotation.class);
            // String pos = token.get(PartOfSpeechAnnotation.class);
            // String ne = token.getString(NamedEntityTagAnnotation.class);
            // System.out.println(word + "\t" + pos + "\t" + ne);j
            System.out.println(word);
        }
        System.out.println("null");
        System.out.println("Use Time: " + (System.currentTimeMillis() - start));
        // Tree tree = sentence.get(TreeAnnotation.class);
        // SemanticGraph dependencies =
        // sentence.get(CollapsedCCProcessedDependenciesAnnotation.class);

        // Map<Integer, CorefChain> graph =
        // document.get(CorefChainAnnotation.class);
    }
}
