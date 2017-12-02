package tf_idf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import data.Stockinfo;
import javafx.util.Pair;

public class TF_IDFImpl implements TF_IDF{

	@Override
	public Pair<String, Double>[] getResult(List<String> words, Stockinfo[] stockInfos) {
		// TODO Auto-generated method stub
		HashMap<String , Double >mapTF=new HashMap<>();
    	HashMap<String , Double>mapTF_IDF=new HashMap<>();
    	for (String str:words) {
    		double value=1.0/(double)words.size();
    		if(mapTF.get(str)!=null)
    		{
    			value=mapTF.get(str)+1.0/(double)words.size();
    		}
    		mapTF.put(str, value);
    	}
    	int count =0;
    	for (String key:words) {
    		if (mapTF_IDF.get(key)!=null)
    			continue;
    		for (int i=0;i<stockInfos.length;i++) {
    			if (stockInfos[i].getContent().contains(key)) {
    				count++;
    				}
    			if (stockInfos[i].getTitle().contains(key)) {
    				count++;
    				}
    			if (stockInfos[i].getAnswer().contains(key)) {
    				count++;
    				}
    			}
    		double value=Math.log((double)stockInfos.length/(count+1));
    		mapTF_IDF.put(key, value*mapTF.get(key));
    	}
        //TODO: write your code here9
    	Pair<String , Double>[]pairs=new Pair[mapTF_IDF.size()];
    	int n=0;
    	for (Map.Entry<String, Double>entry:mapTF_IDF.entrySet()) {
    		Pair<String,Double >pair=new Pair<String, Double>(entry.getKey(), entry.getValue());
    		pairs[n]=pair;
    		n++;
    	}
        return pairs;
	}

}
