package recommand;
import tf_idf.*;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import org.nlpcn.commons.lang.occurrence.Count;

import data.Stockinfo;
import javafx.util.Pair;
import segmenter.*;
import sorter.SorterImpl;
public class RecommandImpl implements Recommand{
	ChineseSegmenterImpl chineseSegmenterImpl=new ChineseSegmenterImpl();
	TF_IDFImpl tf_idfImpl=new TF_IDFImpl();
	SorterImpl sorterImpl=new SorterImpl();
	@Override
	public double[] calculateMatrix(Stockinfo[] stocks, String string) {
		// TODO Auto-generated method stub
		double []matrix=new double[stocks.length];
		List<String>wordList=new LinkedList<>();
    	try {
			wordList=chineseSegmenterImpl.getWordsFromString(string);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	for (int i=0;i<stocks.length;i++) {
    		int count =0;
    		List<String >words=new LinkedList<>();
    		try {
				words=chineseSegmenterImpl.getWordsFromString(stocks[i].getAnswer()+stocks[i].getContent()+stocks[i].getTitle());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		for(String str:wordList)
    			for (String word:words)
    				if(str==word)count ++;
    		matrix[i]=count/(double)words.size();
    	}
		return matrix;
	}
}
