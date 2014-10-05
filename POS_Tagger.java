import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.List;
import java.util.ArrayList;

//import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.process.TokenizerFactory;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;


class PosTagger3 {

  private PosTagger3() {}

  public static void main(String[] args) throws Exception {
	  
	  	ArrayList<String> stopWords = new ArrayList<String>();
		stopWords.add("a");
		stopWords.add("be");
		stopWords.add("by");
		stopWords.add("how");
		stopWords.add("in");
		stopWords.add("is");
		stopWords.add("it");
		stopWords.add("of");
		stopWords.add("on");
		stopWords.add("or");
		stopWords.add("that");
		stopWords.add("the");
		stopWords.add("this");
		stopWords.add("to");
		stopWords.add("why");
		//stopWords.add("spirituality");
		//stopWords.add("fraternity");
    
	String tstr = "..\\data\\input.txt";
    String mfile = "C:\\dev\\stanford-postagger-full-2014-01-04\\models\\english-left3words-distsim.tagger";
    OutputStream wrt = new FileOutputStream("..\\data\\output.txt");
    
    MaxentTagger tagger = new MaxentTagger(mfile);
    TokenizerFactory<CoreLabel> ptbTokenizerFactory = PTBTokenizer.factory(new CoreLabelTokenFactory(),
									   "untokenizable=allDelete");
    BufferedReader r = new BufferedReader(new InputStreamReader(new FileInputStream(tstr), "utf-8"));
    PrintWriter pw = new PrintWriter(new OutputStreamWriter(wrt, "utf-8"));
    String line,tokens[],note,upc,tags;
    int j=0;
    while ((line = r.readLine()) != null)
    {
    	tokens = line.split("\t");
    	upc	   = tokens[0];
    	note   = tokens[1];
    	tags   = tokens[2];
    	
    	StringReader rr = new StringReader(tags);
    	//tokens = rr.split('~')
        j++;
    DocumentPreprocessor documentPreprocessor = new DocumentPreprocessor(rr);
    documentPreprocessor.setTokenizerFactory(ptbTokenizerFactory);

    for (List<HasWord> sentence : documentPreprocessor) {

      List<TaggedWord> tSentence = tagger.tagSentence(sentence);
      for (int i=0; i<tSentence.size(); i++) 
      {
    	  if (!stopWords.contains(tSentence.get(i).value().toLowerCase())) {
    		  System.out.println(upc +","+tSentence.get(i).value()+","+tSentence.get(i).tag());
    	     pw.println(upc +"\t"+tSentence.get(i).value()+"\t"+ tSentence.get(i).tag());
    	  }
      }
//      pw.println(Sentence.listToString(tSentence, false));
//      System.out.println(Sentence.listToString(tSentence, false));
    }

    }
    pw.close();
    r.close();
  }

}