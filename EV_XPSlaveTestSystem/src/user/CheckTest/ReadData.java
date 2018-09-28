package user.CheckTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import user.CanDriver.CanController;
import user.Method.ResultCache;
import user.frame.TabFrame;
import user.model.DataRow;
import user.model.ResultModel;
import user.service.ExportDataService;
import user.util.TiCommandCompare;
/**
 * 读取配置参数
 * @author 150423015
 *
 */
public class ReadData extends CheckAbs
{
//	private TabFrame frame;
	private DataRow totalStatus=null;
	private int lastStep = 0;
	
	public ReadData()
	{
		super();
		stepCount = 2;
	}
	
//	public ReadData(TabFrame frame)
//	{
//		super();
//		this.frame = frame;
//	}

	@Override
	public void doTest()
	{
		isRun=true;
		ret = false;
		step= 0 ;
		CanController.getInstance().writeCmdQueue(CanController.getInstance().getQueryCmd(76,0,10));
		step=1;
		ret = isSuccessfully();
//		if(frame!=null){
//			frame.refreshTables();
//		}
		step = 2;
	}

	public void beforeTest(){
//		List<ResultModel> resultModels = null;
//		stepCount= tempData.getTotalStatus().size()-1;
//		resultModels = ResultCache.getInstance().getTotalStatus();
//		if(resultModels==null){
//			resultModels = new ArrayList<ResultModel>();
//			ResultCache.getInstance().setTotalStatus(resultModels);
//		}
//		initResulCache(tempData.getTotalStatus(),resultModels,1);
//		
//		resultModels = ResultCache.getInstance().getCAN();
//		if(resultModels==null){
//			resultModels = new ArrayList<ResultModel>();
//			ResultCache.getInstance().setCAN(resultModels);
//		}
//		initResulCache(tempData.getCAN(),resultModels,0);
//		
//		resultModels = ResultCache.getInstance().getVoltSampling();
//		if(resultModels==null){
//			resultModels = new ArrayList<ResultModel>();
//			ResultCache.getInstance().setVoltSampling(resultModels);
//		}
//		initResulCache(tempData.getVoltSampling(),resultModels,0);
//		
//		resultModels = ResultCache.getInstance().getTempSampling();
//		if(resultModels==null){
//			resultModels = new ArrayList<ResultModel>();
//			ResultCache.getInstance().setTempSampling(resultModels);
//		}
//		initResulCache(tempData.getTempSampling(),resultModels,0);
//		
//		resultModels = ResultCache.getInstance().getLeakEl();
//		if(resultModels==null){
//			resultModels = new ArrayList<ResultModel>();
//			ResultCache.getInstance().setLeakEl(resultModels);
//		}
//		initResulCache(tempData.getLeakEl(),resultModels,0);
		
	}
	protected void writeTOCache(int step){
//		List<DataRow> srcList=null;
//		List<ResultModel> rsModel=null;
//		ResultCache cache = ResultCache.getInstance();
//		switch (step)
//		{
//			case 1:
//				srcList = tempData.getCAN();
//				rsModel = cache.getCAN();
//			case 2:
//				srcList= tempData.getVoltSampling();
//				rsModel = cache.getVoltSampling();
//				break;
//			case 3:
//				srcList= tempData.getLeakEl();
//				rsModel = cache.getLeakEl();
//				break;
//			case 4:
//				srcList =tempData.getTempSampling();
//				rsModel = cache.getTempSampling();
//				break;
//			default:
//				break;
//		}
//		if(srcList==null || rsModel==null){
//			return ;
//		}
//		for(int index = 0;index<srcList.size();index++){
//			writeTOCache(srcList.get(index),rsModel.get(index));			
//		}
	}

	@Override
	public void sendCloseMsgCtrlCmd(int status)
	{
//		sendControllCmd(status,2);
	}
	private TiCommandCompare compare =  new TiCommandCompare();
	public boolean isSuccessfully(){
		long[] writeValues=ResultCache.getInstance().getWriteValues();
		List<DataRow> paramaterList = CanController.getInstance().getDatas().get("76");
		Collections.sort(paramaterList,compare );
		boolean ret = true;
		long now = System.currentTimeMillis();
		do{
			ret = true;
			for(int i = 0 ;i<paramaterList.size();i++){
				if(i==1||i==3||i==4){
					continue;
				}
				if(paramaterList.get(i)!=null){
					Long temp =parseLong(paramaterList.get(i).getVal());
					System.out.print("writeValues[i] : " + writeValues[i] + " , temp = " + temp + " ,ret : " );
					if(temp!=null &&writeValues[i]==temp){
						System.out.println("OK");
					}else{
						ret&=false;
						System.out.println("NO");
					}
				}
			}
			if(ret){
				return true;
			}
			delay(500);
		}while(System.currentTimeMillis()-now<=10000);
		return ret;
	}
	public String getResultPrompt(){
		if(ret){
			return "读取成功!";
		}else{
			return "读取数据与默认数据不同，请重写配置参数!";
		}
	}
}
