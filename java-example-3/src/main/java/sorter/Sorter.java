package sorter;

import java.util.List;

import javafx.util.Pair;

public interface Sorter {
	List<String> getKeyWords(Pair<String,Double>[] pairs);
	int []matrixSorter(double []matrix);

}
