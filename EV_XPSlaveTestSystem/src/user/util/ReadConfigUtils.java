package user.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

public class ReadConfigUtils {
	private  Properties config;
	private static final ReadConfigUtils instance=new ReadConfigUtils();
	private ReadConfigUtils(){
		config=new Properties();
		try {
			config.load(new FileInputStream(System.getProperty("user.dir")+"/testType.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static ReadConfigUtils getInstance(){
		return instance;
	}
	
	public  String getProperties(String key){
		return (String) config.get(key); 
	}
	
	public  void setProperties(String key,String value){
		config.setProperty(key,value); 
		saveProp();
	}
	
	public  boolean saveFile(Properties prop,String filePath,String comments)throws IOException {
		File file = new File(filePath);
		if (file.exists()) {
			file.delete();
		}
		
		OutputStream fos = new FileOutputStream(file);
		prop.store(fos, comments);
		fos.flush();
		fos.close();

		return true;
    }
	
	public  void saveProp(){
		String file=System.getProperty("user.dir")+"/testType.properties";
		try
		{
			saveFile(config,file,"");
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
