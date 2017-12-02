package Hander;

import data.Stockinfo;

public interface FileHanderInterface {

	Stockinfo[] getStockInfoFromFile(String filePath)throws Exception;
}
