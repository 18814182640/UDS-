package user.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import com.sun.org.apache.xerces.internal.util.SecurityManager;

/**
 * CSV操作(导出和导入)
 */
public class CSVUtils {
	public static Charset charset=Charset.defaultCharset();
	public static boolean append = true;
	public static String separateMark=",";
	public static String rowMark="\r\n";
	
	
	/**
	 * 导出
	 * 
	 * @param file csv文件(路径+文件名)，csv文件不存在会自动创建
	 * @param dataList 数据
	 * @return
	 */
	public static int exportCsv(File dir,File file, List<String[]> dataList) {
		int sucessNum = -1;
		
		if(!dirExists(dir)){
			return sucessNum;
		}
		if(!fileExists(file)){
			return sucessNum;
		}
		
		FileOutputStream out = null;
		OutputStreamWriter osw = null;
		BufferedWriter bw = null;
		try {
			int count = 0 ;
			out = new FileOutputStream(file,append);
			osw = new OutputStreamWriter(out,charset);
			bw = new BufferedWriter(osw);
			if (dataList != null && !dataList.isEmpty()) {
				for(String [] strs:dataList){
					if(strs==null|| strs.length<=0){
						continue;
					}
					for (int index = 0;index<strs.length;index++) {
						if(!StringUtils.isBlank(strs[index])){
							bw.append(strs[index]);
						}
						if(index+1<strs.length){
							bw.append(separateMark);														
						}else{		
							bw.append(rowMark);
						}
					}
					count++;
				}
			}
			sucessNum = count;
		}
		catch (Exception e) {
			sucessNum = -1;
			e.printStackTrace();
			MyLogger.logger.log(Level.INFO, "exportCsv Exception", e);
		} finally {
			if (bw != null) {
				try {
					bw.close();
					bw = null;
				} catch (IOException e) {
					e.printStackTrace();
					MyLogger.logger.log(Level.INFO, "bw.close() IOException e", e);
				}
			}
			if (osw != null) {
				try {
					osw.close();
					osw = null;
				} catch (IOException e) {
					e.printStackTrace();
					MyLogger.logger.log(Level.INFO, "osw.close() IOException e", e);
				}
			}
			if (out != null) {
				try {
					out.close();
					out = null;
				} catch (IOException e) {
					MyLogger.logger.log(Level.INFO, "out.close() IOException e", e);
					e.printStackTrace();
				}
			}
		}

		return sucessNum;
	}
	/**
	 * 不存在就创建
	 * @param file
	 * @return
	 */
	public static boolean dirExists(File dir){
		boolean blg=false;
		if(!dir.exists()){
			try {
				blg = dir.mkdir();
			} catch (Exception e) {
				e.printStackTrace();
				MyLogger.logger.log(Level.INFO, "dirExists exnception e", e);
				blg =false;
			}
		}else{
			blg = true;
		}
		return blg;
	}
	/**
	 * 不存在就创建
	 * @param file
	 * @return
	 */
	private static boolean fileExists(File file){
		boolean blg=false;
		if(!file.exists()){
			try {
				blg = file.createNewFile();
			} catch (Exception e) {
				e.printStackTrace();
				blg =false;
				MyLogger.logger.log(Level.INFO, "fileExists Exception", e);
				
			}
		}else{
			blg = true;
		}
		return blg;
	}
	/**
	 * 导入
	 * @param file csv文件(路径+文件)
	 * @return
	 */
	public static List<String> importCsv(File file) {
		List<String> dataList = new ArrayList<String>();

		BufferedReader br = null;
		FileInputStream ins =null;
		InputStreamReader inr = null;
		try {
			
			ins = new FileInputStream(file);
			inr = new InputStreamReader(ins,charset);
			br = new BufferedReader(inr);
			String line = "";
			while ((line = br.readLine()) != null) {
				dataList.add(line);
			}
		} catch (Exception e) {
		} finally {
			if (br != null) {
				try {
					br.close();
					br = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return dataList;
	}
	public static boolean isExists(String dir ,String fileName){
		File directory = null;
		File file = null;
		boolean ret =false;
		try {
			file = new File(fileName);
			directory = new File(dir);
			ret = directory.exists() && file.exists();
		} catch (Exception e) {
			e.printStackTrace();
			ret =false;
			MyLogger.logger.log(Level.INFO, "isExists Exception", e);
			
		}
		return ret;
	}
}
