package Hander;
import java.io.*;

import data.Stockinfo;

public class FileHander implements FileHanderInterface{

	@Override
	public Stockinfo[] getStockInfoFromFile(String filePath) throws Exception {
		File readerFile =new File( filePath );
		InputStreamReader icp;
		icp = new InputStreamReader(new FileInputStream(readerFile));
		BufferedReader reader=new BufferedReader(icp);
		String str=null;
		Stockinfo []stoInfo=new Stockinfo [60];
		int i=0;
		for(;i<=stoInfo.length&&(str = reader.readLine())!=null;i++) {
			if (i==0)continue;
			else {
				String []stringArray=str.split("\t");
				stoInfo[i-1]=new Stockinfo();
				stoInfo[i-1].setID(stringArray [0]);
				stoInfo[i-1].setTitle(stringArray[1]);
				stoInfo[i-1].setAuthor(stringArray[2]);
				stoInfo[i-1].setDate(stringArray[3]);
				stoInfo[i-1].setLastUpdate(stringArray[4]);
				stoInfo[i-1].setContent(stringArray[5]);
				stoInfo[i-1].setAnswerAuthor(stringArray[6]);
				stoInfo[i-1].setAnswer(stringArray[7]);
				}
			}
		reader.close();
		return stoInfo ;
	}
}
