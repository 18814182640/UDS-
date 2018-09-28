package user.Method;

import java.util.ArrayList;
import java.util.List;
import user.model.ResultModel;

public class ResultCache
{
	private static final ResultCache instance=new ResultCache();
	public static final int[] TEMP_SIMPLE={10,25,40,55,10,25,40,55,10,25,40,55};
	private static long[] writeValues={23130,0,23130,0,0,23130,4500,1000,65000,250};
	private int cells,temps,testFan,isActiveTest,isPassiveTest;
	private String[][] analyseDatas={{"从控板ID","","压差",""},{"温度1","","最高电压",""},{"温度2","","最低电压",""}};
	private String[][] cellVolt;
	private String[][] balances;
	private String[][] temp;
	private String[][] fans;
	private String[][] adjust;
	
	private boolean[][] cellVoltRet;
	private boolean[][] balancesRet;
	private boolean[][] tempRet;
	private boolean[][] fansRet;
	private boolean[][] adjustRet;
	
	private int alarm;
	private int fault;
	private String version="";
	private int[] battaryVolt;
	private int[] battaryCurrent;
	
	//总结果
	private List<ResultModel> totalStatus =  new ArrayList<ResultModel>();
	//1、CAN通讯；
	private List<ResultModel> CAN = new ArrayList<ResultModel>();
	//	2、	RS485通讯；
	private List<ResultModel> RS485 = new ArrayList<ResultModel>();
//	3、	接触器控制控制；
	private List<ResultModel> cocontactor = new ArrayList<ResultModel>();
//	4、	LED输出控制；
	private List<ResultModel> LED = new ArrayList<ResultModel>();
//	5、	输出IO状态控制；
	private List<ResultModel> outputIO = new ArrayList<ResultModel>();
//	6、	输入IO状态回检；
	private List<ResultModel> inputIO = new ArrayList<ResultModel>();
//	7、	漏电检测功能；
	private List<ResultModel> leakEl = new ArrayList<ResultModel>();
//	8、	温度采样功能；
	private List<ResultModel> tempSampling = new ArrayList<ResultModel>();
//	9、	电流采样功能；
	private List<ResultModel> currentSampling = new ArrayList<ResultModel>();
//	10、	高压采样功能； 
	private List<ResultModel> voltSampling = new ArrayList<ResultModel>();
//	11、	SD卡检测；
	private List<ResultModel>  sdCard= new ArrayList<ResultModel>();
//	12、	以太网通讯检测。
	private List<ResultModel> ethernet = new ArrayList<ResultModel>();

	
	public List<ResultModel> getCAN()
	{
		return CAN;
	}
	public void setCAN(List<ResultModel> cAN)
	{
		CAN = cAN;
	}
	public List<ResultModel> getRS485()
	{
		return RS485;
	}
	public void setRS485(List<ResultModel> rS485)
	{
		RS485 = rS485;
	}
	public List<ResultModel> getCocontactor()
	{
		return cocontactor;
	}
	public void setCocontactor(List<ResultModel> cocontactor)
	{
		this.cocontactor = cocontactor;
	}
	public List<ResultModel> getLED()
	{
		return LED;
	}
	public void setLED(List<ResultModel> lED)
	{
		LED = lED;
	}
	public List<ResultModel> getOutputIO()
	{
		return outputIO;
	}
	public void setOutputIO(List<ResultModel> outputIO)
	{
		this.outputIO = outputIO;
	}
	public List<ResultModel> getInputIO()
	{
		return inputIO;
	}
	public void setInputIO(List<ResultModel> inputIO)
	{
		this.inputIO = inputIO;
	}
	public List<ResultModel> getLeakEl()
	{
		return leakEl;
	}
	public void setLeakEl(List<ResultModel> leakEl)
	{
		this.leakEl = leakEl;
	}
	public List<ResultModel> getTempSampling()
	{
		return tempSampling;
	}
	public void setTempSampling(List<ResultModel> tempSampling)
	{
		this.tempSampling = tempSampling;
	}
	public List<ResultModel> getCurrentSampling()
	{
		return currentSampling;
	}
	public void setCurrentSampling(List<ResultModel> currentSampling)
	{
		this.currentSampling = currentSampling;
	}
	public List<ResultModel> getVoltSampling()
	{
		return voltSampling;
	}
	public void setVoltSampling(List<ResultModel> voltSampling)
	{
		this.voltSampling = voltSampling;
	}
	public List<ResultModel> getSdCard()
	{
		return sdCard;
	}
	public void setSdCard(List<ResultModel> sdCard)
	{
		this.sdCard = sdCard;
	}
	public List<ResultModel> getEthernet()
	{
		return ethernet;
	}
	public void setEthernet(List<ResultModel> ethernet)
	{
		this.ethernet = ethernet;
	}
	private ResultCache(){
		
	}
	public static ResultCache getInstance(){
		return instance;
	}
	/**
	 *初始化
	 * @param cells 电池节数
	 * @param temps 温度数
	 */
	public void init(int cells,int temps,int testFan,int isActiveTest,int isPassiveTest){
		this.cells=cells;
		this.temps=temps;
		this.testFan=testFan;
		this.isActiveTest=isActiveTest;
		this.isPassiveTest=isPassiveTest;
		battaryVolt=new int[cells];
		battaryCurrent=new int[cells];
		
		cellVolt=new String[cells][5];
		cellVoltRet=new boolean[cells][5];
		resetBoolean(false,cellVoltRet);
		for(int i=0;i<cellVolt.length;i++){
			cellVolt[i][0]=(i+1)+"";
			cellVolt[i][1]="";
			cellVolt[i][2]="";
			cellVolt[i][3]="";
			cellVolt[i][4]="";
		}
		int blancelen=2+this.isActiveTest*4+this.isPassiveTest*2;
		balances=new String[cells][blancelen];
		balancesRet=new boolean[cells][blancelen];
		resetBoolean(false,balancesRet);
		for(int i=0;i<balances.length;i++){
			balances[i][0]=(i+1)+"";
			for(int j=1;j<blancelen;j++){
				balances[i][j]="";
			}
		}
		temp=new String[temps][4];
		tempRet=new boolean[temps][4];
		resetBoolean(false,tempRet);
		for(int i=0;i<temp.length;i++){
			temp[i][0]=(i+1)+"";
			temp[i][1]=TEMP_SIMPLE[i%12]+"";
			temp[i][2]="";
			temp[i][3]="";
		}
		if(testFan==1){
			fansRet=new boolean[2][2];
			resetBoolean(false,fansRet);
			fans=new String[][]{{"是否开启",""},{"是否关闭",""}};
		}else{
			fans=null;
			fansRet=null;
		}
		adjust=new String[][]{{"3V电压校准","",""},{"4V电压校准","",""},{"校准结果","",""}};
		adjustRet=new boolean[3][3];
	}
	
