package user.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class Property{
	private final static Property INSTANCE = new Property();
	private String filePath;

	public Property() {
		super();
	}
	public Property(String filePath) {
		super();
		this.filePath = filePath;
	}
	
	public static Property getInstance() {
		return INSTANCE;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public static Properties createPropertis(){
		return new Properties();
	}
	public Properties readFile(String filePath) throws IOException{
		Properties prop = new Properties();
        File file = new File(filePath);  
        if (!file.exists()){
        	return prop;
        }
        InputStream fis = new FileInputStream(file);  
        prop.load(fis);  
        fis.close();//一定要在修改值之前关闭fis  
		return prop;
	}
	public boolean saveFile(Properties prop,String filePath,String comments)throws IOException {
		File file = new File(filePath);
		if (file.exists()) {
			file.delete();
		}
//		file.createNewFile();
		
		OutputStream fos = new FileOutputStream(file);
		prop.store(fos, comments);
		fos.flush();
		fos.close();
		
//		FileWriter fileWriter = new FileWriter(file);
//		prop.store(fileWriter, comments);
//
//		fileWriter.flush();
//		fileWriter.close();

		return true;
    }
}
