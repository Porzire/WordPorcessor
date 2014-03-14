package nlp.wordprocessor;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

public class StanfordWordProcessor
        extends AbstractWordProcessor {

    private StanfordCoreNLP pipeline;

    public StanfordWordProcessor(File inFile, File outFile) 
            throws IOException {
        super(inFile, outFile);
        Properties props;
        props = new Properties();
        props.put("annotators", "tokenize, ssplit, pos, lemma");
        this.pipeline = new StanfordCoreNLP(props);
    }

    public String process(String token) {
        List<String> lemmas = new LinkedList<String>();
        Annotation document = new Annotation(token);
        this.pipeline.annotate(document);
        List<CoreMap> sentences = document.get(SentencesAnnotation.class);
        for(CoreMap sentence: sentences) {
            for (CoreLabel t: sentence.get(TokensAnnotation.class)) {
                lemmas.add(t.get(LemmaAnnotation.class));
            }
        }
        return lemmas.get(0);
    }
    
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("[Usage] StanfordWordProcessor inFile outFile");
            System.out.println(args.length);
            System.exit(1);
        }
        try {
            new StanfordWordProcessor(new File(args[0]), new File(args[1])).writeOutput();
        } catch(IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
