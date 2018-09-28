package user.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import user.model.DataRow;
import user.util.DateUtil;
import user.util.Property;

public class ExportDataService implements Runnable
{
	private Persistent persistent;
	private int runTimes = 1000;
	private DataRow[] dataRows;
	private String[] collectDatas;
	private String[] header;
	private String filename;
	private long createFileTime =-1;
	private String dir ;
	private String folder="exportData";
	private static final long logTimes = 30*24*60*60*1000l;
	private Property p ;
	private Properties properties;
	public static final String PropertyFile="config.property";
	private List<String[]> saveData = new ArrayList<String[]>();
	public static final String CREATE_FILE_TIME = "CREATE_FILE_TIME";
	public static final String CREATE_FILE_NAME = "CREATE_TIME";
	private boolean isRun= false;
	private boolean isStop = false;
	public ExportDataService(Persistent persistent,DataRow[] dataRows)
	{
		super();
		this.persistent = persistent;
		this.dataRows = dataRows;
		init();
	}

	public ExportDataService(Persistent persistent,DataRow[] dataRows, int runTimes)
	{
		super();
		this.persistent = persistent;
		this.runTimes = runTimes;
		this.dataRows = dataRows;
		init();
	}
	public void init(){
		if(dataRows!=null && dataRows.length>0){
			collectDatas = new String[dataRows.length+1];
			header = new String[dataRows.length+1];
			header[dataRows.length]="time";
			for(int i = 0 ; i< dataRows.length ; i ++){
				if(dataRows[i]!=null){
					header[i] = dataRows[i].getVarName() ;					
				}
			}
		}
		saveData.add(collectDatas);
		if(runTimes<0){
			runTimes = 1000;
		}
		dir = System.getProperty("user.dir")+File.separatorChar;
		p = Property.getInstance();
		try
		{
			properties = p.readFile(dir + PropertyFile);
			createFileTime = Long.parseLong(properties.getProperty(CREATE_FILE_TIME));
			filename = CREATE_FILE_NAME  +"_"+DateUtil.formatFullDate2(new Date(createFileTime));
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}
	private boolean clear(String[] strs){
		boolean ret =false;
		if(strs!=null && strs.length>0){
			try
			{
				for(int i= 0 ; i<strs.length;i++){
					strs[i] = "";
				}
				ret = true;
			} catch (Exception e)
			{
				ret = false;
				e.printStackTrace();
			}
		}
		return ret;
	}
	private void delay(int time){
		try
		{
			Thread.sleep(time);
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public synchronized boolean exportFile(){
		//收集数据
		if(collect()){
			//保存文件
			return saveFile();
		}else{
			return false;
		}
	}
	@Override
	public void run()
	{
		while(!isStop){
			if(isRun){
				exportFile();
				delay(runTimes);
			}
			delay(1000);
		}
	}
	public boolean collect(){
		//清除原数据
		if(!clear(collectDatas)){
			if(!clear(collectDatas)){
				collectDatas = new String[dataRows.length+1];
			}
		}
		boolean ret = true;
		//收集
		try
		{
			for(int i = 0 ; i <dataRows.length;i++){
				if(dataRows[i]!=null){
					collectDatas[i] = dataRows[i].getVal();
				}
			}
			collectDatas[collectDatas.length-1] = DateUtil.formatFullDate(new Date());
		} catch (Exception e)
		{
			e.printStackTrace();
			ret=false;
		}
		
		return ret;
	}
	private void newFileName(){
		filename = CREATE_FILE_NAME +"_"+ DateUtil.formatFullDate2(new Date(createFileTime)); 
	}
	/**
	 * 保存文件
	 * @return
	 */
	public boolean saveFile(){

		synchronized (persistent)
		{
			boolean ret = false;
			if(isCreateFile()){
				if(updateCreateTime()){
					persistent.writeHeader(dir, folder, filename, header);
				}else{
					return false;
				}
			}else{
				if(persistent.hasSheetByName(dir, folder, filename)==-1){
					if(updateCreateTime()){
						persistent.writeHeader(dir, folder, filename, header);
					}else{
						return false;
					}
				}
			}
			try
			{
				ret  = persistent.writeData(dir, folder, filename, saveData)>0;
			} catch (IOException e)
			{
				ret = false;
				e.printStackTrace();
			}
			return ret;	
		}
			
	}
	private boolean updateCreateTime(){
		boolean ret = true;
		try
		{
			createFileTime = System.currentTimeMillis();
			properties.setProperty(CREATE_FILE_TIME, "" + createFileTime);
			filename = CREATE_FILE_NAME  +"_"+DateUtil.formatFullDate2(new Date(createFileTime));
			p.saveFile(properties,dir + PropertyFile , "" + createFileTime);
		} catch (IOException e)
		{
			ret = false;
			e.printStackTrace();
		}catch(Exception e ){
			ret = false;
		}
		return ret;
	}
	private boolean isCreateFile(){
		//Date d = DateUtil.getFormatDate(filename);
		if(createFileTime<0){
			return true;
		}
		else if(System.currentTimeMillis()-createFileTime>logTimes){
			return true;
		}else{
			return false;
		}
	}
	public Persistent getPersistent()
	{
		return persistent;
	}

	public void setPersistent(Persistent persistent)
	{
		this.persistent = persistent;
	}

	public int getRunTimes()
	{
		return runTimes;
	}

	public void setRunTimes(int runTimes)
	{
		this.runTimes = runTimes;
	}

	public DataRow[] getDataRows()
	{
		return dataRows;
	}

	public void setDataRows(DataRow[] dataRows)
	{
		this.dataRows = dataRows;
	}

	public String[] getCollectDatas()
	{
		return collectDatas;
	}

	public void setCollectDatas(String[] collectDatas)
	{
		this.collectDatas = collectDatas;
	}

	public String[] getHeader()
	{
		return header;
	}

	public void setHeader(String[] header)
	{
		this.header = header;
	}

	public String getFilename()
	{
		return filename;
	}

	public void setFilename(String filename)
	{
		this.filename = filename;
	}

	public long getCreateFileTime()
	{
		return createFileTime;
	}

	public void setCreateFileTime(long createFileTime)
	{
		this.createFileTime = createFileTime;
	}

	public String getDir()
	{
		return dir;
	}

	public void setDir(String dir)
	{
		this.dir = dir;
	}

	public String getFolder()
	{
		return folder;
	}

	public void setFolder(String folder)
	{
		this.folder = folder;
	}

	public List<String[]> getSaveData()
	{
		return saveData;
	}

	public void setSaveData(List<String[]> saveData)
	{
		this.saveData = saveData;
	}

	public static long getLogtimes()
	{
		return logTimes;
	}

	public boolean isRun()
	{
		return isRun;
	}

	public void setRun(boolean isRun)
	{
		this.isRun = isRun;
	}

	public boolean isStop()
	{
		return isStop;
	}
	public void setStop(boolean isStop)
	{
		this.isStop = isStop;
	}	
}
