package segmenter;

import java.io.IOException;
import java.util.List;

import data.Stockinfo;

public interface ChineseSegmenter {
	List<String> getWordsFromInput(Stockinfo[] stocks) throws IOException;
	List<String> getWordsFromString(String string) throws IOException;
}
