package sorter;

import java.util.LinkedList;
import java.util.List;

import javafx.util.Pair;

public class SorterImpl implements Sorter{
	public List<String> getKeyWords(Pair<String,Double>[] pairs){
		List<String>wordsList=new LinkedList<>();
		Pair<String ,Double>temp=null;
		for(int i=0;i<pairs.length;i++) {
			for (int j=i;j<pairs.length;j++) {
				if (pairs[i].getValue()<pairs[j].getValue()) {
					temp=pairs[i];
					pairs[i]=pairs[j];
					pairs[j]=temp;
				}
			}
		}
		for(int i=0;i<20;i++)wordsList.add(pairs[i].getKey());
		return wordsList;
	}

	@Override
	public int[] matrixSorter(double[] matrix) {
		// TODO Auto-generated method stub
		int []recommand=new int [matrix.length];
		for(int i=0;i<recommand.length;i++)recommand[i]=i;
		for(int i=0;i<matrix.length;i++) {
			for (int j=i;j<matrix.length;j++) {
				if (matrix[i]<matrix[j]) {
					double temp=matrix[i];
					matrix[i]=matrix[j];
					matrix[j]=temp;
					int n=recommand[i];
					recommand[i]=recommand[j];
					recommand[j]=n;
				}
			}
		}
		int []a=new int [10];
		for (int i=0;i<10;i++)a[i]=recommand[i];
		return a;
	}

}