	public void resetBoolean(boolean ret,boolean[][] obj){
		for(int i=0;i<obj.length;i++){
			for(int j=0;j<obj[i].length;j++){
				obj[i][j]=ret;
			}
		}
	}
	public void clearBattery(){
		if(battaryVolt!=null&&battaryVolt.length>0){
			for(int i=0;i<battaryVolt.length;i++){
				battaryVolt[i]=0;
			}
		}
		if(battaryCurrent!=null&&battaryCurrent.length>0){
			for(int i=0;i<battaryCurrent.length;i++){
				battaryCurrent[i]=0;
			}
		}
	}
	public void clear(){
		init(cells,temps,testFan,isActiveTest,isPassiveTest);
	}

	public String[][] getCellVolt()
	{
		return cellVolt;
	}

	public void setCellVolt(String[][] cellVolt)
	{
		this.cellVolt = cellVolt;
	}

	public String[][] getBalances()
	{
		return balances;
	}

	public void setBalances(String[][] balances)
	{
		this.balances = balances;
	}

	public String[][] getTemp()
	{
		return temp;
	}

	public void setTemp(String[][] temp)
	{
		this.temp = temp;
	}

	public String[][] getFans()
	{
		return fans;
	}

	public void setFans(String[][] fans)
	{
		this.fans = fans;
	}

	public int getAlarm()
	{
		return alarm;
	}

	public void setAlarm(int alarm)
	{
		this.alarm = alarm;
	}

	public int getFault()
	{
		return fault;
	}

	public void setFault(int fault)
	{
		this.fault = fault;
	}

	public String getVersion()
	{
		return version;
	}

	public void setVersion(String version)
	{
		this.version = version;
	}
	public int[] getBattaryVolt()
	{
		return battaryVolt;
	}
	public void setBattaryVolt(int[] battaryVolt)
	{
		this.battaryVolt = battaryVolt;
	}
	public int[] getBattaryCurrent()
	{
		return battaryCurrent;
	}
	public void setBattaryCurrent(int[] battaryCurrent)
	{
		this.battaryCurrent = battaryCurrent;
	}
	public boolean[][] getCellVoltRet()
	{
		return cellVoltRet;
	}
	public void setCellVoltRet(boolean[][] cellVoltRet)
	{
		this.cellVoltRet = cellVoltRet;
	}
	public boolean[][] getBalancesRet()
	{
		return balancesRet;
	}
	public void setBalancesRet(boolean[][] balancesRet)
	{
		this.balancesRet = balancesRet;
	}
	public boolean[][] getTempRet()
	{
		return tempRet;
	}
	public void setTempRet(boolean[][] tempRet)
	{
		this.tempRet = tempRet;
	}
	public boolean[][] getFansRet()
	{
		return fansRet;
	}
	public void setFansRet(boolean[][] fansRet)
	{
		this.fansRet = fansRet;
	}
	public String[][] getAdjust()
	{
		return adjust;
	}
	public void setAdjust(String[][] adjust)
	{
		this.adjust = adjust;
	}
	public boolean[][] getAdjustRet()
	{
		return adjustRet;
	}
	public void setAdjustRet(boolean[][] adjustRet)
	{
		this.adjustRet = adjustRet;
	}
	public List<ResultModel> getTotalStatus()
	{
		return totalStatus;
	}
	public void setTotalStatus(List<ResultModel> totalStatus)
	{
		this.totalStatus = totalStatus;
	}
	public static long[] getWriteValues()
	{
		return writeValues;
	}
	public String[][] getAnalyseDatas()
	{
		return analyseDatas;
	}
	
}
