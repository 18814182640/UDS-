package user.service;

import java.io.IOException;
import java.util.List;
/**
 * 保存文件
 * @author rdtest305
 *
 */
public interface Persistent {
	/**
	 * 是否存在SHEET，存在返回sheetID,不存在返回-1
	 * @param fileName
	 * @param sheetName
	 * @return
	 */
	public int hasSheetByName(String dir,String folder,String fileName);
	/**
	 * 追加数据
	 * @param dir
	 * @param fileName
	 * @param sheetName
	 * @param datas
	 * @return -1：不成功
	 * @throws IOException
	 */
	public int writeData(String dir,String folder,String fileName,List<String[]> datas)throws IOException;
	/**
	 * 写标题列是否成功
	 * @param dir
	 * @param fileName
	 * @param sheetName
	 * @param header
	 * @return
	 */
	public boolean writeHeader(String dir,String folder,String fileName,String[] header);
}
