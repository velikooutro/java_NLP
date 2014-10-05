import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.util.Version;

public class AnalyzerDemo8 {
public static void main(String[] args) throws IOException {
	
			StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_36);
			BufferedReader br = new BufferedReader(new FileReader("..\\data\\input.txt"));
			
			ArrayList<String> list = new ArrayList<String>();
			
		  String line,tokens[],note,upc;
		  while ((line = br.readLine()) != null)
		  {
			tokens = line.split("~");
			
			upc	   = tokens[0];
	    	note   = tokens[1];

	    	//Reader r = new StringReader(line);
	    	Reader r = new StringReader(note);
	    	TokenStream ts = analyzer.tokenStream("", r);
			
			CharTermAttribute termAttr = ts.addAttribute(CharTermAttribute.class);
			ts.reset();

		    BufferedWriter xx = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("..\\data\\output.txt"),"UTF-8"));
			
		    	 while(ts.incrementToken()) {
					list.add(upc + "\t" + termAttr.toString());
					 for(String s: list) {
						 System.out.println(s);
						 xx.write(s.toString() + "\n");
					}
			}    
			 ts.end();
			 ts.close();
			 xx.flush();
			 xx.close();
			 
		 }
		  br.close();
	}
}
