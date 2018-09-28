package user.service;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import user.util.CSVUtils;
import user.util.MyLogger;

public class CSVFile implements Persistent {

	@Override
	public int hasSheetByName(String dir,String fileName, String sheetName) {
		int ret = -1;
		try {
			String directory = dir+fileName+System.getProperty("file.separator");
			if(CSVUtils.isExists(directory,directory+sheetName+".csv")){
				ret = 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
			MyLogger.logger.log(Level.INFO, "hasSheetByName Exception", e);
			 ret = -1;
		}
		return ret;
	}
	@Override
	public int writeData(String dir, String fileName, String sheetName, List<String[]> datas) throws IOException {
		int ret=-1;
		try {
			String path=dir+fileName+System.getProperty("file.separator");
			File directory = new File(path);
			File file = new File(path+sheetName+".csv");
			ret = CSVUtils.exportCsv(directory, file, datas);
		}
		catch (Exception e) {
			e.printStackTrace();
			MyLogger.logger.log(Level.INFO, "writeData Exception", e);
			if(e instanceof IOException){
				throw (IOException)e;
			}
		}
		return ret;
	}
	@Override
	public boolean writeHeader(String dir,String fileName,String sheetName, String[] header) {
		try {
			if(header==null || header.length<=0){
				return false;
			}
			List<String[]> datas = new ArrayList<String[]>();
			datas.add(header);
			return writeData(dir,fileName,sheetName,datas)==1;
		} catch (IOException e) {
			e.printStackTrace();
			MyLogger.logger.log(Level.INFO, "writeData writeHeader", e);
		}
		return false;
	}

}
